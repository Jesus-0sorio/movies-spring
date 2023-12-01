package com.jesus0sorio.movies.service;

import com.jesus0sorio.movies.model.dto.MovieDto;
import com.jesus0sorio.movies.model.entity.Movie;

public interface IMovie {

    Movie save(MovieDto movies);

    Movie findById(Long id);

    Iterable<Movie> findAll();

    void delete(Movie movie);

    Boolean existsById(Long id);

}
