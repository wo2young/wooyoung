# 내장 함수
# charater 정수를 문자 하나로 형변화
print('chr(65): ', chr(65)) # char 그러니까 아스키코드(ASCII)
# print('chr(A):', chr(A)) 이거는 안된다

# 문자 하나를 아스키 또는 유니코드로 변환
print('ord("A"):', ord("A"))
print('ord("Z"):', ord("Z"))
print('ord("한"):', ord("한"))

# 문자를 숫자로 형변환
print("int('123) :", int('123'))
print("int('f', 16) :", int('f',16))
print("int('1010', 2) :", int('1010',2))

def pp(x) :
    return x + 1

n = [1,2,3,4]
a = map(pp, n)
print('a :', a)
print('a :', list(a))

def key(k):
    print(k)
    return (k, d[k])   # (key, value) 형태로 반환

d = {
    'a': 1,
    'b': 2,
}

print(dict(map(key, d)))

def over3(x) :
    return x > 3
b = filter(over3, n)
print('b', b)
print('list(b)', list(b))

print(format(1234, ','))

print("max(n):", max(n))
print("min(n):", min(n))

income = -100
print( max( [income, 0]), '원 입금 되었습니다')
if income < 0 :
    income = 0
print(str(income) + '원 입금 되었습니다')

users = [
    {
        'id' : 'a',
        'level' : 12
    },
    {
        'id' : 'b',
        'level' : 20
    },
]
def get_level(user) :
    return user['level']

print( max(users, key=get_level))
print( max(users, key=lambda data : data['level']))

print('sum(n) :', sum(n))

print(sorted(n))
print(sorted(n, reverse=True))
n.sort()
print(n)

print(list(reversed(n)))