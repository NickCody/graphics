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
int test(vec2 coord, vec2 center, float radius, float tolerance) {
    float dist_to_center = sqrt(pow(coord.x - center.x,2.0) + pow(coord.y - center.y,2.0));
    float delta =dist_to_center-radius;

    if (abs(delta) < tolerance)
        return 0;
    else if (delta < 0)
        return -1;
    else
        return 1;
}

void main() {
    vec3 white = vec3(1.0, 1.0, 1.0);
    vec3 black = vec3(0.0, 0.0, 0.0);

    vec2 center = iResolution.xy/2.0;
    float radius = distance(center, iMouse);
    int t = test(gl_FragCoord.xy.xy, center, radius, 1.0);

    if (t > 0)
        out_color = vec4( black, 1.0);
    else if (t == 0)
        out_color = vec4( white, 1.0);
    else {
        vec2 uv = gl_FragCoord.xy/iResolution.xy;
        vec3 col = 0.5 + 0.5*cos(iTime+uv.xyx+vec3(0,2,4));
        out_color = vec4(col,1.0);
    }
}
