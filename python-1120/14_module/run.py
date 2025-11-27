import mod1

print(mod1.add(1,2))

from mod1 import *
print(add(1,2))
print(sub(1,2))

import sys
print(sys.path) # 모듈을 가져오는 경로 확인 

import folder.calc
folder.calc.add()

from folder import calc
calc.add()

from folder.calc import add
add()