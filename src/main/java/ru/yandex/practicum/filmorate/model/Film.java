package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.boot.convert.DurationFormat;
import org.springframework.boot.convert.DurationStyle;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {

    private Integer id;
    private String name;
    private String description;

    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate releaseDate;

    @DurationFormat(DurationStyle.SIMPLE)
    private Duration duration;

    @JsonProperty("duration")
    public long getDurationMinutes() {
        return duration.toMinutes();
    }

    public void validate() throws ValidationException {
        if ((this.name == null) || (this.name.isEmpty())) {
            throw new ValidationException("film name invalid");
        }
        if (this.description.length() > 200) {
            throw new ValidationException("film description length > 200");
        }
        if (this.releaseDate.isBefore(LocalDate.of(1985, 12, 28))) {
            throw new ValidationException("film releaseDate < 28.12.1985");
        }
    }
}
