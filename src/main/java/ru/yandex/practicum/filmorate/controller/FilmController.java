package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private Map<Integer, Film> films = new HashMap<>();
    private int generatorId;

    @GetMapping()
    public List<Film> getAllFilms() {
        log.info("Вызываем метод getAllFilms... через GET /films");
        return new ArrayList<>(films.values());
    }

    @PostMapping()
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        log.info("Вызываем метод добавления фильма... через POST /film");
        film.validate();
        film.setId(++generatorId);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping()
    public Film updateFilm(@RequestBody Film film) throws FilmException, ValidationException {
        log.info("Вызываем метод изменения фильма... через PUT /film");
        film.validate();
        if (film.getId() != null) {
            if (films.containsKey(film.getId())) {
                films.replace(film.getId(), film);
            } else {
                throw new FilmException("film id invalid");
            }
        } else {
            throw new FilmException("film id not found");
        }
        return film;
    }

}
