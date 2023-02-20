#version 400

uniform float iTime;

out vec4 frag_color;

void main() {
    float TIME_SCALE=0.1;
    vec3 BLUE = vec3(0.0, 0.0, 1.0);

    vec3 cycleColor;
    float c = cos(iTime * TIME_SCALE);
    if ( c >= 0)
        cycleColor = mix(BLUE, vec3(1.0,1.0,1.0), c);
    else
        cycleColor = mix(BLUE, vec3(0.0,0.0,0.0), -c);

    frag_color = vec4(cycleColor, 1.0);
}