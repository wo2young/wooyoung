test_list = ['a', 'b', 'c']
for letter in test_list :
    print(letter)
    
for letter in 'hello' :
    print(letter)

d = {
    'a': 'aa',
    'b': 'bb',
    'c': 'cc',
}

# dict는 key로 반복 가능
for key in d :
    print(key)

d.keys()
d.values()
d.items()

for k, v in d.items() :
    print('k :', k, 'v : ', v)
    
r = range(5)
print('range(5), r')
r = range(0, 5)
print('range(0, 5), r')
print(list(r))
# range (시작숫자, 종료숫자+1, 건너뛰기)

for i in range(0, 5) :
    print(i)
     
print('-' * 20)
for i in range(2, 5) :
    print(i)
    
print('-' * 20)
for i in range(2, 10, 3) :
    print(i)
    
        
print('-' * 20)
j = 0
for i in range(1,101) :
    j = j + i
print(j)

j = 0
for i in range(1, 101) :
    if (i % 3 != 0) and (i % 5 != 0) :
        j += i     
print(j)

print('-' * 20)
arr = [51, 52, 53, 54, 55]
idx = 0
for value in arr :  
    print(idx, value)
    idx += 1
    
print('-' * 20)
for i in range(len(arr)) :
    print(i, arr[i]) # 당연히 이거 아님?
# 배열에 길이만큼 돌리면 되고 그러면 길이가 당연히 인덱스가 되는거잖아

print('-' * 20)
for i in range(2, 10) :
    print(str(i) + '단')
    for j in range(2,10) :
        print(str(i) + 'X' + str(j) + '=' + str((i * j))) # 이미 아까 해버렸다

t = 12   # 높이

for i in range(1, t+1):
    print(" " * (t - i), end="")       
    print("*" * (2 * i - 1))           # 오랜만에 하니까 헷갈려 뒤질뻔 했네
 
print('#' * 20)   
################################
# 리스트 컴프리헨션 comprehension

for i in range(1,10) :
    print(i)

    '''
        리스트 선언 a
        반복 1~9 만큼
            a에 하나씩 추가
        a 출력해서 확인
    '''
# 1,2,...,9 값을 가지는 리스트를 쉽게 만들어보자
a = []
for i in range(1,10) :
    a.append(i)
print(a)
print('-' * 20)
a = [i for i in range(1,10)]       
print(a)

# 17의 배수 5개
b = []

for i in range(1,6) :
    b.append(17 * i)
    
b = [(17 * i) for  i in range(1,6)]

for i in range(17,17*5+1,17) :
    b.append(i)
    
b = [i for i in range(17, (17*5)+1, 17)] # 느낌점 : 뭔가 합리적인듯 하지만 마음이 편치 않음
print(b)

c = []
for i in range(1,10) :
    if i % 3 == 0 :
        c.append(i)
        
for i in range(3, 10, 3) :
    c.append(i)

c = [i for i in range(1,10) if i % 3 == 0]
print(c)

c = []
for i in range(1,10) :
    if (i % 3 == 0) or (i % 5 == 0) :
        c.append(i)
        
c = [i for i in range(1,10) if (i % 3 == 0) or (i % 5 == 0)]

# 1~9까지에서 홀수는 그래도 짝수는 *2인 배열을 만들자

for i in range(1,10) :
    if i % 2 == 0 :
        c.append(i * 2)
    else :
        c.append(i)      
print(c)

c = [i*2 if i % 2 == 0 else i for i in range(1,10)]

print('-' * 20)
#구구단
d = []
for i in range(2,10) :
    for j in range(1,10) :
        d.append(f'{i}x{j}={i*j}')
print('--- d ---')        
print(d)        

e = [[f'{i}x{j}={i*j}' for j in range(1,10)]for i in range(2,10)]
# 이건 의도화 다르게 2차원 배열이 된다
print('--- e ---')
print(e)
# 그렇다면
f = [ f'{i}x{j}={i*j}' for i in range(2,10) for j in range(1,10)]
print('--- f ---')
print(f)

# dict 컴프리헨션

a = {}
for i in range(5) :
    if i % 2 == 0 :
        a[i] = i ** 2
        
a = { i : i**2 for i in range(5) if i % 2 == 0}
print(a)

print('-' * 20)
a = [11, 12, 13, 14]
for i, data in enumerate(a, 2) :
    print(i, data)

print('-' * 20)
for i, _ in enumerate(a,2) :
    print(i)
 
print('-' * 20)   
a = [10,20,30]
b = [7,8,9]
c = zip(a, b)
print(c)
d = list(zip(a, b))
print(d)

lang = 'Kor'
kr = ['소개', '채용']
en = ['About', 'Recruit']

# '소개' 를 출력
idx = 0
if lang == 'Kor' :
    print(kr[0])
elif lang == 'Eng' :
    print(en[0])
    
d_kr = {
    'common.header.about' : '소개',
    
}

d_en = {
    'common.header.about' : 'About',
    
}

'''
문제 1
1~n 까지 짝수의 합을 수하기
'''
n = 10
print('-' * 30)
result = 0
for i in range(1,n+1) :
    if i % 2 == 0 :
     result += i
print(result)   

result = sum([i for i in range(1,n+1) if i % 2 == 0])
print(result)
'''
문제 2
1~n 까지 숫자를 3개씩 옆으로 찍기
'''
print('-' * 30)
a = 1
b = 4
n = 22  
for i in range(1,n+1) :
    print("")
    if b < n :
            a += 3
            b += 3
    else : 
        break
    for j in range(a, b) :
        print(j, end="")

for i in range(1, n+1) :
    print(i, end="")
    if i % 3 == 0 :
        print("")     
'''
문제 3
+++*---
++***--
+*****-
*******
'''
print()
print('-' * 30)
t = 9 # 높이
for i in range(1, (t+1)) :
    print("+" * (t-i), end="")
    print('*' * ((i*2)-1), end="")
    print("-" * (t-i), end="")
    print("")
'''
문제 4
주사위 2개를 굴려서 중복을 제거한 경우의 수
'''
print('-' * 30)
for i in range(1,7) :
    print("주사위 별 경우의 수")
    for j in range((i),7) :
        print(f'{i},{j} / ', end="")
    print("")

'''
문제 5
키오스크 처럼 메뉴 2개와 종료 'x' 문자를 받아서
'1: 아아, 2: 라떼, x: 종료'
'x'가 입력되기 전까지 무한 반복
'''
print('')
print('-' * 30)
i = 0
j = 0
ice = 1000
latte = 2000
while True :
    print(f'1: 아아({ice}원), 2: 라떼({latte}원), x: 종료')
    a = input('')
    if a == 'x' :
        result = (ice*i) + (latte*j)
        print(f"계산할 금액은 {result}원 입니다~")
        print('이용해 주셔서 감사합니다!!')
        break
    elif a == '1' :
        i += 1
        print(f'아아 {i}잔')
    elif a == '2' :
        j += 1
        print(f'라떼 {j}잔')
    else :
        print('다시 골라주세요')