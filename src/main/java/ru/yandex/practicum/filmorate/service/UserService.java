package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

import static ru.yandex.practicum.filmorate.otherFunction.AddvansedFunctions.stringToGreenColor;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User getUser(Integer id) {
        log.info(stringToGreenColor("call method getUser in UserStorage... via GET /users"));
        return userStorage.getUser(id);
    }

    public List<User> getAllUsers() {
        log.info(stringToGreenColor("call method getAllUsers in UserStorage... via GET /users"));
        return userStorage.getAllUsers();
    }

    public User createUser(User user) {
        log.info(stringToGreenColor("call method add user in UserStorage... via POST /users"));
        return userStorage.createUser(user);
    }

    public User updateUser(User user) {
        log.info(stringToGreenColor("call method update user in UserStorage... via PUT /users"));
        return userStorage.updateUser(user);
    }

    public void addFriend(Integer id, Integer friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        user.setFriend(friend.getId());
        friend.setFriend(user.getId());

    }

    public void deleteFriend(Integer id, Integer friendId) {
        User user = userStorage.getUser(id);
        User friend = userStorage.getUser(friendId);
        user.deleteFriend(friend.getId());
        friend.deleteFriend(user.getId());
    }

    /**
     * получаем ИД друзей юзера
     * по ИД друга получаем объект
     * объединяем объект класса User в коллекцию
     * <p>
     * //        List<User> friends = new ArrayList<>();
     * //        for (Integer friendId : user.getFriends()){
     * //           User friend = userStorage.getUser(friendId);
     * //           friends.add(friend);
     * //        }
     */
    public List<User> getAllFriends(Integer id) {
        User user = userStorage.getUser(id);

        return user.getFriends().stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    /**
     * получаем ИД друзей юзера
     * ИД друга ищем в друзьях у Other (находим только одинаковых друзей у обоих юзеров)
     * по ИД друга получаем объект
     * объединяем объект класса User в коллекцию
     */
    public List<User> getCommonFriends(Integer id, Integer otherId) {
        User user = userStorage.getUser(id);
        User otherUser = userStorage.getUser(otherId);

        return user.getFriends().stream()
                .filter(otherUser.getFriends()::contains)
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }
}
