package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ValidationException extends Throwable {

    public ValidationException(String message) {

        super(message);
        log.info("call ValidationException:" + message);
    }
}
