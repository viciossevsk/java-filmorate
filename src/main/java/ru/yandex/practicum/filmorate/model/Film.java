package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.exception.UserException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Film {

    private Integer id;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private int duration;
    private final Set<Integer> likes = new HashSet<>();

    public void addLike(Integer userId) {
        if (userId != null) {
            if (!(likes.add(userId))) {
                throw new UserException("user with id=" + userId + " already add like this film");
            }
        } else {
            throw new UserException("userId  is empty");
        }
    }

    public void removeLike(Integer userId) {
        if (userId != null) {
            if (!likes.remove(userId)) {
                throw new UserException("user with id=" + userId + " not add like this film");
            }
        } else {
            throw new UserException("userId is empty");
        }
    }

}
