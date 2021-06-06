package movieApp.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity(name="authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastName;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Movie> movieList = new ArrayList<>();

    public Author(int id, String name, String lastName, List<Movie> movieList) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.movieList = movieList;
    }

    public Author(String name, String lastName, List<Movie> movieList){
        this.name = name;
        this.lastName = lastName;
        this.movieList = movieList;
    }

    public Author(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }
}
