package com.example.springyemian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mesdata {
    private int id;
    private String name;
    private String tel;
    private String idnum;
    private String address;
    private String nation;
    private String sex;
    private String stuid;
    private String major;
    private String province;
}
