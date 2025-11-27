class Guild :
    def __init__(self, name) :
        self.name = name
        self.users = []
        
    def GuildList(self) :
        if len(self.users) == 0 :
            print("유저가 없습니다.")
            return None
        else : 
            for i in range(len(self.users)) :
                print(self.users[i]._id, end="")
                if i+1 == len(self.users) :
                    print("") 
                                 
    def getRanker(self) :
        if len(self.users) == 0 :
            print("유저가 없습니다.")
            return None
        else :
            a = 0
            for i in range(len(self.users)) :
                if a < self.users[i]._level :
                    a = self.users[i]._level
                    b = self.users[i]._id
            print("우리 길드 랭커 : ", end="")
            return b
    
    def getBestSalse(self) :
        if len(self.users) == 0 :
            print("유저가 없습니다.")
            return None
        else :
            a = 0
            for i in range(len(self.users)) :
                if a < len(self.users[i]._inven) :
                    a = len(self.users[i]._inven)
                    b = self.users[i]._id
            print("우리 길드 창고 큰 사람 : ", end="")
            return b