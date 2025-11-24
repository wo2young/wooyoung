import random

a = 10
b = 5 < a < 20
print(b)

if True : 
    print(1)
    print(2)
if False :
    print(3)
else : 
    pass

if a < 5 :
    print(1)
elif a : 
    print(2)
else : 
    print(3)

if not (a > 3) :
    print(a)

a = True
b = False
if a and b :
    print('and')
elif a or b : 
    print('or')

c = False
if not c :
    print('false')

if 1 != 0 :
    print('같지 않다')

money = 2000

if money >= 3000 :
    print('택시타자')
else :
    print('걸어가자')

card = False

if money >= 3000 or card  :
    print("택시타자")
else :
    print('걸어가자')

# 24일에 출석한 명부
student_24 = ['상명', '민서', '우영', '용군', '덕인']
#24일에 '동현'이 있다면 '복습' 출력
# 없다면 '진도나감' 츨력

if '동현' in student_24 :
    print('복습')
else :
    print('진도 나감')

money = -100
if money < 0 :
    money = 0

if money < 0 : money = 0

# score = input(90)
# print('>>>', score)

score = input('점수를 입력하세요:')
print('>>>', score)

score = int(score)

# 점수에 따라
# 90 이상이면 'A'
# 80  90  'B'
# if score >= 90 :
#     print('A')
# elif score >= 80 :
#     print('B')
# elif score >= 80 :
#     print('C')
# else :
#     print('F')

# else가 있다는건 하나 이상은 꼭 실행된다
# if score >= 90 :
#     print('A')
# elif score >= 80 :
#     print('B')
# elif score >= 80 :
#     print('C')
# else :
#     print('F')

result = '합격' if score >= 60  else '불합격'

result = '복습' if '동현' in student_24 else '진도나감'
print(result)

result = 'A' if score >= 90  else 'B' if score >=80 else 'C' if score >=70 else 'F'
print(result)

month = input('해당 월 입력하세요:')
print('>>>', month)
month = int(month)
if 0 < month <= 12 : 
    weather = '가을' if month >= 10 else '여름' if month >= 7 else '봄' if month >= 4 else '겨울'    
    print(weather)
else :
    print("해당 월은 존재하지 않습니다")


result = input('숫자를 입력하세요:')
print('>>>', result)
result = int(result)
if result % 2 == 0 :
    print('짝수')
elif result % 2 == 1 :
    print('홀수')
else :
    ("숫자만 입력해주세요")

result = input('가위 바위 보 중에 선택해 입력해세요: ')
print('>>>', result)
result2 = ['가위', '바위', '보']
result2 = result2[random.randint(0, 2)]
if result == '바위' :
    if result2 == '바위' :
        print('상대가 낸값 : ' + result2 + ' / 결과 : 비김')
    elif result2 == '가위' :
        print('상대가 낸값 : ' + result2 + ' / 결과 : 이김')
    elif result2 == '보' :
        print('상대가 낸값 : ' + result2 + ' / 결과 : 짐')

elif result == '가위' :
    if result2 == '가위' :
        print('상대가 낸값 : ' + result2 + ' / 결과 : 비김')
    elif result2 == '바위' :
        print('상대가 낸값 : ' + result2 + ' / 결과 : 짐')
    elif result2 == '보' :
        print('상대가 낸값 : ' + result2 + ' / 결과 : 이김')

elif result == '보' :
    if result2 == '보' :
        print('상대가 낸값 : ' + result2 + ' / 결과 : 비김')
    elif result2 == '가위' :
        print('상대가 낸값 : ' + result2 + ' / 결과 : 짐')
    elif result2 == '바위' :
        print('상대가 낸값 : ' + result2 + ' / 결과 : 이김')

else :
    print('잘못된 값이 입력되었습니다')

result = input('a 값을 입력해주세요: ')
print('>>>', result)
result2 = input('b 값을 입력해주세요: ')
print('>>>', result2)
if result > result2 :
    print("a에 값이 더 크네요 " + '/ a = ' + result)
elif result < result2 :
    print("b에 값이 더 크네요 " + '/ b = ' + result2)
elif result == result2 :
    print('값이 같습니다')
else :
    print('숫자로 입력해주세요')

