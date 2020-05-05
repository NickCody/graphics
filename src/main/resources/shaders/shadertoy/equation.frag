#version 400


uniform float iTime;
uniform vec2 iResolution;
uniform vec2 iMouse;

out vec4 out_color;

//
// Shadertoy Start
//


// interpolation kernel
// Credit: @FabriceNeyret2
// https://www.shadertoy.com/view/wsByWz
float P(float x)  { x = clamp(x,0.,1.); return x*x*(3.-2.*x); } // = smoothstep, BTW ;-)

#define draw(v,w)   max(0., w - abs(v-U.y) / fwidth(v-U.y) )    // antialiased curve y = v(x)

void mainImage( out vec4 O, vec2 u )
{
    float Z = 1.;
    vec2 R = iResolution.xy,
    U = Z * ( 2.*u - R ) / R.y,
    M = iMouse.xy/R.y;

    vec2 uv = u/iResolution.xy;
    vec3 col = 0.5 + 0.5*cos(iTime+uv.xyx+vec3(0,2,4));

    col += draw(M.y*sin(100.*M.x * U.x), 1.2);

    O = vec4(col, 1.);

}


//
// Shadertoy End
//

void main() {
    mainImage(out_color, gl_FragCoord.xy);
}