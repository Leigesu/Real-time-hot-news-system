package com.example.springyemian.dao;

import com.example.springyemian.domain.Dingdan;
import com.example.springyemian.domain.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface Goodsdao {

    @Select("select * from boke_goods where goodclass = #{classname} order by rand()")
    public List<Goods> getgoodsbyclassname(String classname);

    @Select("select * from boke_goods where ishot = 1")
    public List<Goods> getgoodshot();

    @Insert("insert into boke_shop_dingdan values(#{id},#{time},#{userid},#{username},#{createtime},#{goodidlist},#{allcount},#{dingdanid})")
    public Boolean setdingdan(Dingdan dingdan);

    @Select("select * from boke_shop_dingdan where dingdanid=#{ddid}")
    public Dingdan getdingdanbyid(String ddid);

    @Select("select * from boke_shop_dingdan where userid = #{userid} ORDER BY id desc")
    public List<Dingdan> getdingdanbyuserid(String userid);

    @Select("select * from boke_goods where id = #{id}")
    public Goods getgoodsbyid(int id);

}
