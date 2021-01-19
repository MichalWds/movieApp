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
    public ResponseEntity<Optional<Movie>> update(@PathVariable int id){
        return ResponseEntity.ok(ratingService.increaseRating(id));
    }
}
