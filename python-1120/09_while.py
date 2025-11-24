a = 0
while a < 5 :
    print(a)
    a += 1
print('>>>', a) # 정답은 5

money = 300
while money > 0 :
    print('남은 금액 :', money)
    money -= 100

count = 0
while count < 5 :
    count += 1
    if count == 2 :
        break
    print(f'count : {count}')
else : 
    print('break 안 만남')
# break로 나가면 else가 실행하지 않는다




