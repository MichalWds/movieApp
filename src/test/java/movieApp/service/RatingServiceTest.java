package movieApp.service;

import movieApp.exception.MovieException;
import movieApp.exception.RatingException;
import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static movieApp.model.Lang.*;
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

    /**
     * Preparing object to tests
     */
    @BeforeEach
    public void setUp() throws RatingException {
        movie = new Movie(1, "SomeTittle", ENG, 4, author);
        movieService.save(movie);
    }

    @AfterEach
    public void cleanUp() throws MovieException {
        movieService.deleteAll();
    }

    /*
    INCREASING RATING TEST START
     */
    @Test
    public void testIncreasingRatingCorrectly() throws MovieException {
        int movieRatingBeforeIncrease = movie.getRate();
        int movieRatingAfterIncreasing = movie.getRate() + 1;

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Optional<Movie> movieOptional = ratingService.increaseRating(movie.getId());

        assertThat(movieOptional).isNotEmpty();
        assertThat(movieRatingBeforeIncrease).isLessThan(movie.getRate());
        assertThat(movieRatingAfterIncreasing).isEqualTo(movie.getRate());
        verify(movieRepository, atLeastOnce()).save(movie);
    }

    @Test
    public void testIncreasingRatingThrowMovieException() {
        assertThatExceptionOfType(MovieException.class).isThrownBy(() -> ratingService.increaseRating(movie.getId()));
        verify(movieRepository, atLeastOnce()).findById(movie.getId());
    }

    @Test
    public void testIncreasingRatingNotPossibleRatingHigherThanTen() throws MovieException {
        movie.setRate(11);
        int movieRatingBeforeIncrease = movie.getRate();

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Optional<Movie> movieOptional = ratingService.increaseRating(movie.getId());

        assertThat(movieOptional).isNotEmpty();
        assertThat(movieRatingBeforeIncrease).isEqualTo(movie.getRate());
    }

    @Test
    public void testIncreasingRatingNotPossibleRatingLowerThanZero() throws MovieException {
        movie.setRate(-1);
        int movieRatingBeforeIncrease = movie.getRate();

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Optional<Movie> movieOptional = ratingService.increaseRating(movie.getId());

        assertThat(movieOptional).isNotEmpty();
        assertThat(movieRatingBeforeIncrease).isEqualTo(movie.getRate());
    }
    /*
    INCREASING RATING TEST END
     */

    /*
    DeCREASING RATING TEST START
     */

    @Test
    public void testDecreasingRatingCorrectly() throws MovieException {
        int decreasing_number = 4;
        int movieRatingBeforeDecrease = movie.getRate();
        int movieRatingAfterDecrease = movie.getRate() - decreasing_number;

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Optional<Movie> movieOptional = ratingService.decreasingRating(movie.getId(), decreasing_number);

        assertThat(movieOptional).isNotEmpty();
        assertThat(movieRatingBeforeDecrease).isGreaterThan(movie.getRate());
        assertThat(movieRatingAfterDecrease).isEqualTo(movie.getRate());
        verify(movieRepository, atLeastOnce()).save(movie);
    }

    @Test
    public void testDecreasingRatingThrowsMovieException() {
        int decreasing_number = 4;
        assertThatExceptionOfType(MovieException.class).isThrownBy(() -> ratingService.decreasingRating(movie.getId(), decreasing_number));
        verify(movieRepository, atLeastOnce()).findById(movie.getId());
    }

    @Test
    public void testDecreasingRatingShouldBeZeroWhenDecreasingNumberIsGreaterThanRating() throws MovieException {
        movie.setRate(1);
        int decreasing_number = 4;
        int movieRatingAfterDecrease = 0;

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Optional<Movie> movieOptional = ratingService.decreasingRating(movie.getId(), decreasing_number);

        assertThat(movieOptional).isNotEmpty();
        assertThat(movieRatingAfterDecrease).isEqualTo(movie.getRate());
    }

    @Test
    public void testDecreasingRatingNotPossibleRatingGreaterThanMaximumRating() throws MovieException {
        movie.setRate(11);
        int decreasing_number = 4;
        int movieRatingAfterDecrease = movie.getRate();

        when(movieRepository.findById(1)).thenReturn(Optional.of(movie));
        Optional<Movie> movieOptional = ratingService.decreasingRating(movie.getId(), decreasing_number);

        assertThat(movieOptional).isNotEmpty();
        assertThat(movieRatingAfterDecrease).isEqualTo(movie.getRate());
    }
    /*
    DeCREASING RATING END
     */
}
