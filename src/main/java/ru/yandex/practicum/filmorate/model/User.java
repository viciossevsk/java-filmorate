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
public class User {

    private Integer id;
    private String email;
    private String login;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();

    public void setFriend(Integer friendId) {
        if (friendId != null) {
            if (!friends.add(friendId)) {
                throw new UserException("friend with id=" + friendId + " already exists");
            }
        } else {
            throw new UserException("friendId  is empty");
        }
    }

    public void deleteFriend(Integer friendId) {
        if (friendId != null) {
            if (!friends.remove(friendId)) {
                throw new UserException("friend with id=" + friendId + " not exists");
            }
        } else {
            throw new UserException("friend id is empty");
        }
    }

}
