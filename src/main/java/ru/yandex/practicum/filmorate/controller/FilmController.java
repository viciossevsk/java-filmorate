package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {

    private Map<Integer, Film> films = new HashMap<>();
    private int generatorId;

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @PostMapping("/film")
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        film.validate();
        film.setId(++generatorId);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping("/film")
    public void updateFilm(@RequestBody Film film) throws FilmException, ValidationException {
        film.validate();
        if (film.getId() != null) {
            films.put(film.getId(), film);
        } else {
            throw new FilmException("film id not found");
        }

    }

}
