show dbs

use testdb // collection 생성 혹은 불러오기

show collections

db.stats() /* 테이블 형태로 보여줌*/

db.createCollection("user")

show collections

db.user.drop()

db.collection2.drop()

db.createCollection("log", { capped : true, size : 5242880, max : 5000})

db.log.isCapped()

db.collection1.isCapped() // capped가 있으면 true 없으면 false

db.collection1.drop() // collection 삭제

db.dropDatabase() // DB 삭제(collection이 있어도 삭제)

use human
db.createCollection("users")

db.users.insertOne(
    {
        subject: "coding", author: "human", views:50
    }
)

db.users.find()

db.users.insertMany(
    [
      { subject: "coffee", author: "xyz", views: 50 },
      { subject: "Coffee Shopping", author: "efg", views: 5 },
      { subject: "Baking a cake", author: "abc", views: 90  },
      { subject: "baking", author: "xyz", views: 100 },
      { subject: "Café Con Leche", author: "abc", views: 200 },
      { subject: "Сырники", author: "jkl", views: 80 },
      { subject: "coffee and cream", author: "efg", views: 10 },
      { subject: "Cafe con Leche", author: "xyz", views: 10 },
      { subject: "coffees", author: "xyz", views: 10 },
      { subject: "coffee1", author: "xyz", views: 10 }
    ]
  )
  
db.users.find() // select처럼 실무에서는 조건없이 쓰지 마라

db.users.drop()

db.createCollection("users", {capped: true, sise: 100000})

db.users.insertMany([
	{ name:"David", age:45, address:"서울" },
	{ name:"DaveLee", age:25, address:"경기도" },
	{ name:"Andy", age:50, hobby:"골프", address:"경기도" },
	{ name:"Kate", age:35, address:"수원시" },
	{ name:"Brown", age:8 }
])

db.users.find()

db.users.find({ }, { name:1, address: 1})

db.users.find({ }, { name:1, address: 1, _id:0})

db.users.find({ address: '서울'})

db.users.find({ name: "DaveLee" }, {name: 1, age: 1, address: 1})

db.users.find({ name: "Kate" }, {name: 1, age: 1, address: 1, _id: 0})

db.users.find({age: {$gt: 25}})

db.users.find({age: 50, address: '경기도'}, {name: 1})

db.users.find({age: {$lt: 30}}, {name: 1, age: 1})

db.users.find({$or: [{name: 'Brown'}, {age: 35}]}, {age: 1})
db.users.find({$or: [{name: 'Brown'}, {age: 35}]})


db.users.find( { address: "경기도" } ).sort( { age: -1 } )

db.users.count()

db.users.find().count()

db.users.count( { address: { $exists: true } } )

db.users.distinct( "address" )

db.users.find().limit(1)

db.users.insertMany([
   { name: "유진", age: 25, hobbies: ["독서", "영화", "요리"] },
   { name: "동현", age: 30, hobbies: ["축구", "음악", "영화"] },
   { name: "혜진", age: 35, hobbies: ["요리", "여행", "독서"] }
])

db.users.find()

db.users.find( { hobbies: { $in: [ "축구", "요리" ] } } )

db.users.find( { hobbies: { $nin: [ "축구", "요리" ] } } )

db.users.updateMany( { age: { $gt: 25 } }, { $set: { address: "서울" } } )

db.users.find()

db.users.updateMany( { address: "서울" } , { $inc: { age: 3 } } )

db.users.find()

db.users.updateMany({age: {$gt: 40}}, {$set: {address: "수원"}})

db.users.find()

db.users.updateOne( { name: "유진" }, { $push: { hobbies: "운동" } } )

db.users.find()


db.users.deleteMany({age: {$lt: 30}}) // age가 30보다 작은 줄 삭제
db.users.deleteOne({age: 33}) // 동현 삭제

db.users.find()

