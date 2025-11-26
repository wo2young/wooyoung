class FourCal :
    # 생성자
    # 생성할 때 무조건 실행되는 함수
    # 전달인자가 안 맞으면 생성이 안된다
    def __init__(self, x, y) : # __init__ 메서드 이름 자체가 생성자이다.
        self.x = x
        self.y = y
        
    def setData(self, x, y) :
        self.x = x
        self.y = y
    def add(self) :
        return self.x + self.y
# 뭐.뭐(FourCal)

a = FourCal(1, 2)
print(type(a))

b = FourCal(30, 45)
print(id(a), id(b)) # 메모리 위치라고 생각하면 좋다

a.setData(3,4)
print('a.x :', a.x)

b.setData(30,40)
print('b.x :', b.x)

print('a.add() :', a.add())
print('b.add() :', b.add())

class FourCal2 :
    
    def static_var(self) :
        pass
    def __init__(self) : 
        self.x = 5
        self.y = 10
    def add(self) :
        return self.x + self.y
        
class MoreCal(FourCal2) : # 이게 상속이라네
    def pow(self) :
        return self.x ** self.y

d = MoreCal()
print('d.x :', d.x)
print('d.add() :', d.add())
print('d.pow() :', d.pow())