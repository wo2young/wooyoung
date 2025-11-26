def add(a, b) :
    c = a + b
    return c

d = add(1,2) # 호이스팅 그런거 없음 선언 후 사용가능

print(d)

def sub(x, y) :
    return x-y

a = sub(1, 2)
print(a)

b = sub(y = 1, x = 2)
print(b)

# c = sub(1,2,3)
# c = sub(1)

def add_many(*x) :
    print(type(x), x)
    for data in x :
        print(data) 
           
add_many(1,2)
add_many()

def menu(pizza, *topping) : # 가변인자는 특성상 가장 마지막에 한번 밖에 못 옴
    print(pizza, topping)

menu('포테이토', '슈크림', '고구마', '베이컨')

def make_dict(**Kwargs) : # **은  dict 형태로 출력해주는 함수이다.
    print(type(Kwargs), Kwargs)
    
make_dict(x=5)
make_dict(x=5, y=10)
make_dict(key='value')
# make_dict(10) **로 받을 때는 key = value 형태만 가능
make_dict()

def test_complex(*a,**b) :
    print('a: ', a)
    print('b: ', b)

test_complex(1,2,3)
test_complex(1,2,3, x=5)
# test_complex(x=5, 1,2,3)
test_complex(x=5)

def test_tuple() :
    return 1,2
    return [1,2] # 이거 return이 여러개여도 에러가 안남

a = test_tuple()
print(type(a), a)
b = a[0]
c= a[1]

b,c = test_tuple()
print(type(b), c)

def user_info(name, job, nation='한국') :
# def user_info(name, nation='한국', job) : 디폴트 값은 무조건 끝에 둔다
    print(name, job, nation)
    
user_info('a','b','c')
user_info('a','b')

# 같은 이름으로 재 선언하면 덮어 써진다
def add(a, b) :
    c = a + b + 1
    return c

print(type(add))
a = add(1,2)
print(a)

add = 1 # 이러면 add라는 함수는 없어진다 int로 변경되었기 떄문에

def test1() :
    t = 1 # 후 그래도 지역변수가 있네

test1()
# print(t)

a = 1 
def vartest(a) :
    a = a+1
    
vartest(a)
print(a)

b = [1,2,3,4]
def test3(z) :
    z.append(5)

test3(b)
print(b)

test3(b)
print(b)

c = [1,2,3,4]
def test4(c) :
    c = [1,2,3,4,5]
test4(c)
print(c)

# 전역 변수를 바꾸는 방법
## 방법 1
a = 1
def vartest(a) :
    a = a + 1
    return a

a = vartest(a)
print(a)

# 방법 2
a = 1
def vartest2() :
    global a # =을 이용해서 값을 변경할 때 만 global을 쓰면 된다
    a = a + 1
    
vartest2()
print(a)

def vartest3() :
    a = 3
vartest3()
print(a)

# 람다 lambda
# x = 3
# def test_sqr(x) :
#     return x ** 2
# a = test_sqr(x)
# a = lambda x: x ** 2

# test_sqr(lambda x: x ** 2)

def test(x, y) :
    '''
        함수 설명
        
        Args :
        Parameters :
            x : int 첫번째 값
            y : int 두번째 값
        Returns :
            integer 두 값을 더한 값
    '''
    return x + y
test(1,2)

def testDebug(x, y) :
    x = x + 3
    y = y + 3
    return y

testDebug(1,2)


# TODO : 일단 실험용
def uiui() :
    a = a +1
    
    

    