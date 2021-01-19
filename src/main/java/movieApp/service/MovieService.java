package movieApp.service;

import movieApp.model.Movie;
import movieApp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    private List<Movie> movieList = List.of(new Movie(1, "Killer", "PL", 5),
            new Movie(2, "Killer2", "PL", 6),
            new Movie(3, "Killer3", "PL", 7));

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(int id) {
        return movieRepository.findById(id);
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public void deleteById(int id) {
        movieRepository.deleteById(id);
    }

}
