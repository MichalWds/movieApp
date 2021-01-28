package movieApp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //czytaj po id encji
    private int id;
    private String tittle;
    private String language;
    private int rate;

    @ManyToOne
    @JoinColumn(name ="author_id")
    @JsonIgnore
    private Author author;

    public Movie(){
    }

    public Movie(int id, String tittle, String language, int rate, Author author) {
        this.id = id;
        this.tittle = tittle;
        this.language = language;
        this.rate = rate;
        this.author = author;
    }

    public Movie(String tittle, String language, int rate, Author author) {
        this.tittle = tittle;
        this.language = language;
        this.rate = rate;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
