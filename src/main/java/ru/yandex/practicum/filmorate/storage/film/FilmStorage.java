package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    Film createFilm(@RequestBody Film film);

    Film updateFilm(@RequestBody Film film);

    void validate(Film film);

    List<Film> getAllFilms();


}
