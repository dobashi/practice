#!/usr/bin/env python

import sys
import random

RANDOM_SEED = 314
random.seed(RANDOM_SEED)

a = 10
b = -4
X_RANGE = (-10, 10)
ERROR_RANGE = (-20, 20)

if len(sys.argv) < 2:
    sys.stderr.write("{} <number of output>\n".format(sys.argv[0]))
    sys.exit(-1)

for n in range(0, int(sys.argv[1])):
    # x, y, eを計算して出力する
    x = random.random() * (X_RANGE[1] - X_RANGE[0]) + X_RANGE[0]
    y = a * x + b
    e = random.random() * (ERROR_RANGE[1] - ERROR_RANGE[0]) + ERROR_RANGE[0]
    print("{},{}".format(x, y+e))
