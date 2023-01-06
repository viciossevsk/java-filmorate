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

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    private int generatorId;

    @GetMapping()
    public List<User> getAllUsers() {
        log.info("Вызываем метод getAllUsers... через GET /users");
        return new ArrayList<>(users.values());
    }

    @PostMapping()
    public User createUser(@RequestBody User user) throws ValidationException {
        log.info("Вызываем метод добавления пользователя... через POST /user");
        user.validate();
        user.setId(++generatorId);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping()
    public User updateUser(@RequestBody User user) throws UserException, ValidationException {
        log.info("Вызываем метод изменения пользователя... через PUT /user");
        user.validate();
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

}
