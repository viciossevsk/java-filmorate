package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserException extends Throwable {

    public UserException(String message) {

        super(message);
        log.info("Вызвано исключение UserException:" + message);
    }
}
