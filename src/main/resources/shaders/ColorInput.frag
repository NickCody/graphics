#version 400

const float PI_2 = 1.57079632679489661923;

uniform vec2 u_resolution;
uniform vec2 u_mouse;

in vec3 color;
out vec4 frag_color;

#define FALLOFF (u_resolution.x / 8.0)

void main() {
    float distMouse = min(FALLOFF, distance(gl_FragCoord.xy, u_mouse));

    float lin_falloff = 1.0-(min(distMouse, FALLOFF) / FALLOFF);
    float trig_falloff = cos(min(distMouse, FALLOFF) / FALLOFF * PI_2);

    vec3 finalColor = mix(color, vec3(1.0,1.0,1.0), pow(trig_falloff, 2));
    frag_color = vec4(finalColor, 1.0);
}