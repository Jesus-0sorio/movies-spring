package com.jesus0sorio.movies.controller;

import com.jesus0sorio.movies.model.dto.MovieDto;
import com.jesus0sorio.movies.model.entity.Movie;
import com.jesus0sorio.movies.service.IMovie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1")
public class MovieController {

    @Autowired
    private IMovie movieService;

    @GetMapping("/movies")
    public ResponseEntity<?> index() {
        try {
            List<Movie> movies = (List<Movie>) movieService.findAll();

            if (movies.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting movies", e);
        }
    }

    @GetMapping("/movie/{id}")
    public MovieDto show(@PathVariable Long id) {
        Movie movie = movieService.findById(id);

        if (Objects.isNull(movie)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found");
        }

        return MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .year(movie.getYear())
                .director(movie.getDirector())
                .type(movie.getType())
                .build();
    }

    @PostMapping("/movie")
    public ResponseEntity<?> create(@RequestBody MovieDto movieDto) {

        try {
            Movie movie = movieService.save(movieDto);

            return new ResponseEntity<>(Movie.builder()
                    .title(movieDto.getTitle())
                    .year(movieDto.getYear())
                    .director(movieDto.getDirector())
                    .type(movieDto.getType())
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating movie", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/movie")
    public ResponseEntity<?> update(@RequestBody MovieDto movieDto) {
        Map<String, String> response = new HashMap<>();

        if(Objects.isNull(movieDto.getId())) {
            response.put("message", "Movie id is required");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if(!movieService.existsById(movieDto.getId())) {
            response.put("message", "Movie not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            System.out.printf("movieDto: %s\n", movieDto.toString());
            Movie movie = movieService.save(movieDto);

            return new ResponseEntity<>(Movie.builder()
                    .title(movieDto.getTitle())
                    .year(movieDto.getYear())
                    .director(movieDto.getDirector())
                    .type(movieDto.getType())
                    .build(), HttpStatus.OK);
        } catch (DataAccessException e){
            response.put("error", e.getMessage());
            response.put("message", "Error updating movie");

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/movie/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, String> response = new HashMap<>();

        Movie   movie = movieService.findById(id);

        if (Objects.isNull(movie)) {
            response.put("message", "Movie not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            movieService.delete(movie);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataAccessException e){
            response.put("error", e.getMessage());
            response.put("message", "Error deleting movie");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
