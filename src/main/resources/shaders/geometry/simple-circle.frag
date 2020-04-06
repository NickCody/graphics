#version 400

uniform float iTime;
uniform vec2 iResolution;
uniform vec2 iMouse;

out vec4 out_color;

//
// coord - pixel to test
// center - center of circle
// radius - radius of circle
// tolerance - thickness of circle line
float test(vec2 coord, vec2 center, float radius, float tolerance) {
    float dist_to_center = sqrt(pow(coord.x - center.x,2.0) + pow(coord.y - center.y,2.0));

    if (abs(dist_to_center-radius) < tolerance)
        return 0.0;
    else
        return 1.0;
}

void main() {
    vec3 white = vec3(1.0, 1.0, 1.0);
    vec3 black = vec3(0.0, 0.0, 0.0);

    vec2 center = iResolution.xy/2.0;
    float radius = distance(center, iMouse);
    float t = test(gl_FragCoord.xy.xy, center, radius, 0.5);

    out_color = vec4( mix(white, black, t), 1.0);
}
