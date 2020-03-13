#version 400

const float PI = 3.1415926535897932384626433832795;
const float PI_2 = 1.57079632679489661923;
const float PI_4 = 0.785398163397448309616;

uniform vec2 u_resolution;
uniform vec2 u_mouse;

in vec3 color;
out vec4 frag_color;

#define FALLOFF 300.0

void main() {
    float distMouse = min(FALLOFF, distance(gl_FragCoord.xy, u_mouse));

    float trig_falloff = cos(min(distMouse, FALLOFF) / FALLOFF * PI_2);
    float lin_falloff = 1.0-(min(distMouse, FALLOFF) / FALLOFF);

    vec3 finalColor = mix(color, vec3(1.0,1.0,1.0), lin_falloff);
    frag_color = vec4(finalColor, 1.0);
}