package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.RatingDao;
import ru.yandex.practicum.filmorate.exception.RatingNotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Component
public class RatingDaoImpl implements RatingDao {
    private final JdbcTemplate jdbcTemplate;
    private final static String GET_ALL_RATINGS_SQL = "select * from rating";
    private final static String GET_RATING_BY_ID_SQL = "select * from rating where rating_id = ?";
    private final static String GET_RATING_ID_BY_NAME_SQL = "select rating_id from rating where name = ?";
    private final static String SET_NEW_RATING_SQL = "insert into rating (name) values(?)";


    @Autowired
    public RatingDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Integer addNewRating(String name) {
        KeyHolder ratingKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(SET_NEW_RATING_SQL, new String[]{"rating_id"});
            stmt.setString(1, name);
            return stmt;
        }, ratingKeyHolder);
        return Objects.requireNonNull(ratingKeyHolder.getKey()).intValue();
    }

    @Override
    public Integer getRatingIdForRatingName(String name) {
        return jdbcTemplate.query(GET_RATING_ID_BY_NAME_SQL, (rs, rowNum) -> buildRatingId(rs), name)
                .stream().findFirst().orElseThrow(() -> {
                    throw new RatingNotFoundException("Rating not found");
                });
    }

    @Override
    public List<Rating> getAllRatings() {
        return jdbcTemplate.query(GET_ALL_RATINGS_SQL, (rs, rowNum) -> buildRating(rs));
    }

    @Override
    public Rating getRatingById(Integer ratingId) {
        return jdbcTemplate.query(GET_RATING_BY_ID_SQL, (rs, rowNum) -> buildRating(rs), ratingId)
                .stream().findFirst().orElseThrow(() -> {
                    throw new RatingNotFoundException("Rating not found");
                });
    }

    private Rating buildRating(ResultSet rs) throws SQLException {
        return Rating.builder()
                .id(rs.getInt("rating_id"))
                .name(rs.getString("name"))
                .build();
    }

    private Integer buildRatingId(ResultSet rs) throws SQLException {
        return rs.getInt("rating_id");
    }
}
