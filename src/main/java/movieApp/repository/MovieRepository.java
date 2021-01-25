package movieApp.repository;

import movieApp.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository <Movie, Integer> {

    //zwroc filmy tylko z ratingiem wiekszym niz 5
    //zwroc filmy tylko z ratingiem mniejszym niz 5

    //authorRepo
}