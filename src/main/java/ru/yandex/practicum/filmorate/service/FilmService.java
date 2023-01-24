package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToGreenColor;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;


    public List<Film> getAllFilms() {
        log.info(stringToGreenColor("call method getAllFilms in FilmStorage... via GET /films"));
        return filmStorage.getAllFilms();
    }

    public Film createFilm(Film film) {
        log.info(stringToGreenColor("call method add film in FilmStorage... via POST /film"));
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        log.info(stringToGreenColor("call method update film in FilmStorage... via PUT /film"));
        return filmStorage.updateFilm(film);
    }

    public void deleteFilm(Integer id) {
        filmStorage.deleteFilm(id);
    }

    public void addLikeToFilm(Integer id, Integer userId) {
        log.info(stringToGreenColor("add like film..."));
        Film film = filmStorage.getFilm(id);
        User user = userStorage.getUser(userId);
        film.addLike(user.getId());
    }

    public Film getFilm(@PathVariable Integer id) {
        return filmStorage.getFilm(id);
    }

    public void removeLikeFromFilm(Integer id, Integer userId) {
        log.info(stringToGreenColor("remove like from film..."));
        Film film = filmStorage.getFilm(id);
        film.removeLike(userId);
    }

    /**
     * сортируем DESC
     */
    public List<Film> getMostPopularFilms(Integer count) {
        log.info(stringToGreenColor("getAllFilms... "));
        return filmStorage.getAllFilms().stream()
                .sorted(Comparator.comparing(film -> film.getLikes().size() * -1))
                .limit(count)
                .collect(Collectors.toList());
    }


}
