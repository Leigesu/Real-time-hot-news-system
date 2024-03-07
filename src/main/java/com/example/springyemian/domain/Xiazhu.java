package com.example.springyemian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Xiazhu {
    private int id;
    private int userid;
    private int xiazhujine;
    private int xiazhuchangciid;
    private String xiazhunabian;
    private float xiazhurate;
}
