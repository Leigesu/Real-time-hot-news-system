package com.example.springyemian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Guess {
    private int id;
    private Float leftrate;
    private Float rightrate;
    private String leftico;
    private String rightico;
    private String leftteam;
    private String rightteam;
    private int total;
    private int leftsur;
    private int rightsur;
}
