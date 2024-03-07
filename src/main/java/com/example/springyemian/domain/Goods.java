package com.example.springyemian.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods {
    private int id;
    private String goodname;
    private String goodclass;
    private float goodprice;
    private String goodgrade;
    private String goodsrc;
    private String goodlb1;
    private String goodlb2;
    private int goodliulian;
}
