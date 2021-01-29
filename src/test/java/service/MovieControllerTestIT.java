package service;

import movieApp.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {MovieService.class, MockMvc.class}) //had to add classes without it I couldn't Bean Mvc and service classes using just Autowired
@ExtendWith(MockitoExtension.class)
public class MovieControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/movies")).andDo(print()).andExpect(status().isOk());
    }


}
