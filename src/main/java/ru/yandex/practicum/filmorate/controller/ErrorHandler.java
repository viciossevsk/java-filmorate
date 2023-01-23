package ru.yandex.practicum.catsgram.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.yandex.practicum.filmorate.exception.UserException;

import java.util.Map;

/**
 * класс нужен чтобы возвращать код ошибки
 */
//@RestControllerAdvice
public class ErrorHandler {

    //    @ExceptionHandler
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorResponse handleIncorrectParameterException(final ValidationException e) {
//        return new ErrorResponse(stringToBlueColor(
//                e.getMessage()
//        ));
//    }
//
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFoundException(final UserException e) {
        return Map.of("error", "Ошибка с параметром count44444.",
                      "errorMessage", e.getMessage());
    }

//    @ExceptionHandler(FilmException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public ErrorResponse handleFilmNotFoundException(final RuntimeException e) {
//        return new ErrorResponse(stringToBlueColor(
//                e.getMessage()
//        ));
//    }
//
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Map<String, String> handleFilmNotFoundException(final RuntimeException e) {
//        return Map.of("error", "Ошибка с параметром count44444.",
//                      "errorMessage", e.getMessage());
//    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleThrowable(final Throwable e) {
        return Map.of("error", "Ошибка с параметром count11111111.",
                      "errorMessage", e.getMessage());
    }
}