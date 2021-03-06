#version 400

uniform vec2 u_resolution;
uniform vec2 u_mouse;

#define FALLOFF (u_resolution.x / 8.0)

void main() {
    vec2 origin = vec2(0,0);
    float distMouse = min(FALLOFF, distance(gl_FragCoord.xy, u_mouse));
    float distZero = distance(gl_FragCoord.xy, origin)/u_resolution.x;
    vec3 color = mix(vec3(0.6901961,0.8784314,0.9019608), vec3(0.5411765,0.16862746,0.8862745), distZero);
    vec3 final = mix(color, vec3(1.0,1.0,1.0), 1.0-(distMouse/FALLOFF));
    gl_FragColor = vec4(final,1.0);
}