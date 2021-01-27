package service;

import movieApp.exception.MovieException;
import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.repository.MovieRepository;
import movieApp.service.RatingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RatingServiceTest {

    @Mock
    private MovieRepository movieRepository;

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
    public void testIncreasingRatingCorrectly() throws MovieException {

        Movie movie = new Movie(1,"SomeTittle", "ENG", 4, author);

        ratingService.increaseRating(movie.getId());

        verify(movieRepository,atLeastOnce()).findById(1);


    }
}
