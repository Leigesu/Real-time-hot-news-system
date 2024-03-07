package com.example.springyemian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private int id;
    private String dingdanid;
    private String time;
    private String userid;
    private String username;
    private Long createtime;
    private String goodidlist;
    private String allcount;
    private List<Goods> ikun;
}
