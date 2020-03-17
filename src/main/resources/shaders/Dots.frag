#version 400

uniform float u_time;

out vec4 frag_color;

void main() {
    vec3 finalColor;
    float c = cos(u_time);

    if ( c >= 0)
        finalColor = mix(vec3(0.0, 0.0, 1.0), vec3(1.0,1.0,1.0), c);
    else
        finalColor = mix(vec3(0.0, 0.0, 1.0), vec3(0.0,0.0,0.0), -c);

    frag_color = vec4(finalColor, 1.0);
}