package movieApp.service;

import movieApp.exception.AuthorException;
import movieApp.exception.MovieException;
import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.repository.AuthorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static movieApp.model.Lang.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private Movie movie;

    @Mock
    private Author author;

    @InjectMocks
    private AuthorService authorService;

    @BeforeEach
    public void setUp() {
        author = new Author(2, "testName", "testLastName", new ArrayList<>());
        Movie movie = new Movie(2,"someTittle", PL, 4, author);
        Movie movie2 = new Movie(3,"someTittle2", ENG, 6, author);
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies.add(movie2);
        author.setMovieList(movies);
    }

    @Test
    public void testFindByIdCorrectly() throws AuthorException {
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));

        Optional<Author> author = authorService.findById(1);

        assertThat(author).isNotEmpty();
    }

    @Test
    public void testFindByIdThrowAuthorException() {
        assertThatExceptionOfType(AuthorException.class).isThrownBy(() -> authorService.findById(author.getId()));
    }

    @Test
    public void testFindAllAuthorsCorrectly() throws AuthorException {
        when(authorRepository.findAll()).thenReturn(List.of(author));

        List<Author> authors = authorService.findAll();

        assertThat(authors).isNotEmpty();
        assertThat(authors).size().isGreaterThan(0);
    }

    @Test
    public void testFindAllAuthorsThrowAuthorException() {
        assertThatExceptionOfType(AuthorException.class).isThrownBy(() -> authorService.findAll());
    }

    @Test
    public void testSaveCorrectly() {
        Author author2 = new Author(1, "Name", "LastName", new ArrayList<>(List.of(movie)));

        when(authorRepository.save(author2)).thenReturn(author);
        Author author = authorService.save(author2);

        verify(authorRepository, times(1)).save(author2);
        assertThat(author.getId()).isEqualTo(2);
    }

    @Test
    public void testSaveThrowIllegalArgumentExceptionWrongName() {
        author.setName("Name2");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> authorService.save(author));
    }

    @Test
    public void testSaveThrowIllegalArgumentExceptionWrongLastName() {
        author.setLastName("Last5Name");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> authorService.save(author));
    }

    @Test
    public void testFindAuthorAllMoviesThrowAuthorException(){

        assertThat(authorRepository.findById(author.getId())).isEmpty();
        assertThatExceptionOfType(AuthorException.class).isThrownBy(() -> authorService.findAuthorAllMovies(author.getId()));
    }

    @Test
    public void testFindAuthorAllMoviesCorrectly() throws AuthorException {

        when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));

        Optional<List<Movie>> authorAllMovies = authorService.findAuthorAllMovies(author.getId());

        assertThat(authorAllMovies).isNotEmpty();
        assertThat(authorAllMovies.get().size()).isEqualTo(2);
    }

    @Test
    public void testShowAuthorMoviesAverageRatingThrowAuthorException() {

        assertThat(authorRepository.findById(author.getId())).isEmpty();
        assertThatExceptionOfType(AuthorException.class).isThrownBy(() -> authorService.showAuthorMoviesAverageRating(author.getId()));
    }

    @Test
    public void testShowAuthorMoviesAverageRatingCorrectly() throws AuthorException {

        when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));

        double rating = authorService.showAuthorMoviesAverageRating(author.getId());

        assertThat(rating).isEqualTo(5.0);
    }

    @Test
    public void testDeleteAuthorById() throws MovieException, AuthorException {
        when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));

        authorService.deleteById(author.getId());

        verify(authorRepository, atLeastOnce()).deleteById(author.getId());
    }

    @Test
    public void testDeleteAuthorByIdThrowMovieException() {
        assertThatExceptionOfType(AuthorException.class).isThrownBy(() -> authorService.deleteById(movie.getId()));
    }

    @Test
    public void testDeleteAllAuthors() throws MovieException, AuthorException {
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(new Author());

        when(authorRepository.findAll()).thenReturn(authors);

        authorService.deleteAll();

        verify(authorRepository, atLeastOnce()).deleteAll();
    }

    @Test
    public void testDeleteAllAuthorsThrowMovieException() {
        assertThatExceptionOfType(AuthorException.class).isThrownBy(() -> authorService.deleteAll());
    }
}
