package movieApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import movieApp.exception.RatingException;
import movieApp.model.Author;
import movieApp.model.Lang;
import movieApp.model.Movie;
import movieApp.repository.MovieRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static movieApp.model.Lang.PL;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc   //pakiet musi byc taki sam, inaczej nie czyta beanow, importy nie pomagaja
@ExtendWith(MockitoExtension.class)
public class MovieControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @MockBean
    private MovieRepository movieRepository;

    @Test
    public void testFindAllStatus404() throws Exception {
        mockMvc.perform(get("/movies"))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    void testShouldNotFoundAnyMoviesThrowException() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();

        when(movieRepository.findAll()).thenReturn((movieList));
        mockMvc.perform(get("/movies"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("MovieException: Not found any movie with given id.")));
    }

    @Test
    void testShouldFoundAllMovies() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "someTittle", PL, 5, author));

        when(movieRepository.findAll()).thenReturn(movieList);
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"tittle\":\"someTittle\",\"language\":\"PL\",\"rate\":5}]")));
    }

    @Test
    void testShouldNotFoundMovieByIdThrowException() throws Exception {
        Author author = new Author();
        Optional<Movie> movie = Optional.of(new Movie(1, "tittle", PL, 3, author));

        when(movieRepository.findById(movie.get().getId())).thenReturn(Optional.empty());
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("MovieException: Not found any movie with given id.")));
    }

    @Test
    void testShouldFoundMovieById() throws Exception {
        Author author = new Author();
        Optional<Movie> movie = Optional.of(new Movie(1, "tittle", PL, 3, author));

        when(movieRepository.findById(movie.get().getId())).thenReturn(Optional.of(movie.get()));
        mockMvc.perform(get("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"tittle\":\"tittle\",\"language\":\"PL\",\"rate\":3}")));
    }

    @Test
    void testShouldFindAllMoviesByLang() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "someTittle", PL, 5, author));
        movieList.add(new Movie(2, "someTittle2", Lang.ENG, 5, author));

        when(movieRepository.findAll()).thenReturn(movieList);
        mockMvc.perform(get("/movies/lang/PL"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"tittle\":\"someTittle\",\"language\":\"PL\",\"rate\":5}]")));
    }

    @Test
    void testShouldNotFindAllMoviesByLangReturnEmptySet() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "someTittle", PL, 5, author));
        movieList.add(new Movie(2, "someTittle2", Lang.ENG, 5, author));

        when(movieRepository.findAll()).thenReturn(movieList);
        mockMvc.perform(get("/movies/lang/DE"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[]")));
    }

    @Test
    void testShouldFindAllMoviesWithRatingHigherThan() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "someTittle", PL, 5, author));
        movieList.add(new Movie(2, "someTittle2", Lang.ENG, 7, author));

        when(movieRepository.findAll()).thenReturn(movieList);
        mockMvc.perform(get("/movies/hRating/5"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":2,\"tittle\":\"someTittle2\",\"language\":\"ENG\",\"rate\":7}]")));
    }

    @Test
    void testShouldFindAllMoviesWithRatingLowerThan() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "someTittle", PL, 5, author));
        movieList.add(new Movie(2, "someTittle2", Lang.ENG, 7, author));

        when(movieRepository.findAll()).thenReturn(movieList);
        mockMvc.perform(get("/movies/lRating/6"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"tittle\":\"someTittle\",\"language\":\"PL\",\"rate\":5}]")));
    }

    @Test
    void testShouldFindAllMoviesWithRatingEqualTo() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "someTittle", PL, 5, author));
        movieList.add(new Movie(2, "someTittle2", Lang.ENG, 7, author));

        when(movieRepository.findAll()).thenReturn(movieList);
        mockMvc.perform(get("/movies/rating/5"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"tittle\":\"someTittle\",\"language\":\"PL\",\"rate\":5}]")));
    }

    @Test
    void testsShouldDeleteAll() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "someTittle", PL, 5, author));
        movieList.add(new Movie(2, "someTittle2", Lang.ENG, 7, author));

        when(movieRepository.findAll()).thenReturn(movieList);
        mockMvc.perform(delete("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    void testsShouldNotDeleteAllBecauseOfEmptySet() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();

        when(movieRepository.findAll()).thenReturn(movieList);
        mockMvc.perform(delete("/movies"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("MovieException: Not found any movie with given id.")));
    }

    @Test
    void testShouldDeleteById() throws Exception {
        Author author = new Author();
        Optional<Movie> movie = Optional.of(new Movie(1, "tittle", PL, 3, author));

        when(movieRepository.findById(movie.get().getId())).thenReturn(movie);
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("")));
    }

    @Test
    void testShouldNotDeleteById() throws Exception {
        Author author = new Author();
        Optional<Movie> movie = Optional.of(new Movie(1, "tittle", PL, 3, author));

        when(movieRepository.findById(movie.get().getId())).thenReturn(Optional.empty());
        mockMvc.perform(delete("/movies/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("MovieException: Not found any movie with given id.")));
    }

    @Test
    void testShouldIncreaseMovieRating() throws Exception {
        Author author = new Author();
        Optional<Movie> movie = Optional.of(new Movie(1, "tittle", PL, 3, author));

        when(movieRepository.findById(movie.get().getId())).thenReturn(movie);
        mockMvc.perform(put("/movies/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"tittle\":\"tittle\",\"language\":\"PL\",\"rate\":4}")));
    }

    @Test
    void testShouldNotIncreaseMovieRatingMaxRating() throws Exception {
        Author author = new Author();
        Optional<Movie> movie = Optional.of(new Movie(1, "tittle", PL, 10, author));

        when(movieRepository.findById(movie.get().getId())).thenReturn(movie);
        mockMvc.perform(put("/movies/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"tittle\":\"tittle\",\"language\":\"PL\",\"rate\":10}")));
    }

    @Test
    void testShouldDecreaseMovieRating() throws Exception {
        Author author = new Author();
        Optional<Movie> movie = Optional.of(new Movie(1, "tittle", PL, 10, author));

        when(movieRepository.findById(movie.get().getId())).thenReturn(movie);
        mockMvc.perform(put("/movies/1/9"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"tittle\":\"tittle\",\"language\":\"PL\",\"rate\":1}")));
    }

    @Test
    void testShouldDecreaseMovieRatingNotLowerThanZero() throws Exception {
        Author author = new Author();
        Optional<Movie> movie = Optional.of(new Movie(1, "tittle", PL, 3, author));

        when(movieRepository.findById(movie.get().getId())).thenReturn(movie);
        mockMvc.perform(put("/movies/1/9"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"tittle\":\"tittle\",\"language\":\"PL\",\"rate\":0}")));
    }

    @Test
    void save() throws Exception {

        mockMvc.perform(post("/movies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"tittle\":\"tittle\",\"language\":\"PL\",\"rate\":4}\""))

                .andDo(print())
                .andExpect(status().isOk());
    }
}


