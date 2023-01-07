package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToBlueColor;
import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToGreenColor;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private Map<Integer, Film> films = new HashMap<>();
    private int generatorId;

    @GetMapping()
    public List<Film> getAllFilms() {
        log.info(stringToGreenColor("call method getAllFilms... via GET /films"));
        return new ArrayList<>(films.values());
    }

    @PostMapping()
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        log.info(stringToGreenColor("call method add film... via POST /film"));
        log.info(stringToBlueColor(film.toString()));
        validate(film);
        film.setId(++generatorId);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping()
    public Film updateFilm(@RequestBody Film film) throws FilmException, ValidationException {
        log.info(stringToGreenColor("call method update film... via PUT /film"));
        log.info(stringToBlueColor(film.toString()));
        validate(film);
        if (film.getId() != null) {
            if (films.containsKey(film.getId())) {
                films.replace(film.getId(), film);
            } else {
                throw new FilmException(stringToGreenColor("film id invalid"));
            }
        } else {
            throw new FilmException(stringToGreenColor("film id not found"));
        }
        return film;
    }

    public void validate(Film film) throws ValidationException {
        log.trace(stringToGreenColor("call method validate for film"));
        if ((film.getName() == null) || (film.getName().isEmpty())) {
            throw new ValidationException(stringToGreenColor("film name invalid"));
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException(stringToGreenColor("film description length > 200"));
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException(stringToGreenColor("film releaseDate < 28.12.1985"));
        }
        if (film.getDuration() < 0) {
            throw new ValidationException(stringToGreenColor("film duration < 0"));
        }
    }

}
