pkg load miscellaneous;

global dim=128;
global r=2
global frag = 1/(dim-1)

grays = [0:1/256:1;0:1/256:1;0:1/256:1]'
Z = zeros(dim,dim);
c = d = [-1 1];

function r = linearFalloff(x, d)
  r = 1-(x./d)
endfunction

function c = scale_range(x)
  global r;
  global frag;
  global dim;
  c = r * (clip(x, [1 dim])*frag - frag)-(r/2);
endfunction

function c = color_calc(x, y, p)
  c = (cos(x) * cos(y)).** p;
  #c = (1-sqrt(x*x + y*y)) .** p
endfunction

for x=1:dim
  for y=1:dim
    Z(x,y) = color_calc(scale_range(x), scale_range(y), 2);
  end
end

#figure(1, 'position',[0,0,800,800]);
colormap(grays);
imagesc(c, d, Z);
axis("square")