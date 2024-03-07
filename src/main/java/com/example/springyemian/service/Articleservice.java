package com.example.springyemian.service;

import com.example.springyemian.domain.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface Articleservice {
//    文章相关
    List<Article> getAll();
    Article getbyid(String id);
    List<Article> getrefresh(int times);
    Boolean updateartdianzancount(int count,int id);
    List<Article> getuserarticlebyid(int id);
    List<Article> getsearchbycontent(String content);
    Boolean updatecommentcount(int articleid);

    //    用户相关
    Boolean updateuserip(String ip,String from,int id);
    User selectusername(String username);
    Boolean updateuserwzcount(int wzcount,int id);
    Boolean setuserheadico(String headico,int id);
    Boolean insertuser(String username,String pwd);
    int isusernameexist(String username);

//    图片上传
    Boolean postimg(String id,MultipartFile[] file);
    List<Imgarr> getimgarr();
    List<Imgarr> getimgbyid(String id);

    Boolean save(Article art);
    List<Price> getAllPrice();
    List<TheShit> getAllTheshit();
    List<Comments> getAllComments(int id);
    List<Comments> getAllxinComments(int id);
    Boolean commentdianzan(int dianzanshu,int id);
    Boolean savearticlecomments(Comments comments);

}
