package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.UserException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    private int generatorId;

    @GetMapping("/users")
    public List<User> getAllFilms() {
        return new ArrayList<>(users.values());
    }

    @PostMapping("/user")
    public void createFilm(@RequestBody User user) throws ValidationException {
        user.validate();
        user.setId(++generatorId);
        users.put(user.getId(), user);
    }

    @PutMapping("/user")
    public void updateFilm(@RequestBody User user) throws UserException, ValidationException {
        user.validate();
        if (user.getId() != null) {
            users.put(user.getId(), user);
        } else {
            throw new UserException("user id not found");
        }

    }

}
