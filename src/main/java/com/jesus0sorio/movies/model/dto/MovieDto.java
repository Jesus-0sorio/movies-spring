package com.jesus0sorio.movies.model.dto;

import lombok.*;
import java.io.Serializable;

@Data
@ToString
@Builder
public class MovieDto implements Serializable {

    private Long id;
    private String title;
    private int year;
    private String director;
    private String type;
}
