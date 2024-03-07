package com.example.springyemian.service;

import com.example.springyemian.domain.Dingdan;
import com.example.springyemian.domain.Goods;
import com.example.springyemian.domain.History;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface Goodservice {
    List<Goods> getgoodsbyclassname(String classname);
    List<Goods> getgoodshot();
    Boolean setdingdan(Dingdan dingdan);
    Dingdan getdingdanbyid(String ddid);
    List<History> getdingdanbyuserid(String userid);
    Goods getgoodsbyid(int id);
}
