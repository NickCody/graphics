1;

function r = linearFalloff(x, d)
  r = 1-(x./d)
endfunction

#x = 0:pi/60:pi/2
range = [0,pi/2]

figure(1, 'position',[0,0,1920,1080]);

subplot (2, 4, 1)
fplot (@cos, range);
title("cos(x)")
legend("off")
subplot (2, 4, 2)
fplot (@(x) cos(x) .** 2, range);
title("cos(x)^2")
legend("off")
subplot (2, 4, 3)
fplot (@(x) cos(x) .** 4, range);
title("cos(x)^4")
legend("off")
subplot (2, 4, 4)
fplot (@(x) cos(x) .** 8, range);
title("cos(x)^8")
legend("off")


subplot (2, 4, 5)
fplot (@(x) linearFalloff(x,pi/2), range);
title("lin(x)")
legend("off")
subplot (2, 4, 6)
fplot (@(x) linearFalloff(x,pi/2) .** 2, range);
title("lin(x)^2")
legend("off")
subplot (2, 4, 7)
fplot (@(x) linearFalloff(x,pi/2) .** 4, range);
title("lin(x)^4")
legend("off")
subplot (2, 4, 8)
fplot (@(x) linearFalloff(x,pi/2) .** 8, range);
title("lin(x)^8")
legend("off")


