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

//    @GetMapping
//    public ResponseEntity<List<Movie>> findAll() throws MovieException {
//        return ResponseEntity.ok(movieService.findAll());
//    }

    @GetMapping
    public String findAllMovies(@ModelAttribute Movie movie, Model model) throws MovieException {
        model.addAttribute("movie", movie);
        List<Movie> movies = movieService.findAll();
        movies.add(movie);
        model.addAttribute("movies", movies);
        return "findAllMovies";
    }

    @GetMapping("/{id}")
    public String findMovieById(@PathVariable int id, Model model) throws MovieException {
        Optional<Movie> findById = movieService.findById(id);
        model.addAttribute("mov", findById);
        model.addAttribute("id", id);
        if (findById.isPresent()) {
            return "findMovieById";
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
        return "findMovieByLang";
    }

    @GetMapping("/hRating/{number}")
    public ResponseEntity<List<Movie>> findAllMoviesWithRatingHigherThan(@PathVariable int number) {
        return ResponseEntity.ok(movieService.findAllMoviesWithRatingHigherThan(number));
    }

    @GetMapping("/lRating/{number}")
    public ResponseEntity<List<Movie>> findAllMoviesWithRatingLowerThan(@PathVariable int number) {
        return ResponseEntity.ok(movieService.findAllMoviesWithRatingLowerThan(number));
    }

    @GetMapping("/rating/{number}")
    public ResponseEntity<List<Movie>> findAllMoviesWithRatingEqualTo(@PathVariable int number) {
        return ResponseEntity.ok(movieService.findAllMoviesWithRatingEqualToGivenRating(number));
    }


    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody Movie movie) throws RatingException {
        return ResponseEntity.ok(movieService.save(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Movie>> updateByIncreasingRating(@PathVariable int id) throws MovieException {
        return ResponseEntity.ok(ratingService.increaseRating(id));
    }

    @PutMapping("/{id}/{dId}")
    public ResponseEntity<Optional<Movie>> updateByDecreasingRating(@PathVariable int id, @PathVariable int dId) throws MovieException {
        return ResponseEntity.ok(ratingService.decreasingRating(id, dId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws MovieException {
        movieService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<List<Movie>> deleteAll() throws MovieException {
        movieService.deleteAll();
        return ResponseEntity.ok().build();
    }

    //    @GetMapping("/{id}")
//    public ResponseEntity<Optional<Movie>> findById(@PathVariable int id) throws MovieException {
//        Optional<Movie> findById = movieService.findById(id);
//        if (findById.isPresent()) {
//            return ResponseEntity.ok(findById);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
