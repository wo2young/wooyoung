class User :
    def __init__(self, id, level, inven) :
        self._id = id
        self._level = level
        self._inven = inven
        
    def getId(self) :
        return self._id
        
    def setId(self, _id) :
        self._id = _id
        
    def getLevel(self) :
        return self._level
    
    def setLevel(self, _level) :
        if _level < 0:
            print("레벨은 0 이상이어야 합니다.")
            return
        self._level = _level
        
    def getInven(self) :
        return self._inven
    
    def setInven(self, _inven) :
        self._inven = _inven
    
    def printInfo(self) :
        print("id : ", self._id, end=" / ")
        print("level : ", self._level, end=" / ")
        print("inven", self._inven)
        
    def __str__(self) :
        return f'(str)User(id:{self._id}, level:{self._level}, inven:{self._inven})'
    
    def __repr__(self) :
        return f'(repr)User(id:{self._id}, level:{self._level}, inven:{self._inven})'


