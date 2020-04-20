#version 400

uniform float iTime;
uniform vec2 iResolution;
uniform vec2 iMouse;

out vec4 out_color;

//
// Shadertoy Start
//

#define hash(p)   ( 2.* fract(sin( (p) *  78.233 ) * 43758.5453) -1. )

// interpolation kernel
float P(float x)  { x = clamp(x,0.,1.); return x*x*(3.-2.*x); } // = smoothstep, BTW ;-)

#define draw(v,w)   max(0., w- abs(v-U.y) / fwidth(v-U.y) )     // antialiased curve y=v(x)

#define C(i) mod(floor((i)/2.),2.)

void mainImage( out vec4 O, vec2 u )
{
    float Z = 1.;
    vec2 R = iResolution.xy,
    U = Z * ( 2.*u - R ) / R.y;

    float x = U.x + 0.*iTime,
          i = floor(x), f = fract(x),                   // node id, local coord in cell
         g0 = hash(i),                                  // random slopes at nodes
         g1 = hash(i+1.),
         K0 = P(1.-f),                                  // gradients (i.e. tangents)
         K1 = P(f),
         P0 = g0* f * K0,                               // gradients * kernels
         P1 = g1*(f-1.) * K1,
          d = sin(62.28*f),                             // for dashs (applied to gradients)
         c0 = max( draw(P0,1.), draw(g0* f    ,.7)*d ), // left curve + gradient in cell
         c1 = max( draw(P1,1.), draw(g1*(f-1.),.7)*d ), // right curve + gradient in cell
          c = C(i)*c0 + C(i+1.)*c1                      // to visually separate neighbor wavelets
            + max(draw(K0,.7),draw(K1,.7));

    O = mod(i,2.) == 0.                                 // for consistant wavelet coloring
        ? vec4( c0, c1, c, 1 )                          // draw wavelets ( in red &  green
        : vec4( c1, c0, c, 1 );                         // + blue tint to separate from neighbors )
    O += draw(P0+P1,1.5)                                // reconstructed smooth noise function
      +  float( floor(u-R/2.).y == 0.)*d                // axis
      + max(0., 5.-R.y/Z/2.*length(vec2(fract(x+.5)-.5,U.y)) ); // nodes
      + .2* step(abs(U.y),1.);                          // [-1,1] range in grey

    O = pow(O, vec4(1./2.2) );                          // to sRGB

}


//
// Shadertoy End
//

void main() {
    mainImage(out_color, gl_FragCoord.xy);
}