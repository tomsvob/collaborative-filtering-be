package app.Models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "film")
    private List<Rating> ratings;

    @ManyToMany(mappedBy = "recommendedFilms")
    private List<AppUser> recommendedFor;

    public Film() {
    }

    public Film(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
