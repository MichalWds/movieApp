package movieApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    public void testFindAllNotFound() throws Exception {
        mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().is(400));
    }

    @Test
    void shouldFoundMovies() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie(1, "someTittle", Lang.PL, 5, author));

        given(movieRepository.findAll()).willReturn(movieList);
        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[{\"id\":1,\"tittle\":\"someTittle\",\"language\":\"PL\",\"rate\":5}]")));
    }

    @Test
    void shouldFoundMovie() throws Exception {
        Author author = new Author();
        Optional<Movie> movie = java.util.Optional.of(new Movie(1, "tittle", Lang.PL, 3, author));


    }

}


