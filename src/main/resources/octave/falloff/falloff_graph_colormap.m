1;

function r = linearFalloff(x, d)
  r = 1-(x./d);
endfunction

range = [0,pi/2]

figure(1, 'position',[0,0,800,400]);

subplot (1, 2, 1)
fplot (@(x) cos(x) .** 2, range);
title("cos(x)^2")
legend("off")
subplot (1, 2, 2)
falloff_colormap;