package com.example.springyemian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comments {
    private int id;
    private String time;
    private String userheadico;
    private String username;
    private int userid;
    private int articleid;
    private String comment;
    private Long createtime;
    private String userfrom;
    private int dianzanshu;
}
