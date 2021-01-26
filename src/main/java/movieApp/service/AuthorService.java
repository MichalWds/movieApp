package movieApp.service;

import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> findById(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("NOT FOUND");
        } else {
            return authorRepository.findById(id);
        }
    }

    public List<Author> findAll(){
        if(authorRepository.findAll().size() > 1) {
            return authorRepository.findAll();
        }else {
            throw new RuntimeException("NOT FOUND ANY AUTHORS");
        }
    }

    public Author save(Author author) {
        //check if name contains ONLY alphabets
        if (author.getName().matches("[a-zA-Z]+") && author.getLastName().matches("[a-zA-Z]+")) {
            return authorRepository.save(author);
        } else {
            System.out.println("Wrong name or lastName");
            throw new IllegalArgumentException();
        }
    }

//    public List<Movie> findAllMovies(Author author){
//        return
//    }





}
