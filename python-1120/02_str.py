print("hello")
print('world')

print("""HE 
LLO""") 
print('''WOR 
      LD''') 
 
print('안녕하세요  \'우영\' 반갑워요')
 
print(''' 
      글씨1\ 
      글씨\t2 
      글씨\n3 
      ''') 
 
print('처음' + ' 다음')
# print(1 + '다음 ')
# print(1 + '2') 
print (str(1) + ' 글씨') # str이 자바에서 to string이다
 
print('-' * 30) 
 
a = 'abcde'
print( a, len(a)) 

a = 14
b = "지금 기온은" + str(a) +"도 입니다"
print(b)

c = f"지금 기온은 {a}도 입니다"
print(c)

e = '지금 기온은 %d도 입니다' % 3.14 # %d는 정수타입으로 값이 들어감(버림)
print(e)

print('%9x' % 10) # %x는 16진수로 나온다

f = '오늘: {0}일, 내일 {1}일'.format(20, 21)
print(f)

a = 'hobby'
print(a.count('b')) # 몇 번 들어가 있는지 셈

print(a.find('b')) # 최초로 만나는 index(위치) 반환(0부터 시작)
print(a.find('x')) # 없으면 -1 반환, index()도 비슷한데 없으면 에러

b = 'abcd'
c = ';'
print(c.join(b))

a = '   a bc    '
print(a)
print(a.strip())

a = 'hobby'
b = a.replace('b', '!')
print(b)

a = "Life is too short"
b = a.split()   # 글씨를 배영로 만들어 줌
c = a.split(' ') # 전달인자가 없는 경우 기본값 공백
print(a, b, c)

d = a.split('i')
print(d)

a = 'https://naver.com'
print( a.startswith('https'))
print( a.endswith('.net'))