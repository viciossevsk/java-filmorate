package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public interface FilmStorage {

    List<Film> getAllFilms();

    Film createFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilmById(Integer id);

    Film getFilmById(Integer id);

    List<Genre> getAllGenres();

    Genre getGenreById(Integer id);

    List<Rating> getAllRatings();

    Rating getRatingById(Integer id);

    List<Film> getMostPopularFilms(Integer count);

    void addLikeToFilm(int filmId, int userId);

    void removeLike(int filmId, int userId);
}
