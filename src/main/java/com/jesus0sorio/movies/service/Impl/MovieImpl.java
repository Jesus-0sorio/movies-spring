package com.jesus0sorio.movies.service.Impl;

import com.jesus0sorio.movies.model.dao.MovieDao;
import com.jesus0sorio.movies.model.dto.MovieDto;
import com.jesus0sorio.movies.model.entity.Movie;
import com.jesus0sorio.movies.service.IMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieImpl implements IMovie {

    @Autowired
    private MovieDao moviesDao;

    @Override
    @Transactional
    public Movie save(MovieDto movieDto) {
        return moviesDao.save((Movie.builder()
                .id(movieDto.getId())
                .title(movieDto.getTitle())
                .year(movieDto.getYear())
                .director(movieDto.getDirector())
                .type(movieDto.getType())
                .build()));
    }

    @Override
    @Transactional(readOnly = true)
    public Movie findById(Long id){
        return moviesDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public Iterable<Movie> findAll() {
        return moviesDao.findAll();
    }

    @Transactional
    public void delete(Movie movie) {
        moviesDao.delete(movie);
    }

    @Transactional(readOnly = true)
    public Boolean existsById(Long id) {
        return moviesDao.existsById(id);
    }
}
