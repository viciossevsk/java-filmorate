package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.UserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.now;
import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToGreenColor;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int generatorId = 0;

    public List<User> getAllUsers() {
        log.info(stringToGreenColor("getAllUsers..."));
        return new ArrayList<>(users.values());
    }

    public User createUser(User user) {
        log.info(stringToGreenColor("add user..."));
        validate(user);
        user.setId(++generatorId);
        users.put(user.getId(), user);
        return user;
    }


    public User updateUser(User user) {
        log.info(stringToGreenColor("update user..."));
        validate(user);
        if (user.getId() != null) {
            if (users.containsKey(user.getId())) {
                users.replace(user.getId(), user);
            } else {
                throw new UserException("user id invalid");
            }
        } else {
            throw new UserException("user id not found");
        }
        return user;
    }

    @Override
    public User getUser(Integer id) {
        if (id != null) {
            if (users.containsKey(id)) {
                return users.get(id);
            } else {
                throw new UserException("user id=" + id + " not found");
            }
        } else {
            throw new UserException("user id is empty");
        }
    }

    public void validate(User user) {
        log.trace("validate user");
        if ((user.getEmail() == null) || (user.getEmail().isEmpty()) || (!user.getEmail().contains("@"))) {
            throw new ValidationException("user email invalid");
        }
        if ((user.getLogin() == null) || (user.getLogin().isEmpty()) || (user.getLogin().contains(" "))) {
            throw new ValidationException("user login invalid");
        }
        if ((user.getName() == null) || (user.getName().isEmpty())) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(now())) {
            throw new ValidationException("user birthday in future");
        }
    }
}
