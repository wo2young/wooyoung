import time as t
import threading

def delay() :
    t.sleep(1)
    print('1초 뒤')

print('시작')
for i in range(3) :
    delay()
print('끝')

print('쓰레드 시작')
for i in range(3) :
    th = threading.Thread(target=delay)
    th.start()
print('쓰레드 끝')

def delay2(x) :
    t.sleep(1)
    print(x,'1초 뒤')
for i in range(3) :
    th = threading.Thread(target=delay2, args=(i,))
    # th.daemon = True # 백그라운드에서 돌린다
    th.start()