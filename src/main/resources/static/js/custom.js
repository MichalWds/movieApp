function findMovieById(){
    var movieByIdInput = document.getElementById('movieByIdInput').value;
    var movieById = document.getElementById('movieById');
    movieById.href = "http://localhost:9090/movies/" + movieByIdInput;
    window.location = movieById;
}

function findMovieByLang(){
    var movieByLangInput = document.getElementById('movieByLangInput').value;
    var movieByLang = document.getElementById('movieByLang');
    movieByLang.href = "http://localhost:9090/movies/lang/" + movieByLangInput;
    window.location = movieByLang;
}