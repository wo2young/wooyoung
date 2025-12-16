use sample_mflix

db.movies.find()

db.movies.aggregate([
    {
      $avg : "$movies_runtime"    
    }
])

db.movies.find().count()


