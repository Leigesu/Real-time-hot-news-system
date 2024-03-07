package com.example.springyemian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dingdan {
    private int id;
    private String dingdanid;
    private String time;
    private String userid;
    private String username;
    private Long createtime;
    private String goodidlist;
    private String allcount;
}
