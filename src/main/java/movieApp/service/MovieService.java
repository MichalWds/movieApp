package movieApp.service;

import movieApp.exception.RatingException;
import movieApp.model.Movie;
import movieApp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie save(Movie movie) throws RatingException {

        if (movie.getRate() > 10 || movie.getRate() < 0) {
            throw new RatingException();
        } else {
            return movieRepository.save(movie);
        }
    }

    public List<Movie> findAllPLMovies() {
        List<Movie> movieList = movieRepository.findAll();

        return movieList.stream().filter(movie -> movie.getLanguage().equals("PL")).collect(Collectors.toList());
    }

    public List<Movie> findAllENGMovies() {
        List<Movie> movieList = movieRepository.findAll();

        return movieList.stream().filter(movie -> movie.getLanguage().equals("ENG")).collect(Collectors.toList());
    }

    public List<Movie> findAllMoviesWithRatingHigherThanFive() {
        List<Movie> movieList = movieRepository.findAll();
        return movieList.stream().filter(movie -> movie.getRate() >= 5).collect(Collectors.toList());
    }

    public List<Movie> findAllMoviesWithRatingLowerThanFive() {
        List<Movie> movieList = movieRepository.findAll();
        return movieList.stream().filter(movie -> movie.getRate() < 5).collect(Collectors.toList());
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(int id) {
        return movieRepository.findById(id);
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public void deleteById(int id) {
        movieRepository.deleteById(id);
    }
}
