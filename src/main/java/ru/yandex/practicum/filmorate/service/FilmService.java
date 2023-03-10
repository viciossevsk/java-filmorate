package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    private final static String DIRECTOR_AND_TITLE = "title,director";
    private final static String TITLE_AND_DIRECTOR = "director,title";
    private final static String DIRECTOR = "director";
    private final static String TITLE = "title";

    public void deleteFilmById(int filmId) {
        filmStorage.deleteFilmById(filmId);
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public List<Film> getFilmsDirectorsSortBy(Integer directorId, String sortBy) {
        return filmStorage.getFilmsDirectorsSortBy(directorId, sortBy);
    }

    public Film createFilm(Film film) {
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }
        validateFilm(film);
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        validateFilm(film);
        return filmStorage.updateFilm(film);
    }

    public void addLikeToFilm(Integer filmId, Integer userId) {
        Film filmExist = filmStorage.getFilmById(filmId);
        User userExist = userStorage.getUserById(userId);
        filmStorage.addLikeToFilm(filmId, userId);
    }

    public Film getFilmById(Integer id) {
        return filmStorage.getFilmById(id);
    }

    public void removeLikeFromFilm(Integer filmId, Integer userId) {
        Film filmExist = filmStorage.getFilmById(filmId);
        User userExist = userStorage.getUserById(userId);
        filmStorage.removeLike(filmId, userId);
    }

    public List<Genre> getAllGenres() {
        return filmStorage.getAllGenres();
    }

    public Genre getGenreById(int genreId) {
        return filmStorage.getGenreById(genreId);
    }

    /**
     * ?????????????????? DESC
     */
    public List<Film> getMostPopularFilms(Integer count, Integer genreId, Integer year) {
        return filmStorage.getMostPopularFilms(count, genreId, year);
    }

    public List<Rating> getAllRatings() {
        return filmStorage.getAllRatings();
    }

    public Rating getRatingById(int ratingId) {
        return filmStorage.getRatingById(ratingId);
    }

    private boolean validateFilm(Film film) throws ValidationException {
        if (film.getDescription().length() > 200) {
            throw new ValidationException("Film description may not exceed 200 symbols");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("Invalid film release date");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Duration must be positive number");
        }
        return true;
    }

    public List<Film> getCommonFilms(Integer userId, Integer friendId) {
        return filmStorage.getCommonFilms(userId, friendId);
    }

    public List<Film> searchFilmsByQuery(String query, String by) {
        switch (by) {
            case DIRECTOR_AND_TITLE:
            case TITLE_AND_DIRECTOR:
                return filmStorage.searchFilmsByTitleDirector(query);
            case DIRECTOR:
                return filmStorage.searchFilmByDirector(query);
            case TITLE:
                return filmStorage.searchFilmByTitle(query);
            default:
                return filmStorage.getAllFilms();
        }
    }
}