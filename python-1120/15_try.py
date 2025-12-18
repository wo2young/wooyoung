try :
    4 / 0
except :
    print('4/0')
    
try :
    4 / 0
except Exception as e :
    print('[ERR]', e)


import traceback

try :
    4 / 0
except Exception as e :
    print('[ERR]', e)
    print('-' * 20)
    traceback.print_exc() # 에러 내용이 디테일하게 나온다
    
try :
    pass
except :
    pass

try :
    pass
finally :
    pass

try :
    pass
except :
    pass
finally :
    pass

try :       # 시도
    pass
except :    # 예외 발생
    pass
else :      # try에 예외가 없었을 때
    pass
finally :   # 무조건 마지막에 실행
    pass


try :
    a = [1,2,3]
    b = a[100]
except :
    pass

try :
    a = [1,2,3]
    b = a[100]
except :
    pass
else :
    print('몰라')
    
def make(t) :
    if(t == 1) :
        raise NotImplementedError
    else : 
        raise ZeroDivisionError

try :
    make(1)
except NotImplementedError as e :
    print('의도적으로 1 넣었을 떄 발생하는 예외')
except ZeroDivisionError :
    print('의도적으로 1이 아닐 때 발생하는 예외')
    
a = 'a'
assert a == 'a', '1'
# assert a == 'b', '2'

