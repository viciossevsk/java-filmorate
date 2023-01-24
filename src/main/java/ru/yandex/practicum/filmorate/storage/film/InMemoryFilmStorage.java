package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();
    private int generatorId;

    @Override
    public Film getFilm(Integer id) {
        if (id != null) {
            if (films.containsKey(id)) {
                return films.get(id);
            } else {
                throw new FilmException("film id is empty");
            }
        } else {
            throw new FilmException("film id=" + id + " not found");
        }
    }

    @Override
    public List<Film> getAllFilms() {
        log.info(stringToGreenColor("getAllFilms... via GET /films"));
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) {
        validate(film);
        film.setId(++generatorId);
        films.put(film.getId(), film);
        log.info(stringToGreenColor("add film... via POST /film"));
        log.info(stringToBlueColor(film.toString()));
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        log.info(stringToGreenColor("update film... via PUT /film"));
        validate(film);
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

    public void validate(Film film) {
        log.trace(stringToGreenColor("validate for film"));
        if ((film.getName() == null) || (film.getName().isEmpty())) {
            throw new ValidationException("film name invalid");
        }
        if (film.getDescription().length() > 200) {
            throw new ValidationException("film description length > 200");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("film releaseDate < 28.12.1985");
        }
        if (film.getDuration() < 0) {
            throw new ValidationException("film duration < 0");
        }
    }

    @Override
    public void deleteFilm(Integer id) {
        log.info(stringToGreenColor("delete film... via DELETE /film"));
        Film film = getFilm(id);
        films.remove(film.getId());
    }
}