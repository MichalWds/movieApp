package movieApp.controller;

import movieApp.exception.MovieException;
import movieApp.exception.RatingException;
import movieApp.model.Lang;
import movieApp.model.Movie;
import movieApp.service.MovieService;
import movieApp.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private MovieService movieService;
    private RatingService ratingService;

    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() throws MovieException {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/lang/{lang}")
    public ResponseEntity<List<Movie>> findAllMoviesByLang(@PathVariable Lang lang) {
        return ResponseEntity.ok(movieService.findAllMoviesByLang(lang));
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

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Movie>> findById(@PathVariable int id) throws MovieException {
        Optional<Movie> findById = movieService.findById(id);
        if (findById.isPresent()) {
            return ResponseEntity.ok(findById);
        } else {
            return ResponseEntity.notFound().build();
        }
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
}
