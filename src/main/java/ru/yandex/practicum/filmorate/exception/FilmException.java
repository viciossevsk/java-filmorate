package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FilmException extends Throwable {

    public FilmException(String message) {
        super(message);
        log.info("Вызвано исключение FilmException:" + message);
    }
}
