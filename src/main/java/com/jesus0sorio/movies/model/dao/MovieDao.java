package com.jesus0sorio.movies.model.dao;

import com.jesus0sorio.movies.model.entity.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieDao extends CrudRepository<Movie, Long> {
}
