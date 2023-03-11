package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.DirectorDao;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorDao directorDao;

    public List<Director> getAllDirectors() {
        return directorDao.getAllDirectors();
    }


    public Director getDirectorById(Integer directorId) {
        return directorDao.getDirectorById(directorId);
    }

    public Director createDirector(Director director) {
        validateDirector(director);
        return directorDao.createDirector(director);
    }

    public Director updateDirector(Director director) {
        return directorDao.updateDirector(director);
    }

    public void deleteDirector(Integer directorId) {
        directorDao.deleteDirector(directorId);
    }

    private boolean validateDirector(Director director) throws ValidationException {
        if (director.getName().trim().isEmpty()) {
            throw new ValidationException("Director name cannot be empty");
        }
        return true;
    }
}


