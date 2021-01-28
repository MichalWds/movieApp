package service;

import movieApp.exception.MovieException;
import movieApp.exception.RatingException;
import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.repository.MovieRepository;
import movieApp.service.MovieService;
import movieApp.service.RatingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieService movieService;

    @Mock
    private Movie movie;

    @Mock
    private Author author;

    @InjectMocks
    private RatingService ratingService;

    @BeforeAll
    public static void setUp(){

    }

    @Test
    public void testIncreasingRatingCorrectly() throws MovieException, RatingException {
        Movie movie = new Movie(1,"SomeTittle", "ENG", 4, author);
        movieService.save(movie);
        int movieRatingBeforeIncrease = movie.getRate();

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Optional<Movie> movieOptional = ratingService.increaseRating(movie.getId());

        assertThat(movieOptional).isNotEmpty();
        assertThat(movieRatingBeforeIncrease).isLessThan(movie.getRate());
        assertThat(movieRatingBeforeIncrease).isEqualTo(movie.getRate()-1);
    }

    @Test
    public void testIncreasingRatingThrowMovieException() throws MovieException, RatingException {
        Movie movie = new Movie(1,"SomeTittle", "ENG", 4, author);
        movieService.save(movie);

        assertThatExceptionOfType(MovieException.class).isThrownBy(() -> ratingService.increaseRating(movie.getId()));
        verify(movieRepository, atLeastOnce()).findById(movie.getId());
    }

    @Test
    public void testIncreasingRatingNotPossibleRatingHigherThanTen() throws MovieException, RatingException {
        Movie movie = new Movie(1,"SomeTittle", "ENG", 11, author);
        movieService.save(movie);
        int movieRatingBeforeIncrease = movie.getRate();

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Optional<Movie> movieOptional = ratingService.increaseRating(movie.getId());

        assertThat(movieOptional).isNotEmpty();
        assertThat(movieRatingBeforeIncrease).isEqualTo(movie.getRate());
        assertThat(movieRatingBeforeIncrease).isEqualTo(movie.getRate());
    }

    @Test
    public void testIncreasingRatingNotPossibleRatingLowerThanZero() throws MovieException, RatingException {
        Movie movie = new Movie(1,"SomeTittle", "ENG", -1, author);
        movieService.save(movie);
        int movieRatingBeforeIncrease = movie.getRate();

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Optional<Movie> movieOptional = ratingService.increaseRating(movie.getId());

        assertThat(movieOptional).isNotEmpty();
        assertThat(movieRatingBeforeIncrease).isEqualTo(movie.getRate());
        assertThat(movieRatingBeforeIncrease).isEqualTo(movie.getRate());
    }
}
