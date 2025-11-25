import math

MAX_VALUE = 10000


def divisors(n):
    return [i for i in range(1, int(n / 2) + 1) if n % i == 0]


def d(n):
    acc = 0
    for i in divisors(n):
        acc += i
    return acc


amicable_numbers = set()

for a in range(1, MAX_VALUE + 1):
    b = d(a)
    if d(b) == a and a != b:
        amicable_numbers.add(a)
        amicable_numbers.add(b)

print(sum(amicable_numbers))
