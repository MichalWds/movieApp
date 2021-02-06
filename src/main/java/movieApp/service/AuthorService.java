package movieApp.service;

import movieApp.exception.AuthorException;
import movieApp.exception.MovieException;
import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static movieApp.model.Lang.ENG;
import static movieApp.model.Lang.PL;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<Author> findById(int id) throws AuthorException {
        Optional<Author> author = authorRepository.findById(id);

        if (author.isPresent()) {
            return author;
        } else {
            throw new AuthorException();
        }
    }

    public List<Author> findAll() throws AuthorException {
        List<Author> authorList = authorRepository.findAll();
        if (!authorList.isEmpty()) {
            return authorList;
        } else {
            throw new AuthorException();
        }
    }

    public Author save(Author author) {

        for (Movie movie : author.getMovieList()) {
            if (movie.getAuthor() == null) {
                movie.setAuthor(author);
            }
        }
        //check if name contains ONLY alphabets
        if (author.getName().matches("[a-zA-Z]+") && author.getLastName().matches("[a-zA-Z]+")) {
            return authorRepository.save(author);
        } else {
            System.out.println("Wrong name or lastName");
            throw new IllegalArgumentException();
        }
    }

    public Optional<List<Movie>> findAuthorAllMovies(int id) throws AuthorException {
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

    public void deleteAll() throws AuthorException {
        List<Author> authorList = authorRepository.findAll();
        if (!authorList.isEmpty()) {
            authorRepository.deleteAll();
        } else {
            throw new AuthorException();
        }
    }

    public void deleteById(int id) throws AuthorException {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
        } else {
            throw new AuthorException();
        }
    }
}