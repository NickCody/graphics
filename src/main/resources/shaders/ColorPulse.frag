#version 400

uniform float u_time;

out vec4 frag_color;

void main() {
    vec3 cycleColor;
    float c = cos(u_time);
    vec3 blue = vec3(0.0, 0.0, 1.0);

    if ( c >= 0)
        cycleColor = mix(blue, vec3(1.0,1.0,1.0), c);
    else
        cycleColor = mix(blue, vec3(0.0,0.0,0.0), -c);

    frag_color = vec4(cycleColor, 1.0);
}