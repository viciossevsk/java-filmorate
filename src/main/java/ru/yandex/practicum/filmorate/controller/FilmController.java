package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.List;

import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToBlueColor;
import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToGreenColor;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    FilmStorage filmStorage;

    public FilmController(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping()
    public List<Film> getAllFilms() {
        log.info(stringToGreenColor("call method getAllFilms... via GET /films"));
        return filmStorage.getAllFilms();
    }

    @PostMapping()
    public Film createFilm(@RequestBody Film film) {
        log.info(stringToGreenColor("call method add film... via POST /film"));
        log.info(stringToBlueColor(film.toString()));
        return filmStorage.createFilm(film);
    }

    @PutMapping()
    public Film updateFilm(@RequestBody Film film) {
        log.info(stringToGreenColor("call method update film... via PUT /film"));
        log.info(stringToBlueColor(film.toString()));
        return filmStorage.updateFilm(film);
    }
}
