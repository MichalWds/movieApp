package movieApp.controller;

import movieApp.exception.MovieException;
import movieApp.exception.RatingException;
import movieApp.model.Lang;
import movieApp.model.Movie;
import movieApp.service.MovieService;
import movieApp.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final RatingService ratingService;

    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @GetMapping
    public String findAllMovies(@ModelAttribute Movie movie, Model model) throws MovieException {
        model.addAttribute("movie", movie);
        List<Movie> movies = movieService.findAll();
        movies.add(movie);
        model.addAttribute("movies", movies);
        return "movie/findAllMovies";
    }

    @GetMapping("/{id}")
    public String findMovieById(@PathVariable int id, Model model) throws MovieException {
        Optional<Movie> findById = movieService.findById(id);
        model.addAttribute("mov", findById);
        model.addAttribute("id", id);
        if (findById.isPresent()) {
            return "movie/findMovieById";
        } else {
            return MovieException.class.toString();
        }
    }

    @GetMapping("/lang/{lang}")
    public String findAllMoviesByLang(@PathVariable Lang lang, Model model, Movie movie) {

        model.addAttribute("movie", movie);
        List<Movie> movies = movieService.findAllMoviesByLang(lang);
        movies.add(movie);
        model.addAttribute("movies", movies);
        return "movie/findMovieByLang";
    }

    @GetMapping("/hRating/{number}")
    public String findAllMoviesWithRatingHigherThan(@PathVariable int number, Model model, Movie movie) {

        model.addAttribute("movie", movie);
        List<Movie> movies = movieService.findAllMoviesWithRatingHigherThan(number);
        movies.add(movie);
        model.addAttribute("movies", movies);
        return "movie/findMovieByRatingH";
    }

    @GetMapping("/lRating/{number}")
    public String findAllMoviesWithRatingLowerThan(@PathVariable int number, Model model, Movie movie) {

        model.addAttribute("movie", movie);
        List<Movie> movies = movieService.findAllMoviesWithRatingLowerThan(number);
        movies.add(movie);
        model.addAttribute("movies", movies);
        return "movie/findMovieByRatingL";
    }

    @GetMapping("/rating/{number}")
    public String findAllMoviesWithRatingEqualTo(@PathVariable int number, Movie movie, Model model) {

        model.addAttribute("movie", movie);
        List<Movie> movies = movieService.findAllMoviesWithRatingEqualToGivenRating(number);
        movies.add(movie);
        model.addAttribute("movies", movies);
        return "movie/findMovieByRatingEq";
    }

    @GetMapping("/save")
    public String save(Movie movie, Model model) throws RatingException {

        model.addAttribute("movie", movie);
        model.addAttribute("id", movie.getId());
        model.addAttribute("tittle", movie.getTittle());
        model.addAttribute("language", movie.getLanguage());
        model.addAttribute("rate", movie.getRate());
        return "movie/saveMovieForm";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("movie") Movie movie) throws RatingException {
        movieService.save(movie);
        return "movie/savedMovieForm";
    }

    @GetMapping("/inc")
    public String increase(Movie movie, Model model) {

        model.addAttribute("movie", movie);
        model.addAttribute("id", movie.getId());
        model.addAttribute("tittle", movie.getTittle());
        model.addAttribute("language", movie.getLanguage());
        model.addAttribute("rate", movie.getRate());
        return "movie/incRating";
    }

    @PostMapping("/inc")
    public String updateByIncreasingRating(@ModelAttribute("movie") Movie movie) throws MovieException {
        ratingService.increaseRating(movie.getId());
        return "movieOption";
    }

    @GetMapping("/dec")
    public String decrease(Movie movie, Model model) {

        model.addAttribute("movie", movie);
        model.addAttribute("id", movie.getId());
        model.addAttribute("tittle", movie.getTittle());
        model.addAttribute("language", movie.getLanguage());
        model.addAttribute("rate", movie.getRate());
        return "movie/decRating";
    }

    @PostMapping("/dec")
    public String updateByDecreasingRating(@ModelAttribute("movie") Movie movie) throws MovieException {
        ratingService.decreasingRating(movie.getId(), 1);
        return "movieOption";
    }

    @GetMapping("/del")
    public String deleteMovie(Movie movie, Model model) {

        model.addAttribute("movie", movie);
        model.addAttribute("id", movie.getId());
        model.addAttribute("tittle", movie.getTittle());
        model.addAttribute("language", movie.getLanguage());
        model.addAttribute("rate", movie.getRate());
        return "movie/delMovie";
    }

    @PostMapping("/del")
    public String delete(@ModelAttribute("movie") Movie movie) throws MovieException {
        movieService.deleteById(movie.getId());
        return "movieOption";
    }

//    @DeleteMapping
//    public ResponseEntity<List<Movie>> deleteAll() throws MovieException {
//        movieService.deleteAll();
//        return ResponseEntity.ok().build();
//    }
}
