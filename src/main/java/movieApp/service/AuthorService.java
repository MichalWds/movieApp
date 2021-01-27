package movieApp.service;

import movieApp.exception.AuthorException;
import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

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

    public List<Author> findAll() throws AuthorException {
        if (authorRepository.findAll().size() > 1) {
            return authorRepository.findAll();
        } else {
            throw new AuthorException();
        }
    }

    public Author save(Author author) {
        author.getMovieList().add(new Movie(1, "movieOne", "PL", 5, author));
        author.getMovieList().add(new Movie(2, "movieTwo", "ENG", 6, author));
        author.getMovieList().add(new Movie(3, "movieThree", "PL", 9, author));
        //check if name contains ONLY alphabets
        if (author.getName().matches("[a-zA-Z]+") && author.getLastName().matches("[a-zA-Z]+")) {
            return authorRepository.save(author);
        } else {
            System.out.println("Wrong name or lastName");
            throw new IllegalArgumentException();
        }
    }

    public Optional<List<Movie>> findAuthorAllMovies(int id, Author author) throws AuthorException {
        Optional<Author> authorId = authorRepository.findById(id);

        if (authorId.isPresent()) {
            return Optional.ofNullable(authorId.get().getMovieList());
        } else {
            throw new AuthorException();
        }
    }

    public double showAuthorMoviesAverageRating(int id) throws AuthorException {
        Optional<Author> authorId = authorRepository.findById(id);
        double ratingSum = 0.0;
        double average;
        if (authorId.isPresent()) {
            for (Movie movie : authorId.get().getMovieList()) {
                ratingSum = movie.getRate() + ratingSum;
            }
            average = ratingSum / authorId.get().getMovieList().size();
        } else {
            throw new AuthorException();
        }
        return Math.floor(average * 100) / 100;
    }
}