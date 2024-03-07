package com.example.springyemian.service.impl;

import com.example.springyemian.dao.Mesdatadao;
import com.example.springyemian.domain.Guess;
import com.example.springyemian.domain.Mesdata;
import com.example.springyemian.domain.User;
import com.example.springyemian.domain.Xiazhu;
import com.example.springyemian.service.Mesdataservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Mesdataserviceimpl implements Mesdataservice {
    @Autowired
    private Mesdatadao mesdatadao;
    @Override
    public List<Mesdata> getAll() {
        return mesdatadao.getAll();
    }
    @Override
    public List<Mesdata> getbyclassid(String classid, String detailid) {
        return mesdatadao.getbyclassid(classid,detailid);
    }
    @Override
    public List<Mesdata> getbyclassid1(String classid, String detailid) {
        return mesdatadao.getbyclassid1(classid,detailid);
    }
    @Override
    public List<Guess> getrate() {
        return mesdatadao.getrate();
    }

    @Override
    public User getuser() {
        return mesdatadao.getuser();
    }

    @Override
    public Guess selectsur(int id) {
        return mesdatadao.getsur(id);
    }

    @Override
    public Boolean icmoney(int num,int id) {
        return mesdatadao.icmoney(num,id);
    }

    @Override
    public Boolean icmoney2(int num,int id) {
        return mesdatadao.icmoney2(num,id);
    }

    @Override
    public Boolean icmoneytotal(int num, int id) {
        return mesdatadao.icmoneytotal(num,id);
    }

    @Override
    public Boolean updaterate(float lnum, float rnum, int id) {
        return mesdatadao.updaterate(lnum,rnum,id);
    }

    @Override
    public Boolean insertguess_user(int userid, int num, int changciid, String position, float rate) {
        return mesdatadao.insertguess_user(userid,num,changciid,position,rate);
    }

    @Override
    public Boolean updateusersur(int num,int id) {
        return mesdatadao.updateusersur(num,id);
    }

    @Override
    public List<Xiazhu> jiesuanbyid(String position, int saishiid) {
        return mesdatadao.jiesuan(position,saishiid);
    }

    @Override
    public Boolean jiesuanshanku(int saishiid) {
        return mesdatadao.jiesuanshanku(saishiid);
    }


}
