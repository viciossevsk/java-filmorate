package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

@Data
@Slf4j
public class Film {

    private Integer id;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private int duration;

    public void validate() throws ValidationException {
        log.trace("Вызываем метод Film.validate");
        if ((this.name == null) || (this.name.isEmpty())) {
            throw new ValidationException("film name invalid");
        }
        if (this.description.length() > 200) {
            throw new ValidationException("film description length > 200");
        }
        if (this.releaseDate.isBefore(LocalDate.of(1895, 12, 28))) {
            throw new ValidationException("film releaseDate < 28.12.1985");
        }
        if (this.duration < 0) {
            throw new ValidationException("film duration < 0");
        }
    }
}
