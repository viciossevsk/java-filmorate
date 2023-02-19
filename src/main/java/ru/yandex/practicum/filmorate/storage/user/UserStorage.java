package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(User user);

    User getUserById(Integer userId);

    void deleteUser(Integer userId, List<Film> films);

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);

}
