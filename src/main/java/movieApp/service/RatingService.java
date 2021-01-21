package movieApp.service;

import movieApp.model.Movie;
import movieApp.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    private MovieService movieService;
    private MovieRepository movieRepository;

    public RatingService(MovieService movieService, MovieRepository movieRepository) {
        this.movieService = movieService;
        this.movieRepository = movieRepository;
    }

    public Optional<Movie> increaseRating(int id){
        movieRepository.findById(id);
        System.out.println("Movie rate before increase: " + movieRepository.findById(id).get().getRate());

        if(movieRepository.findById(id).isPresent() && movieRepository.findById(id).get().getRate() < 10){
            movieRepository.findById(id).get()
                    .setRate((movieRepository.findById(id).get().getRate())+1);
        }
        System.out.println("Movie rate after increase: "+ movieRepository.findById(id).get().getRate());

        return movieRepository.findById(id);
    }

    public Optional<Movie> decreasingRating(int id, int dId){
        movieRepository.findById(id);
        System.out.println("Movie rate before decreasing: " + movieRepository.findById(id).get().getRate());

        if(movieRepository.findById(id).isPresent() && movieRepository.findById(id).get().getRate() <= 10){
            movieRepository.findById(id).get()
                    .setRate((movieRepository.findById(id).get().getRate())-dId);
        }
        if(movieRepository.findById(id).get().getRate()<0){
            movieRepository.findById(id).get().setRate(0);
        }
        System.out.println("Movie rate after decreasing: "+ movieRepository.findById(id).get().getRate());

        return movieRepository.findById(id);
    }
}
