package movieApp.controller;

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

    //wyswietl wszystkich autorow
    @GetMapping
    public ResponseEntity<List<Author>> findAllAuthors(){
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
//all movies autora
//    @GetMapping("/{authorId}/movies")
//    public ResponseEntity<Author> sss(@RequestBody Author author, @PathVariable int authorId){
//        return ResponseEntity.ok(authorService.findAllMovies(author));
//    }


//
//    //srednia ocena wszystkich filmow autora
//    @PutMapping("/{authorId/rating}")
//
//    }

}
