a = ()
print(type(a))

b = (1,2,3)
print(b[0])
# tuple는 final개념이라 값을 수정할 수 없음

c = (1,) # 단 하나의 값을 가지는 튜플의 경우 (값,)로 선언해야 한다

d = 1,2,3,4
print( type(d))

print(d.index(2))
print(d.count(1))
print(len(d[:])) # 확인 해보니 원본을 훼손하지 않는 함수는 사용 가능 
print(d * 3) # 단, +와 * 가능
print(c + d)