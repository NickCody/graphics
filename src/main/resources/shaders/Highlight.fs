uniform vec2 u_resolution;
uniform vec2 u_mouse;

#define FALLOFF 800

void main() {
    float distMouse = min(FALLOFF, distance(gl_FragCoord, u_mouse));
    float distZero = distance(gl_FragCoord, vec2(0,0))/u_resolution;
    vec3 color = mix(vec3(0.6901961,0.8784314,0.9019608), vec3(0.5411765,0.16862746,0.8862745), distZero);
    vec3 final = mix(color, vec3(1.0,1.0,1.0), 1.0-(distMouse/FALLOFF));
    gl_FragColor = vec4(final,1.0);
}