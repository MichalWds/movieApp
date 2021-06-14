function findMovieById() {
    var movieByIdInput = document.getElementById('movieByIdInput').value;
    var movieById = document.getElementById('movieById');
    movieById.href = "http://localhost:9090/movies/" + movieByIdInput;
    window.location = movieById;
}

function findMovieByLang() {
    var movieByLangInput = document.getElementById('movieByLangInput').value;
    var movieByLang = document.getElementById('movieByLang');
    movieByLang.href = "http://localhost:9090/movies/lang/" + movieByLangInput;
    window.location = movieByLang;
}

function findMovieByRatingH() {
    var movieByRatingHInput = document.getElementById('movieByRatingHInput').value;
    var movieByRatingH = document.getElementById('movieByRatingH');
    movieByRatingH.href = "http://localhost:9090/movies/hRating/" + movieByRatingHInput;
    window.location = movieByRatingH;
}

function findMovieByRatingL() {
    var movieByRatingLInput = document.getElementById('movieByRatingLInput').value;
    var movieByRatingL = document.getElementById('movieByRatingL');
    movieByRatingL.href = "http://localhost:9090/movies/lRating/" + movieByRatingLInput;
    window.location = movieByRatingL;
}

function findMovieByRatingEq() {
    var movieByRatingEqInput = document.getElementById('movieByRatingEqInput').value;
    var movieByRatingEq = document.getElementById('movieByRatingEq');
    movieByRatingEq.href = "http://localhost:9090/movies/rating/" + movieByRatingEqInput;
    window.location = movieByRatingEq;
}

function incrFunction() {
    alert("Rating Increased");
}

function decFunction() {
    alert("Rating Decreased");
}

function delMovie() {
    alert("Movie Deleted");
}