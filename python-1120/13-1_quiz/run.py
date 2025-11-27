from pack.Guild import Guild
from pack.User import User

# 더미 길드 생성
guild = Guild("테스트 길드")

# 유저 생성
u1 = User("A", 10, ["검", "방패"])
u2 = User("B", 20, ["활"])
u3 = User("C", 12, ["너클"])
u4 = User("D", 17, ["단검"])
u5 = User("E", 22, ["지팡이", "모자", "물약"])

print(u1)

users = [u1, u2, u3, u4, u5]

u1.printInfo()
u2.printInfo()
u3.printInfo()
u4.printInfo()
u5.printInfo()

# Guild에 User 추가 (반복문)
for u in users :
    guild.users.append(u)

# 출력 테스트
guild.GuildList()          # 전체 유저 리스트
print(guild.getRanker())   # 가장 레벨 높은 유저
print(guild.getBestSales()) # 가장 inven이 큰 유저

