package app.Controllers;

import app.DTO.FilmDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class FilmListController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/film-list")
    public List<FilmDTO> film(@RequestParam(required=false, defaultValue="World") String name) {
//        TODO: implement
        System.out.println("==== requested film-list ====");
        List<FilmDTO> filmArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long id = counter.incrementAndGet();
            filmArrayList.add(new FilmDTO(id, "Name" + id));
        }
        return filmArrayList;
    }

    @GetMapping("/film-recommended")
    public List<FilmDTO> recommend() {
//        TODO: implement
        System.out.println("==== requested film-recommended ====");
        List<FilmDTO> filmArrayList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            long id = counter.incrementAndGet();
            filmArrayList.add(new FilmDTO(id, "Recommended Name" + id));
        }
        return filmArrayList;
    }

}