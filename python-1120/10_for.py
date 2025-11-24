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
    

       
        
        