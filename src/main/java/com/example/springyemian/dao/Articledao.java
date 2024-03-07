package com.example.springyemian.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springyemian.domain.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface Articledao extends BaseMapper<Article> {
//    文章相关
    @Select("select id,title,createtime,createtimedetail,commentcount,writerid,dianzancount,type,writer,writerfrom,writerhead,fm1,fm2,fm3 from boke_article order by id desc limit 50")
    public List<Article> getAll();

    @Select("select id,title,createtime,createtimedetail,commentcount,writerid,dianzancount,type,writer,writerfrom,writerhead,fm1,fm2,fm3 from boke_article order by id desc limit #{times},30")
    public List<Article> getrefresh(int times);

    @Select("select * from boke_article where id = #{id}")
    public Article getbyid(String id);

    @Update("update boke_article set dianzancount = #{count} where id = #{id}")
    public Boolean updateartdianzancount(int count,int id);

    @Select("select id,title,createtime,createtimedetail,commentcount,writerid,dianzancount,type,writer,writerfrom,writerhead,fm1,fm2,fm3 from boke_article where writerid = #{id} order by id desc limit 500")
    public List<Article> getuserarticlebyid(int id);
    @Insert("insert into boke_article values(#{id},#{title},#{content},#{createtime},#{createtimedetail},#{commentcount},#{writerid},#{dianzancount},#{type},#{writer},#{writerfrom},#{writerhead},#{fm1},#{fm2},#{fm3})")
    public int insertarticle(Article article);
    @Select("select * from boke_article where title like '%${content}%' or writer like '%${content}%' order by id desc")
    public List<Article> getsearchbycontent(String content);
    @Update("update boke_article set commentcount = commentcount +1 where id = #{articleid}")
    public Boolean updatecommentcount(int articleid);

//    用户相关
    @Update("update boke_user set ip = #{ip},userfrom = #{from} where id = #{id}")
    public Boolean updateuserip(String ip,String from,int id);

    //    实现用户登录
    @Select("select * from boke_user where username = #{username}")
    public User selectusername(String username);

    @Select("select count(*) from boke_user where username = #{username}")
    public int isusernameexist(String username);

    @Update("update boke_user set wzcount = #{wzcount} where id = #{id}")
    public Boolean updateuserwzcount(int wzcount,int id);

    @Update("update boke_user set headico = #{headico} where id = #{id}")
    public Boolean setuserheadico(String headico,int id);

    @Insert("insert into boke_user values(null,#{username},#{username},#{pwd},#{createtime},#{time},0,0,0,'/headico/user-11.jpg',null,0)")
    public int insertuser(String username,String pwd,String createtime,String time);

//    实现图片上传保存到数据库
    @Insert("insert into boke_photo values(null,#{imgname},#{userid})")
    public Boolean insertimg(String userid,String imgname);

    @Select("select * from boke_photo")
    public List<Imgarr> getallimg();

    @Select("select * from boke_photo where userclass = #{id} order by id desc")
    public List<Imgarr> getimgbyid(String id);

//    顺道查询price表数据
    @Select("select * from price")
    public List<Price> getAllPrice();

//    顺道查theshit数据
    @Select("select * from theshit")
    public List<TheShit> getAllTheshit();

    //    顺道查评论comments数据
    @Select("select * from boke_comment where articleid = #{id} order by dianzanshu desc")
    public List<Comments> getAllComments(int id);

    @Select("select * from boke_comment where articleid = #{id} order by createtime desc")
    public List<Comments> getAllxinComments(int id);
    @Update("update boke_comment set dianzanshu = #{dianzanshu} where id = #{id}")
    public Boolean commentdianzan(int dianzanshu,int id);

//    添加评论
    @Insert("insert into boke_comment values(#{id},#{time},#{userheadico},#{userid},#{username},#{userfrom},#{articleid},#{comment},#{createtime},#{dianzanshu})")
    public int insertarticlecomments(Comments comments);



}
