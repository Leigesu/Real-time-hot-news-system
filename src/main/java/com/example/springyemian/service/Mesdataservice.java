package com.example.springyemian.service;

import com.example.springyemian.domain.Guess;
import com.example.springyemian.domain.Mesdata;
import com.example.springyemian.domain.User;
import com.example.springyemian.domain.Xiazhu;

import java.util.List;

public interface Mesdataservice {
    List<Mesdata> getAll();
    List<Mesdata> getbyclassid(String classid,String detailid);
    List<Mesdata> getbyclassid1(String classid,String detailid);
    List<Guess> getrate();
    User getuser();

    Guess selectsur(int id);
    Boolean icmoney(int num,int id);
    Boolean icmoney2(int num,int id);

    Boolean icmoneytotal(int num,int id);

    Boolean updaterate(float lnum, float rnum, int id);

    Boolean insertguess_user(int userid,int num, int changciid,String position,float rate);

    Boolean updateusersur(int num,int id);

    List<Xiazhu> jiesuanbyid(String position, int saishiid);

    Boolean jiesuanshanku(int saishiid);

}
