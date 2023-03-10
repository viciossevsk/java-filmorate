package ru.yandex.practicum.filmorate.dao.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmLikesDao;
import ru.yandex.practicum.filmorate.dao.UserEventDao;
import ru.yandex.practicum.filmorate.otherFunction.EventType;
import ru.yandex.practicum.filmorate.otherFunction.OperationType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@Data
public class FilmLikesDaoImpl implements FilmLikesDao {
    private final static String GET_USER_LIKES_BY_FILM_ID_SQL = "select users_id from film_likes where film_id = ?";
    private final static String SET_NEW_LIKE_TO_FILM_SQL = "insert into film_likes(film_id, users_id) values(?, ?)";
    private final static String DELETE_LIKE_FROM_FILM_SQL = "delete from film_likes where film_id = ? and users_id = ?";
    private final static String DELETE_ALL_LIKES_OF_FILM_SQL = "delete from film_likes where film_id = ?";
    private final static String DELETE_ALL_LIKES_OF_USER_SQL = "delete from film_likes where users_id = ?";
    private final static String CHECK_EXIST_FILM_LIKE_SQL = "select count(*) from film_likes where " +
            "users_id = ? and FILM_ID = ?";
    private final JdbcTemplate jdbcTemplate;
    private final UserEventDao userEventDao;

    @Override
    public void addLikeToFilm(Integer filmId, Integer userId) {
        if (!checkUserLikeExist(userId, filmId)) {
            jdbcTemplate.update(SET_NEW_LIKE_TO_FILM_SQL, filmId, userId);
        }
        userEventDao.setUserEvent(userId, EventType.LIKE, OperationType.ADD, filmId);
    }

    @Override
    public void removeLikeFromFilm(Integer filmId, Integer userId) {
        jdbcTemplate.update(DELETE_LIKE_FROM_FILM_SQL, filmId, userId);
        userEventDao.setUserEvent(userId, EventType.LIKE, OperationType.REMOVE, filmId);
    }

    @Override
    public List<Integer> getUserLikesByFilm(Integer filmId) {
        return new ArrayList<>(jdbcTemplate.query(GET_USER_LIKES_BY_FILM_ID_SQL, (rs, rowNum) -> buildUserId(rs),
                                                  filmId));
    }

    @Override
    public void deleteAllLikesByFilm(Integer filmId) {
        jdbcTemplate.update(DELETE_ALL_LIKES_OF_FILM_SQL, filmId);
    }

    @Override
    public void deleteAllLikesOfUser(Integer userId) {
        jdbcTemplate.update(DELETE_ALL_LIKES_OF_USER_SQL, userId);
    }

    private Integer buildUserId(ResultSet rs) throws SQLException {
        return rs.getInt("users_id");
    }

    public Boolean checkUserLikeExist(Integer userId, Integer filmId) {
        Integer count = jdbcTemplate.queryForObject(CHECK_EXIST_FILM_LIKE_SQL, Integer.class, userId, filmId);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }
}
