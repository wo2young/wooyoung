# 날짜와 시간
from datetime import datetime as d
from datetime import date
now = d.now() # 오늘 날짜와 시간
print(now, type(now) )
print(now.strftime('$Y년 %m월 %d일 %H시 %M분 %S초'))
print(now.strftime('$Y년 %m월 %d일 %p %I시 %M분 %S초.%f'))

print(now.date()) # 오늘 날짜

start = date(2025, 11, 19)
end = date(2025, 11, 28)
print( end -  start)

# 시간
import time
print(time.time()) # 1970년 1월 1일 자정부터 지난 시간(초 단위)
before = time.time()
# 시간을 측정하고 싶은 코드 작성
for i in range(10) :
    print(i)
    # pass
    # time.sleep(0.5)
after = time.time()
print(f' 걸린 시간 : {after - before}')

import random as r
print(r.random()) # 0 <= rand <1
print(r.randint(1,6)) # 1~6 사이의 무작위 정수
a = [1,2,3,4,5]
print(r.choice(a)) # 시퀀스 객체에서 무작위 선택
r.shuffle(a)
print(a)

# json
import json
j = '''
    {
        "name": "우영",
        "age": 24
    }
'''
d = json.loads(j)
print(d, type(d))

j2 = json.dumps(d)
print(j2)

d2 = {
    'hasCar?' : True
}
j3 = json.dumps(d2)
print(j3)
print(json.loads(j3))

import urllib.request as req
respons = req.urlopen("https://naver.com")
print(respons)
print(respons.read().decode('utf-8'))

import webbrowser
webbrowser.open('https://naver.com')
webbrowser.open('https://daum.net')
