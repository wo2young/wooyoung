a = {}
a = dict()
print(type(a))

b = {
    "이름" : '김우영',
    '주소'  : '여주',
    '성별' : '남자',
    '오늘' : 21,
    '나이' : 24,
    '스킬' : {
        'java' : '상',
        'python' : '하'
            }
}
print(b)
print(b['나이'])
b['인사']='안녕'
print(b['인사'])

c = { 
    1 : '일',
    2 : '이'
     }
print(c[1])

d = {
    '이름' : '우영',
    '이름' : '김우영'
}
print(d['이름'])

e = b.keys()
print(e)
print(type(e))

print(b.values())

print( b.items()) #key value 튜플로 나옴

d.clear()
print(d)
d = {}

print(b.get('이름'))
print(b['이름'])
print(b.get('이름2')) #key가 없을떄 none를 돌려주며 에러가 없음

print(b.get('이름', '값없음'))
print(b.get('이름2', '값없음'))

print('이름' in b) #in은 key가 있는지 T/F로 나온다

e = b.pop('이름')
print(e, b)


