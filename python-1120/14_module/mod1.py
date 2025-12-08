def add(x,y) :
    print('mod의 add 실행')
    return x + y

def sub(x,y) :
    print('mod의 sub 실행')
    return x - y

def test() :
    print('test 실행')
    print(add(1,2))

print('__name__ : ', __name__)
if __name__ == '__name__' :
    test()