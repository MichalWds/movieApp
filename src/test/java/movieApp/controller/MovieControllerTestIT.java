package movieApp.controller;

import movieApp.model.Author;
import movieApp.model.Lang;
import movieApp.model.Movie;
import movieApp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc   //pakiet musi byc taki sam, inaczej nie czyta beanow, importy nie pomagaja
public class MovieControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Test
    public void testFindAllNotFound() throws Exception {
        mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().is(400));
    }

    @Test
    void shouldFoundMovies() throws Exception {
        Author author = new Author();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("someTittle", Lang.PL, 5, author));
        movieService.findAll();

        mockMvc.perform(get("/movies"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"id\":1,\"nickname\":\"adas\",\"health\":500,\"attack\":50,\"mana\":40,\"money\":40}")));
    }
}
