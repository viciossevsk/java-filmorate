package ru.yandex.practicum.filmorate.exception;

import lombok.extern.slf4j.Slf4j;

import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToRedColor;

@Slf4j
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
        log.info(stringToRedColor("call UserException:" + message));
    }
}
