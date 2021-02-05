package movieApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import movieApp.exception.RatingException;
import movieApp.model.Author;
import movieApp.model.Lang;
import movieApp.model.Movie;
import movieApp.repository.AuthorRepository;
import movieApp.repository.MovieRepository;
import movieApp.service.AuthorService;
import movieApp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static movieApp.model.Lang.ENG;
import static movieApp.model.Lang.PL;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AuthorControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorService authorService;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void testShouldFindAllAuthors() throws Exception {
        Author author = new Author();
        List<Author> authorList = new ArrayList<>();
        List<Movie> movieList = new ArrayList<>();
        authorList.add(new Author(1,"Adas", "Malysz", movieList));

        when(authorRepository.findAll()).thenReturn(authorList);
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"Adas\",\"lastName\":\"Malysz\",\"movieList\":[]}]")));
    }

    @Test
    void testShouldNotFindAnyAuthors() throws Exception {
        Author author = new Author();
        List<Author> authorList = new ArrayList<>();

        when(authorRepository.findAll()).thenReturn(authorList);
        mockMvc.perform(get("/authors"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("AuthorException: Not found any author with given id.")));
    }

    @Test
    void testShouldNotFindAuthorByIdThrowException() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        Optional<Author> author= Optional.of(new Author(1,"Adas", "Malysz", movieList));

        when(authorRepository.findById(author.get().getId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("AuthorException: Not found any author with given id.")));
    }

    @Test
    void testShouldFindAuthorById() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        Optional<Author> author= Optional.of(new Author(1,"Adas", "Malysz", movieList));

        when(authorRepository.findById(author.get().getId())).thenReturn(author);
        mockMvc.perform(get("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"name\":\"Adas\",\"lastName\":\"Malysz\",\"movieList\":[]}")));
    }

    @Test
    void testShouldFindAuthorAllMovies() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        Author author= new Author(1,"Adas", "Malysz", movieList);
        Movie movie = new Movie(3, "tittle", PL, 4, author);
        movieList.add(movie);

        when(authorRepository.findById(author.getId())).thenReturn(Optional.ofNullable(author));
        mockMvc.perform(get("/authors/1/movies"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":3,\"tittle\":\"tittle\",\"language\":\"PL\",\"rate\":4}]")));
    }

    @Test
    void testShouldNotFindAuthorAllMovies() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        Author author= new Author(1,"Adas", "Malysz", movieList);
        Movie movie = new Movie(1, "tittle", PL, 4, author);
        movieList.add(movie);


        when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/authors/1/movies"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("AuthorException: Not found any author with given id.")));
    }

    @Test
    void testShouldReturnAuthorMoviesAverageRating() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        Author author= new Author(1,"Adas", "Malysz", movieList);
        Movie movie = new Movie(1, "tittle", PL, 4, author);
        Movie movie2 = new Movie(2, "tittle", ENG, 6, author);
        movieList.add(movie);
        movieList.add(movie2);

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));
        mockMvc.perform(get("/authors/1/rating"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("5.0")));
    }

    @Test
    void testShouldNotReturnAuthorMoviesAverageRating() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        Author author= new Author(1,"Adas", "Malysz", movieList);

        when(authorRepository.findById(author.getId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/authors/1/rating"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("AuthorException: Not found any author with given id.")));
    }

    @Test
    void testShouldDeleteById() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        Optional<Author> author= Optional.of(new Author(1,"Adas", "Malysz", movieList));

        when(authorRepository.findById(author.get().getId())).thenReturn(author);
        mockMvc.perform(delete("/authors/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    void testShouldNotDeleteById() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        Optional<Author> author= Optional.of(new Author(1,"Adas", "Malysz", movieList));

        when(authorRepository.findById(author.get().getId())).thenReturn(Optional.empty());
        mockMvc.perform(delete("/authors/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("AuthorException: Not found any author with given id.")));
    }
}