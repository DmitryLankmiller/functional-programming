import math

SUM_VALUE = 1000

for b in range(2, 998):
    for a in range(1, b):
        c = math.sqrt(a**2 + b**2)
        if a + b + c == SUM_VALUE:
            print("[" ,a, b, c, "]",a*b*c)
            break
