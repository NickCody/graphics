#version 400

uniform float iTime;
uniform vec2 iResolution;
uniform vec2 iMouse;

out vec4 out_color;


const vec3 white = vec3(1., 1., 1.);
const vec3 black = vec3(0., 0., 0.);
const vec3 gray  = vec3(.2, .2, .3);

// circleTest
//
// coord      - pixel to test
// center     - center of circle
// radius     - radius of circle
// width      - thickness of circle line
// pixelWidth - blendiness

vec3 circleTest(vec2 coord, vec2 center, float radius, float width, float pixelWidth) {
    float delta = distance(coord, center)-radius;

    // From shadertoy default shader
    vec3 inside = .5 + .5*cos(iTime+coord.xyx+vec3(0,2,4));

    float blend = smoothstep(0., pixelWidth, abs(delta) - width);

    if (delta  < 0.)
    return mix(white, inside, blend); // rgb -> white (inside  edge)
    else if (delta  > 0.)
    return mix(white, black, blend);  // white -> black (outside edge)
    else
    return white;					  // can't decide

}

// Credit: https://thebookofshaders.com/edit.php#10/ikeda-simple-grid.frag
//
float grid(vec2 st, float res){
    vec2 grid = fract(st*res);
    return 1.-(step(res,grid.x) * step(res,grid.y));
}


void mainImage(out vec4 out_color, vec2 fragCoord) {

    float aspect     = iResolution.y/iResolution.x,
    pixelWidth = 1./iResolution.x,
    width      = 2./iResolution.x,
    maxPetals  = 12.,
    br         = .1;


    vec2 dMouse = iMouse.xy == vec2(0) ? iResolution.xy/2. : iMouse.xy,
    cen    = vec2(1., aspect) / 2.,
    q      = fragCoord.xy/iResolution.x,
    nq     = q - cen,
    m      = dMouse.xy/iResolution.x,
    nm     = m - cen;

    float a   = atan(nq.y,nq.x);
    float p   = ceil(maxPetals * m.x);
    float vr  = 8. * br * aspect * nm.y; // -1./petals keeps bottom row as vr=0
    float s   = sin(p * a);
    float r   = br  + vr * s;

    vec3 c = circleTest(
    q,
    cen,
    r,
    width,
    pixelWidth
    );

    c = c == vec3(0.) ? gray * grid(fragCoord, maxPetals/iResolution.x ) : c;

    out_color = vec4(c, 1.);
}

//
// Shadertoy End
//

void main() {
    mainImage(out_color, gl_FragCoord.xy);
}