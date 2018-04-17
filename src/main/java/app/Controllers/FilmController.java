package app.Controllers;

import app.DTO.FilmDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class FilmController {
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/film")
    public FilmDTO film(@RequestParam(required=false, defaultValue="World") String name) {
        System.out.println("==== requested film ====");
        return new FilmDTO(counter.incrementAndGet(), name);
    }

}