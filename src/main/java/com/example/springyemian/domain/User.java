package com.example.springyemian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    private String username;
    private String pwd;
    private String name;
    private String createtime;
    private String time;
    private String ip;
    private float surplus;
    private String headico;
    private String userfrom;
    private int isroot;
    private int wzcount;
}
