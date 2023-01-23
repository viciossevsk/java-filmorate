package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmorateApplicationTests {

    private FilmController filmController;
    private InMemoryFilmStorage inMemoryFilmStorage;
    private UserController userController;
    private InMemoryUserStorage inMemoryUserStorage;

    @BeforeEach
    void create() {
        inMemoryFilmStorage = new InMemoryFilmStorage();
        inMemoryUserStorage = new InMemoryUserStorage();

        filmController = new FilmController(new FilmService(inMemoryFilmStorage, inMemoryUserStorage));
        userController = new UserController(new UserService(inMemoryUserStorage));
    }

    @AfterEach
    void destroy() {

        filmController = null;
        userController = null;
    }

    @Test
    void correctFilm() throws ValidationException {

        Film corrFilm = Film.builder()
                .id(1)
                .name("Жара")
                .description("Документальный фильм про солнце")
                .releaseDate(LocalDate.of(1990, 5, 6))
                .duration(100)
                .build();

        Film film = Film.builder()
                .name("Жара")
                .description("Документальный фильм про солнце")
                .releaseDate(LocalDate.of(1990, 5, 6))
                .duration(100)
                .build();

        assertEquals(corrFilm, filmController.createFilm(film), "Incorrect record");
    }

    @Test
    void nameIsEmptyFilm() throws ValidationException {

        Film film = Film.builder()
                .name("")
                .description("Документальный фильм про солнце")
                .releaseDate(LocalDate.of(1990, 5, 6))
                .duration(100)
                .build();

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            filmController.createFilm(film);
        }, "ValidationException was expected");

        Assertions.assertEquals("film name invalid", thrown.getMessage());

    }

    @Test
    void descriptionIsLongFilm() throws ValidationException {

        Film film = Film.builder()
                .name("Жара")
                .description("Документальный фильм про солнцеДокументальный фильм про солнцеДокументальный фильм про " +
                                     "солнцеДокументальный фильм про солнце" +
                                     "Документальный фильм про солнцеДокументальный фильм про солнцеДокументальный " +
                                     "фильм про солнцеДокументальный фильм про солнце")
                .releaseDate(LocalDate.of(1990, 5, 6))
                .duration(100)
                .build();

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            filmController.createFilm(film);
        }, "ValidationException was expected");

        Assertions.assertEquals("film description length > 200", thrown.getMessage());

    }

    @Test
    void releaseDateIsOldFilm() throws ValidationException {

        Film film = Film.builder()
                .name("Жара")
                .description("Документальный фильм про солнце")
                .releaseDate(LocalDate.of(1790, 5, 6))
                .duration(100)
                .build();

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            filmController.createFilm(film);
        }, "ValidationException was expected");

        Assertions.assertEquals("film releaseDate < 28.12.1985", thrown.getMessage());

    }

    @Test
    void durationLessThanZeroFilm() throws ValidationException {

        Film film = Film.builder()
                .name("Жара")
                .description("Документальный фильм про солнце")
                .releaseDate(LocalDate.of(1990, 5, 6))
                .duration(-100)
                .build();

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            filmController.createFilm(film);
        }, "ValidationException was expected");

        Assertions.assertEquals("film duration < 0", thrown.getMessage());

    }

    @Test
    void correctUser() throws ValidationException {

        User corrUser = User.builder()
                .id(1)
                .email("qwer@ui.ru")
                .login("qwerty")
                .name("pavel")
                .birthday(LocalDate.of(1990, 5, 6))
                .build();

        User user = User.builder()
                .email("qwer@ui.ru")
                .login("qwerty")
                .name("pavel")
                .birthday(LocalDate.of(1990, 5, 6))
                .build();

        assertEquals(corrUser, userController.createUser(user), "Incorrect record");
    }

    @Test
    void emailIsIncorrectUser() throws ValidationException {

        User user = User.builder()
                .email("query.ru")
                .login("qwerty")
                .name("pavel")
                .birthday(LocalDate.of(1990, 5, 6))
                .build();

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        }, "ValidationException was expected");

        Assertions.assertEquals("user email invalid", thrown.getMessage());

    }

    @Test
    void loginIsInvalidFilm() throws ValidationException {

        User user = User.builder()
                .email("qwer@ui.ru")
                .login("")
                .name("pavel")
                .birthday(LocalDate.of(1990, 5, 6))
                .build();

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        }, "ValidationException was expected");

        Assertions.assertEquals("user login invalid", thrown.getMessage());

    }

    @Test
    void birthdayInFutureUser() throws ValidationException {

        User user = User.builder()
                .email("qwer@ui.ru")
                .login("qwerty")
                .name("pavel")
                .birthday(LocalDate.of(2055, 5, 6))
                .build();

        ValidationException thrown = assertThrows(ValidationException.class, () -> {
            userController.createUser(user);
        }, "ValidationException was expected");

        Assertions.assertEquals("user birthday in future", thrown.getMessage());

    }


}

