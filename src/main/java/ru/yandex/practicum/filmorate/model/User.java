package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

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

}
