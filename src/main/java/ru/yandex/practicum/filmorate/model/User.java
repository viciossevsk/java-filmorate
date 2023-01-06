package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.time.LocalDate;

import static java.time.LocalDate.now;

@Data
@Slf4j
@Builder
public class User {

    private Integer id;
    private String email;
    private String login;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    public void validate() throws ValidationException {
        log.trace("Вызываем метод User.validate");
        if ((this.email == null) || (this.email.isEmpty()) || (!this.email.contains("@"))) {
            throw new ValidationException("user email invalid");
        }
        if ((this.login == null) || (this.login.isEmpty()) || (this.login.contains(" "))) {
            throw new ValidationException("user login invalid");
        }
        if ((this.name == null) || (this.name.isEmpty())) {
            this.name = this.login;
        }
        if (this.birthday.isAfter(now())) {
            throw new ValidationException("user birthday in future");
        }
    }

}
