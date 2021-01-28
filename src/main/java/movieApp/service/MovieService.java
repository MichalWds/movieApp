package movieApp.service;

import movieApp.exception.MovieException;
import movieApp.exception.RatingException;
import movieApp.model.Lang;
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

    public List<Movie> findAllMoviesByLang(Lang lang) {
        List<Movie> movieList = movieRepository.findAll();

        return movieList.stream().filter(movie -> movie.getLanguage().equals(lang)).collect(Collectors.toList());
    }

    public List<Movie> findAllMoviesWithRatingHigherThan(int number) {
        List<Movie> movieList = movieRepository.findAll();
        return movieList.stream().filter(movie -> movie.getRate() > number).collect(Collectors.toList());
    }

    public List<Movie> findAllMoviesWithRatingLowerThan(int number) {
        List<Movie> movieList = movieRepository.findAll();
        return movieList.stream().filter(movie -> movie.getRate() < number).collect(Collectors.toList());
    }

    public List<Movie> findAllMoviesWithRatingEqualToGivenRating(int number) {
        List<Movie> movieList = movieRepository.findAll();
        return movieList.stream().filter(movie -> movie.getRate() == number).collect(Collectors.toList());
    }

    public List<Movie> findAll() throws MovieException {
        if (movieRepository.findAll().size() > 0) {
            return movieRepository.findAll();
        } else {
            throw new MovieException();
        }
    }

    public Optional<Movie> findById(int id) throws MovieException {
        if (movieRepository.findById(id).isPresent()) {
            return movieRepository.findById(id);
        } else {
            throw new MovieException();
        }
    }

    public void deleteAll() throws MovieException {
        if (movieRepository.findAll().size() > 0) {
            movieRepository.deleteAll();
        } else {
            throw new MovieException();
        }
    }

    public void deleteById(int id) throws MovieException {
        if (movieRepository.findById(id).isPresent()) {
            movieRepository.deleteById(id);
        } else {
            throw new MovieException();
        }
    }
}
