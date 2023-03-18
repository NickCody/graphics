#version 400

out vec4 FragColor;

uniform float iTime;
uniform vec2 iResolution;
uniform vec2 iMouse;

vec3 lightPos = vec3(8.0, 5.0, 3.0);

float cubeSDF(vec3 p, vec3 size) {
    vec3 d = abs(p) - size;
    return min(max(d.x, max(d.y, d.z)), 0.0) + length(max(d, 0.0));
}

vec3 calcNormal(vec3 p) {
    float eps = 0.001;
    vec3 dx = vec3(eps, 0.0, 0.0);
    vec3 dy = vec3(0.0, eps, 0.0);
    vec3 dz = vec3(0.0, 0.0, eps);

    float nx = cubeSDF(p + dx, vec3(0.5)) - cubeSDF(p - dx, vec3(0.5));
    float ny = cubeSDF(p + dy, vec3(0.5)) - cubeSDF(p - dy, vec3(0.5));
    float nz = cubeSDF(p + dz, vec3(0.5)) - cubeSDF(p - dz, vec3(0.5));

    return normalize(vec3(nx, ny, nz));
}

vec3 rayMarch(vec3 ro, vec3 rd) {
    float t = 0.0;
    for (int i = 0; i < 100; i++) {
        vec3 p = ro + t * rd;
        float d = cubeSDF(p, vec3(0.5));
        if (d < 0.001) {
            return p;
        }
        t += d;
        if (t > 10.0) {
            break;
        }
    }
    return vec3(0.0);
}

void main() {
    vec2 uv = gl_FragCoord.xy / iResolution;
    uv = uv * 2.0 - 1.0;
    uv.x *= iResolution.x / iResolution.y;

    //vec3 camPos = vec3(5.0, 5.0, 5.0);
    float camRadius = 1.0;
    float camHeight = 1.0;
    float t = iTime * 1.0;
    vec3 camPos = vec3(camRadius * cos(t), camRadius * cos(t/10.0), camRadius * sin(t));
    vec3 camTarget = vec3(0.0, 0.0, 0.0);
    vec3 camUp = vec3(0.0, 1.0, 0.0);

    vec3 camDir = normalize(camTarget - camPos);
    vec3 camRight = normalize(cross(camUp, camDir));
    vec3 camNewUp = cross(camDir, camRight);

    float focalLength = 1.0;

    vec3 rd = normalize(uv.x * camRight + uv.y * camNewUp + focalLength * camDir);
    vec3 p = rayMarch(camPos, rd);

    if (length(p) > 0.0) {
        vec3 n = calcNormal(p);
        vec3 lightDir = normalize(lightPos - p);

        // Diffuse
        float diff = max(dot(n, lightDir), 0.0);
        vec3 diffuse = diff * vec3(0.0, 0.0, 1.0);

        // Specular
        float specularStrength = 0.5;
        vec3 viewDir = normalize(camPos - p);
        vec3 reflectDir = reflect(-lightDir, n);
        float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32);
        vec3 specular = specularStrength * spec * vec3(1.0, 1.0, 1.0);

        // Ambient
        vec3 ambient = vec3(0.03, 0.03, 0.03);

        FragColor = vec4(diffuse + specular + ambient, 1.0);
    } else {
        FragColor = vec4(0.0, 0.0, 0.0, 1.0);
    }
}
