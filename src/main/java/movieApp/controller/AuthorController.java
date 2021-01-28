package movieApp.controller;

import movieApp.exception.AuthorException;
import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> findAllAuthors() throws AuthorException {
        return ResponseEntity.ok(authorService.findAll());
     }

    @GetMapping("/{authorId}")
    public ResponseEntity<Optional<Author>> findById(@PathVariable int authorId) {
        Optional<Author> findById = authorService.findById(authorId);
        if (findById.isPresent()) {
            return ResponseEntity.ok(findById);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{authorId}")
    public ResponseEntity<Author> save(@RequestBody Author author, @PathVariable int authorId) {
        return ResponseEntity.ok(authorService.save(author));
    }

    @GetMapping("/{authorId}/movies")
    public ResponseEntity<Optional<List<Movie>>> findAuthorAllMovies(@RequestBody Author author, @PathVariable int authorId) throws AuthorException {
        return ResponseEntity.ok(authorService.findAuthorAllMovies(authorId, author));
    }

    @GetMapping("/{authorId}/rating")
    public ResponseEntity<Double> showAuthorMoviesAverageRating(@RequestBody Author author, @PathVariable int authorId) throws AuthorException {
        return ResponseEntity.ok(authorService.showAuthorMoviesAverageRating(authorId));
    }
}
