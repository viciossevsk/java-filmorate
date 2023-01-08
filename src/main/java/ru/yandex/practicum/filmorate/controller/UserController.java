package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.now;
import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToBlueColor;
import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToGreenColor;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    private int generatorId;

    @GetMapping()
    public List<User> getAllUsers() {
        log.info(stringToGreenColor("call method getAllUsers... via GET /users"));
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User createUser(@RequestBody User user) throws ValidationException {
        log.info(stringToGreenColor("call method add user... via POST /user"));
        log.info(stringToBlueColor(user.toString()));
        validate(user);
        user.setId(++generatorId);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) throws UserException, ValidationException {
        log.info(stringToGreenColor("call method update user... via PUT /user"));
        log.info(stringToBlueColor(user.toString()));
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

    public void validate(User user) throws ValidationException {
        log.trace("call method validate user");
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
