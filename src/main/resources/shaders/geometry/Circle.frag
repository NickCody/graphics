#version 400

uniform float iTime;
uniform vec2 iResolution;
uniform vec2 iMouse;

out vec4 out_color;

//
// Shadertoy Start
//

const float E = 2.7182818284590452353602875;

float sigmoid(float x) {
    return 1.0/(1.0+pow(E, -x));
}

// Maps
// 0 -> 0.5 -> 1 to 0 -> 1.0 -> 0
//
float sigmoidToFalloff(float s) {
    return abs(1.0 - (s * 2.0));
}

//
// coord - pixel to test
// center - center of circle
// radius - radius of circle
// tolerance - thickness of circle line
// sharpness - 1.0 blurry, 0.0001 sharp
float test(vec2 coord, vec2 center, float radius, float thickness, float sharpness) {
    float d = sqrt(pow(coord.x - center.x,2.0) + pow(coord.y - center.y,2.0));
    float d1 = abs(d-radius);

    if (d1 < thickness)
        return 0.0;
    else
        return sigmoidToFalloff(sigmoid((d1-thickness)*sharpness));
}

void mainImage(out vec4 out_color, vec2 fragCoord) {
    vec3 white = vec3(1.0, 1.0, 1.0);
    vec3 black = vec3(0.0, 0.0, 0.0);
    float scale = 1.0;
    float radius = iResolution.x/5.0;

    vec2 center = (iResolution.xy * scale)/2.0;
    vec2 st = (fragCoord.xy * scale);
    float thickness = iMouse.y/iResolution.y * 30.0 * scale;
    float sharpness = iMouse.x/iResolution.x * scale;
    float t = test(st, center, radius, thickness, sharpness);
    out_color = vec4( mix(white, black, t), 1.0);
}

//
// Shadertoy End
//

void main() {
    mainImage(out_color, gl_FragCoord.xy);
}