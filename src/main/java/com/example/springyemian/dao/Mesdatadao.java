package com.example.springyemian.dao;

import com.example.springyemian.domain.Guess;
import com.example.springyemian.domain.Mesdata;
import com.example.springyemian.domain.User;
import com.example.springyemian.domain.Xiazhu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface Mesdatadao {
    @Select("select * from mesdata ")
    public List<Mesdata> getAll();

    @Select("select * from mesdata where ${classid} = #{detailid}")
    public List<Mesdata> getbyclassid(String classid,String detailid);

    @Select("select * from mesdata where ${classid} != #{detailid}")
    public List<Mesdata> getbyclassid1(String classid,String detailid);

    @Select("select * from guess")
    public List<Guess> getrate();

    @Select("select * from boke_user where id = 1")
    public User getuser();

    @Select("select * from guess where id = #{id}")
    public Guess getsur(int id);

    @Update("update guess set leftsur = #{num} where id = #{id}")
    public Boolean icmoney(int num,int id);

    @Update("update guess set rightsur = #{num} where id = #{id}")
    public Boolean icmoney2(int num,int id);

    @Update("update guess set total = #{num} where id = #{id}")
    public Boolean icmoneytotal(int num,int id);

    @Update("update guess set leftrate = #{lnum},rightrate =#{rnum} where id = #{id}")
    public Boolean updaterate(float lnum, float rnum,int id);

    @Insert("insert into guess_user values(null,#{userid},#{num},#{changciid},#{position},#{rate})")
    public Boolean insertguess_user(int userid, int num, int changciid, String position, float rate);

    @Update("update boke_user set surplus = #{num} where id = #{id}")
    public Boolean updateusersur(int num,int id);

    @Select("select * from guess_user where xiazhunabian = #{position} and xiazhuchangciid = #{saishiid}")
    public List<Xiazhu> jiesuan(String position,int saishiid);

    @Delete("delete from guess_user where xiazhuchangciid = #{saishiid}")
    public Boolean jiesuanshanku(int saishiid);



}
