package movieApp.controller;

import movieApp.exception.AuthorException;
import movieApp.exception.MovieException;
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
    public ResponseEntity<Optional<Author>> findById(@PathVariable int authorId) throws AuthorException {
        Optional<Author> findById = authorService.findById(authorId);
        if (findById.isPresent()) {
            return ResponseEntity.ok(findById);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Author> save(@RequestBody Author author) {
        return ResponseEntity.ok(authorService.save(author));
    }

    @GetMapping("/{authorId}/movies")
    public ResponseEntity<Optional<List<Movie>>> findAuthorAllMovies(@PathVariable int authorId) throws AuthorException {
        return ResponseEntity.ok(authorService.findAuthorAllMovies(authorId));
    }

    @GetMapping("/{authorId}/rating")
    public ResponseEntity<Double> showAuthorMoviesAverageRating(@PathVariable int authorId) throws AuthorException {
        return ResponseEntity.ok(authorService.showAuthorMoviesAverageRating(authorId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) throws AuthorException {
        authorService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<List<Author>> deleteAll() throws AuthorException {
        authorService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
