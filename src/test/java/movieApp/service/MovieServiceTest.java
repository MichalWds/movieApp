package movieApp.service;

import movieApp.exception.MovieException;
import movieApp.exception.RatingException;
import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static movieApp.model.Lang.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private Movie movie;

    @Mock
    private Author author;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    public void setUp() {
        movie = new Movie(2, "SomeTittle", ENG, 4, author);
    }

    public List<Movie> prepareMovieList() {
        Movie movie1 = new Movie(2,"SomeTittle", ENG, 4, author);
        Movie movie2 = new Movie(3,"SomeTittle", ENG, 5, author);
        Movie movie3 = new Movie(4,"SomeTittle", PL, 6, author);
        Movie movie4 = new Movie(5,"SomeTittle", PL, 7, author);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);
        movieList.add(movie2);
        movieList.add(movie3);
        movieList.add(movie4);
        return movieList;
    }

    @Test
    public void testSaveMovieCorrectly() throws RatingException {
        Movie movie2 = new Movie( "SomeTittle", ENG,4, author);

        when(movieRepository.save(movie2)).thenReturn(movie);

        Movie movie =movieService.save(movie2);

        verify(movieRepository, times(1)).save(movie2);
        assertThat(movie.getId()).isEqualTo(2);
    }

    @Test
    public void testSaveMovieThrowRatingExceptionRatingTooHigh() {
        movie.setRate(20);
        assertThatExceptionOfType(RatingException.class).isThrownBy(() -> movieService.save(movie));
    }

    @Test
    public void testSaveMovieThrowRatingExceptionRatingTooLow() {
        movie.setRate(-3);
        assertThatExceptionOfType(RatingException.class).isThrownBy(() -> movieService.save(movie));
    }

    @Test
    public void testFindAllMoviesByLangNotEmptySet() {
        List<Movie> preparedMovieList = prepareMovieList();

        when(movieRepository.findAll()).thenReturn(preparedMovieList);
        List<Movie> filteredList = movieService.findAllMoviesByLang(ENG);
        List<Movie> checkedIfOnlyOneLangList = filteredList.stream().filter(movie -> movie.getLanguage().equals(ENG)).collect(Collectors.toList());

        verify(movieRepository, times(1)).findAll();
        assertThat(preparedMovieList.size()).isGreaterThan(filteredList.size());
        assertThat(filteredList.size() == 2);
        assertThat(checkedIfOnlyOneLangList.equals(filteredList));
    }

    @Test
    public void testFindAllMoviesByLangEmptySet() {

        List<Movie> preparedMovieList = prepareMovieList();

        when(movieRepository.findAll()).thenReturn(preparedMovieList);
        List<Movie> filteredList = movieService.findAllMoviesByLang(DE);

        verify(movieRepository, times(1)).findAll();
        assertThat(preparedMovieList.size()).isGreaterThan(filteredList.size());
        assertThat(filteredList.size() == 0);
    }

    @Test
    public void testFindAllMoviesWithRatingHigherThan() {
        int rating_number =5;
        List<Movie> preparedMovieList = prepareMovieList();

        when(movieRepository.findAll()).thenReturn(preparedMovieList);
        List<Movie> filteredList = movieService.findAllMoviesWithRatingHigherThan(rating_number);

        verify(movieRepository, times(1)).findAll();
        assertThat(preparedMovieList.size()).isGreaterThan(filteredList.size());
        assertThat(filteredList.size() == 2);
    }

    @Test
    public void testFindAllMoviesWithRatingLowerThan() {
        int rating_number =5;
        List<Movie> preparedMovieList = prepareMovieList();

        when(movieRepository.findAll()).thenReturn(preparedMovieList);
        List<Movie> filteredList = movieService.findAllMoviesWithRatingLowerThan(rating_number);

        verify(movieRepository, times(1)).findAll();
        assertThat(preparedMovieList.size()).isGreaterThan(filteredList.size());
        assertThat(filteredList.size() == 1);
    }

    @Test
    public void testFindAllMoviesWithRatingEqualToGivenRating() {
        int rating_number =5;
        List<Movie> preparedMovieList = prepareMovieList();

        when(movieRepository.findAll()).thenReturn(preparedMovieList);
        List<Movie> filteredList = movieService.findAllMoviesWithRatingEqualToGivenRating(rating_number);

        verify(movieRepository, times(1)).findAll();
        assertThat(preparedMovieList.size()).isGreaterThan(filteredList.size());
        assertThat(filteredList.size() == 1);
    }

    @Test
    public void testFindAllMovies() throws MovieException {
        when(movieRepository.findAll()).thenReturn(List.of(new Movie()));

        List<Movie> movies = movieService.findAll();

        assertThat(movies).isNotEmpty();
    }

    @Test
    public void testFindAllMoviesThrowMovieException() {

        assertThat(movieRepository.findAll().size()).isEqualTo(0);
        assertThatExceptionOfType(MovieException.class).isThrownBy(() -> movieService.findAll());
    }

    @Test
    public void testFindMovieById() throws MovieException {
        when(movieRepository.findById(2)).thenReturn(Optional.of(movie));

        Optional<Movie> movie = movieService.findById(2);

        assertThat(movie).isNotEmpty();
    }

    @Test
    public void testFindMovieByIdThrowMovieException() {
        assertThatExceptionOfType(MovieException.class).isThrownBy(() -> movieService.findById(movie.getId()));
    }


    @Test
    public void testDeleteMovieById() throws MovieException {
        when(movieRepository.findById(movie.getId())).thenReturn(Optional.ofNullable(movie));

        movieService.deleteById(movie.getId());

        verify(movieRepository, atLeastOnce()).deleteById(movie.getId());
    }

    @Test
    public void testDeleteMovieByIdThrowMovieException() {
        assertThatExceptionOfType(MovieException.class).isThrownBy(() -> movieService.deleteById(movie.getId()));
    }

    @Test
    public void testDeleteAllMovies() throws MovieException {
        List<Movie> preparedMovies = prepareMovieList();
        when(movieRepository.findAll()).thenReturn(preparedMovies);

        movieService.deleteAll();

        verify(movieRepository, atLeastOnce()).deleteAll();
    }
    @Test
    public void testDeleteAllMoviesThrowMovieException() {
        assertThatExceptionOfType(MovieException.class).isThrownBy(() -> movieService.deleteAll());
    }
}
