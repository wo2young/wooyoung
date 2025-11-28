f = open('text.txt', 'w') # w는 없으면 만들고 있으면 덮어쓰기
f.close()

f = open('text.txt', 'w', encoding='utf-8') # w는 없으면 만들고 있으면 덮어쓰기
for i in range(1, 10) :
    f.write(f'{i} line 안녕 \n')
f.close()

print('-' * 20)
f = open('text.txt', 'r', encoding='utf-8')
while True :
    line = f.readline() 
    print(line, end='')
    if not line :
        break
f.close()

print('-' * 20)
f = open('text.txt', 'r', encoding='utf-8')
r = f.read() # 파일 통으로 읽기(보통 그림 파일같은 것)
print(r)
f.close()

print('-' * 20)
f = open('text.txt', 'r', encoding='utf-8')
for line in f :
    print(line)
f.close()

# print('-' * 20)
f = open('text.txt', 'a', encoding='utf-8')
f.write('추가 줄\n')
f.close()

# with : close() 자동으로 해준다
with open('text.txt', 'a', encoding='utf-8') as f :
    f.write('추가 줄2\n')

import sys
print(sys.argv)

