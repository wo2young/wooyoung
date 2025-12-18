a = 'abc'
print(type(a))
print(1, a.strip())

a = []
print(type(a))
# print(2, a.strip())

a = [1,2,3,['a','b','c']]
print(a)
print(a[-1])
print(a[-1][1])

print(a[0:2])

b = 'abcde'
print(b[1:4])

print(a[:2])
print(a[1:])
print(a[1:100])

c = [1,2,3,4,5,6,7,8,9]
print(c[5:2])

print("-" * 30) 
print(c[:])
print(len(c[:]))
# for(i in c){
#     print(c[i])
# }
for i in range(len(c)):
    print(c[i])

del c[5] # 변수를 지운다
print(c[:])
print(len(c[:]))

a = [1,2,3,4,5]
del a[1:3]
print(a)

c = [1,2,3]
c.append(40)
print(c)
c.append([100,200])
print(c)

d = [78,3,4,6,31]
d.sort()
print(d)

d.reverse()
print(d)

e = 'eeerdddd'
print(e)

list1 = d.sort()
print(list1) # None

e = [1,2,3,4,5]
print(e.index(3))
print(3 in e)

e.insert(3,66)
print(e)

f = [1,2,3,4,3]
f.reverse()
f.remove(3)
f.reverse()
print(f)
g = f.pop()
print(g, f)

i = [1,2,3,3]
j = i.count(3)
print(j)

i.extend([10,30])
print(i)
i += [100,200]
print(i)


