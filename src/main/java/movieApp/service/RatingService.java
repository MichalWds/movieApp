package movieApp.service;

import movieApp.exception.MovieException;
import movieApp.exception.RatingException;
import movieApp.model.Movie;
import movieApp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    private final MovieRepository movieRepository;

    public RatingService(MovieService movieService, MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Optional<Movie> increaseRating(int id) throws MovieException {
        Optional<Movie> movieById = movieRepository.findById(id);
        if (movieById.isPresent()) {
            int rating = movieById.get().getRate();

            System.out.println("Movie rating before increase: " + rating);
            if (rating < 10 && rating >=0) {
                movieById.get().setRate(++rating);
                movieRepository.save(movieById.get());
            }
            System.out.println("Movie rating after increase: " + rating);
        }else {
           throw new MovieException();
        }
        return movieById;
    }

    public Optional<Movie> decreasingRating(int id, int dId) throws MovieException {
        Optional<Movie> movieById = movieRepository.findById(id);
        if (movieById.isPresent()) {
            int rating = movieById.get().getRate();

            System.out.println("Movie rating before decreasing: " + rating);
            if (rating <= 10) {
                movieById.get().setRate(rating - dId);
                if (movieById.get().getRate() < 0) {
                    movieById.get().setRate(0);
                }
                movieRepository.save(movieById.get());
                System.out.println("Movie rating after decreasing: " + movieById.get().getRate());
            }
        }else {
            throw new MovieException();
        }
        return movieById;
    }
}
