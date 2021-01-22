package movieApp.controller;

import movieApp.model.Movie;
import movieApp.service.MovieService;
import movieApp.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private MovieService movieService;
    private RatingService ratingService;

    public MovieController(MovieService movieService, RatingService ratingService) {
        this.movieService = movieService;
        this.ratingService = ratingService;
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAll() {
        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Movie>> findById(@PathVariable int id) {
        Optional<Movie> byId = movieService.findById(id);
        if (byId.isPresent()) {
            return ResponseEntity.ok(byId);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Movie> save(@RequestBody Movie movie, @PathVariable int id) {
        return ResponseEntity.ok(movieService.save(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Movie>> updateByIncreasingRating(@PathVariable int id) {
        return ResponseEntity.ok(ratingService.increaseRating(id));
    }

    @PutMapping("/{id}/{dId}")
    public ResponseEntity<Optional<Movie>> updateByDecreasingRating(@PathVariable int id, @PathVariable int dId) {
        return ResponseEntity.ok(ratingService.decreasingRating(id, dId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        movieService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<List<Movie>> deleteAll() {
        movieService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
