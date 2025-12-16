show dbs

use sample_mflix

show collections

db.movies.find()

db.comments.find().count()

db.movies.aggregate([
  { $match: { year: 1995 } }
]);

db.movies.find({year: 1995})

db.movies.find({},{genres: 1})

db.movies.countDocuments({ year: 1995 })

db.comments.aggregate([
  { 
    $group: {
      _id: "$movie_id",
      commentCount: { $sum: 1 },   // Document 수를 합산
      sum_runtime: {$sum: "$runtime"}
    }
  }
]);

db.movies.aggregate([
  {
    $group: {
         _id: "$year", 
         totalMovies: { $sum: 1 }
    }
  }
]);

db.movies.aggregate([
  {
    $group: {
         _id: "$year",
         averageRating: { $avg: "$imdb.rating" }
    }
  }
]);

db.movies.aggregate([
  {
    $group: {
         _id: "$year",
         minRating: { $min: "$imdb.rating" },
         maxRating: { $max: "$imdb.rating" }
    }
  }
]);

db.movies.aggregate([
 {
   $group: {
     _id: "$year",
     titles: { $push: "$title" }
   }
 }
]);

db.movies.aggregate([
  {
    $group: {
         _id: "$year",
         genres: { $addToSet: "$genres" }
    }
  }
]);

db.movies.aggregate([
  {
    $group: {
         _id: "$year",
         genres: { $addToSet: "$genres" }
    }
  }
]);

db.movies.aggregate([
    {
        $group: {
            _id: "$year",
            avgTitleLength: { $avg: { $strLenCP: { $toString: "$title" } } }
        }
    }
]);

db.movies.aggregate([
    { $match: { year: { $gte: 2000 } } },
    { $count: "movies_since_2000" }
]);

db.movies.aggregate([
    { $sort: { "year": 1, "title": 1 } }
]);

db.movies.aggregate([
    { $sort: { "year": 1, "title": 1 } }
]);

db.movies.aggregate([
    { $sort: { "imdb.rating": -1 } },
    { $limit: 5 }
]);

db.movies.aggregate([
    { $project: { _id: 0, title: 1, year: 1 } }
]);

db.movies.aggregate([
    { 
        $project: { 
            title: 1,
            year: 1,
            releasedIn: { $concat: ["$title", " (", { $toString: "$year" }, ")"] }
        }
    }
]);
