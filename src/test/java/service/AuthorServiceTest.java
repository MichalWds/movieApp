package service;

import movieApp.model.Author;
import movieApp.model.Movie;
import movieApp.repository.AuthorRepository;
import movieApp.repository.MovieRepository;
import movieApp.service.AuthorService;
import movieApp.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
        author = new Author("name", "lastName", List.of(movie));
    }

    @Test
    public void testFindByIdCorrectly(){
        when(authorRepository.findById(1)).thenReturn(Optional.of(author));

        Optional<Author> author = authorService.findById(1);

        assertThat(author).isNotEmpty();
    }
}
