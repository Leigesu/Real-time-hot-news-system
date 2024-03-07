package com.example.springyemian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private int id;
    private String title;
    private String content;
    private String createtimedetail;
    private Long createtime;
    private int commentcount;
    private int writerid;
    private int dianzancount;
    private String writer;
    private int type;
    private String writerfrom;
    private String writerhead;
    private String fm1;
    private String fm2;
    private String fm3;
}
