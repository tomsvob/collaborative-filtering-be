package app.DTO;

public class FilmDTO {
    private final long id;
    private String name;

    public FilmDTO() {
        this.id = -1;
        this.name = "";
    }

    public FilmDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
