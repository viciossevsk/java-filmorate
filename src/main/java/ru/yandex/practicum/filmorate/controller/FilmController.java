package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable("id") Integer filmId) {
        return filmService.getFilmById(filmId);
    }

    @GetMapping("/director/{id}")
    public List<Film> getFilmsDirectorsSortBy(@PathVariable("id") Integer directorId,
                                              @RequestParam(defaultValue = "name", required = false) String sortBy) {
        return filmService.getFilmsDirectorsSortBy(directorId, sortBy);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    /**
     * пользователь ставит лайк фильму
     * <p>
     * id - фильм
     * userId - ИД юзера
     */
    @PutMapping("/{id}/like/{userId}")
    public void addLikeToFilm(@PathVariable("id") Integer filmId, @PathVariable Integer userId) {
        filmService.addLikeToFilm(filmId, userId);
    }

    /**
     * пользователь удаляет лайк.
     *
     * id - фильм
     * userId - ИД юзера
     */
    @DeleteMapping("/{id}/like/{userId}")
    public void removeLikeFromFilm(@PathVariable("id") Integer filmId, @PathVariable Integer userId) {
        filmService.removeLikeFromFilm(filmId, userId);
    }

    /**
     * возвращает список из первых count фильмов по количеству лайков
     *
     * count - количество фильмов
     * genreId - фильтр по жанру фильма
     * year - фильтр по году выпуска фильма
     * return Если значение параметра count не задано, верните первые 10
     */
    @GetMapping("/popular")
    public List<Film> getMostPopularFilmsWithGenreYear(@RequestParam(defaultValue = "10", required = false) Integer count,
                                                       @RequestParam(required = false) Integer genreId,
                                                       @RequestParam(required = false) Integer year) {
        return filmService.getMostPopularFilms(count, genreId, year);
    }

    @DeleteMapping("{filmId}")
    public void deleteFilmById(@PathVariable("filmId") Integer filmId) {
        filmService.deleteFilmById(filmId);
    }

    @GetMapping("/common")
    public List<Film> getCommonFilms(@RequestParam Integer userId,
                                     @RequestParam Integer friendId) {
        return filmService.getCommonFilms(userId, friendId);
    }

    @GetMapping("/search")
    public List<Film> search(@RequestParam(value = "query", required = false) String query,
                             @RequestParam(value = "by", required = false) String by) {
        return filmService.searchFilms(query, by);//вызываем метод поиска
    }

}