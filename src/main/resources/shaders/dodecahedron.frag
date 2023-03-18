#version 400

out vec4 FragColor;

uniform float iTime;
uniform vec2 iResolution;
uniform vec2 iMouse;

vec3 lightPos = vec3(5.0, 2.0, 0.0);

const float phi = 1.618033988749895;
const float dodecahedronSize = 1.0;

float dodecahedronSDF(vec3 p, float size) {
    vec3 a = vec3(1.0 / phi, phi, 0.0);
    vec3 b = vec3(1.0 / phi, -phi, 0.0);
    vec3 c = vec3(-1.0 / phi, phi, 0.0);
    vec3 d = vec3(-1.0 / phi, -phi, 0.0);
    vec3 e = vec3(phi, 0.0, 1.0 / phi);
    vec3 f = vec3(-phi, 0.0, 1.0 / phi);
    vec3 g = vec3(phi, 0.0, -1.0 / phi);
    vec3 h = vec3(-phi, 0.0, -1.0 / phi);
    vec3 i = vec3(0.0, 1.0 / phi, phi);
    vec3 j = vec3(0.0, 1.0 / phi, -phi);
    vec3 k = vec3(0.0, -1.0 / phi, phi);
    vec3 l = vec3(0.0, -1.0 / phi, -phi);

    float s = size / 1.902113032590307;

    float dist = dot(a, p) - s;
    dist = max(dist, -dot(a, p) - s);
    dist = max(dist, dot(b, p) - s);
    dist = max(dist, -dot(b, p) - s);
    dist = max(dist, dot(c, p) - s);
    dist = max(dist, -dot(c, p) - s);
    dist = max(dist, dot(d, p) - s);
    dist = max(dist, -dot(d, p) - s);
    dist = max(dist, dot(e, p) - s);
    dist = max(dist, -dot(e, p) - s);
    dist = max(dist, dot(f, p) - s);
    dist = max(dist, -dot(f, p) - s);
    dist = max(dist, dot(g, p) - s);
    dist = max(dist, -dot(g, p) - s);
    dist = max(dist, dot(h, p) - s);
    dist = max(dist, -dot(h, p) - s);
    dist = max(dist, dot(i, p) - s);
    dist = max(dist, -dot(i, p) - s);
    dist = max(dist, dot(j, p) - s);
    dist = max(dist, -dot(j, p) - s);
    dist = max(dist, dot(k, p) - s);
    dist = max(dist, -dot(k, p) - s);
    dist = max(dist, dot(l, p) - s);
    dist = max(dist, -dot(l, p) - s);

    return dist;
}

vec3 calcNormal(vec3 p, float size) {
    float eps = 0.001;
    vec3 dx = vec3(eps, 0.0, 0.0);
    vec3 dy = vec3(0.0, eps, 0.0);
    vec3 dz = vec3(0.0, 0.0, eps);

    float nx = dodecahedronSDF(p + dx, size) - dodecahedronSDF(p - dx, size);
    float ny = dodecahedronSDF(p + dy, size) - dodecahedronSDF(p - dy, size);
    float nz = dodecahedronSDF(p + dz, size) - dodecahedronSDF(p - dz, size);

    return normalize(vec3(nx, ny, nz));
}

vec3 rayMarch(vec3 ro, vec3 rd) {
    float t = 0.0;
    for (int i = 0; i < 100; i++) {
        vec3 p = ro + t * rd;
        float d = dodecahedronSDF(p, dodecahedronSize);
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
    float camRadius = 3.0;
    float camHeight = 2.5;
    float t = iTime * 0.1;
    vec3 camPos = vec3(camRadius * cos(t), camHeight, camRadius * sin(t));
    vec3 camTarget = vec3(0.0, 0.0, 0.0);
    vec3 camUp = vec3(0.0, 1.0, 0.0);

    vec3 camDir = normalize(camTarget - camPos);
    vec3 camRight = normalize(cross(camUp, camDir));
    vec3 camNewUp = cross(camDir, camRight);

    float focalLength = 1.0;

    vec3 rd = normalize(uv.x * camRight + uv.y * camNewUp + focalLength * camDir);
    vec3 p = rayMarch(camPos, rd);

    if (length(p) > 0.0) {
        vec3 n = calcNormal(p, dodecahedronSize);
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

        FragColor = vec4(0.15 + diffuse + specular, 1.0);
    } else {
        FragColor = vec4(0.0, 0.0, 0.0, 1.0);
    }
}
