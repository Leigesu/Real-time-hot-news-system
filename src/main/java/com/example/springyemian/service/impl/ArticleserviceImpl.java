package com.example.springyemian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.springyemian.Controller.utils.JedisConnectionFactory;
import com.example.springyemian.Controller.utils.R;
import com.example.springyemian.dao.Articledao;
import com.example.springyemian.domain.*;
import com.example.springyemian.service.Articleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ArticleserviceImpl implements Articleservice {
    private Jedis jedis;
    @Autowired
    private Articledao articledao;

    @Override
    public List<Article> getAll() {
        return articledao.getAll();
    }

    @Override
    public Article getbyid(String id) {
        return articledao.getbyid(id);
    }

    @Override
    public List<Article> getrefresh(int times) {
        return articledao.getrefresh(times);
    }

    @Override
    public Boolean updateartdianzancount(int count,int id) {
        return articledao.updateartdianzancount(count,id);
    }

    @Override
    public List<Article> getuserarticlebyid(int id) {
        jedis = JedisConnectionFactory.getjedis();
        jedis.auth("s1172944356");
        jedis.select(0);
        String useratricle = "useratricle_" + id;

        if (jedis.get(useratricle)!=null){
            List<Article> articles =  (List<Article>) JSONObject.parseArray(jedis.get(useratricle),Article.class);
            jedis.close();
            System.out.println("\n\n\n*----------------走的redis啊啊啊啊啊-------------\n\n\n");
            return articles;
        }
        List<Article> useratricles =  articledao.getuserarticlebyid(id);
        jedis.setex(useratricle,60*30,JSONObject.toJSON(useratricles).toString());
        jedis.close();
        return useratricles;
    }

    @Override
    public List<Article> getsearchbycontent(String content) {
        return articledao.getsearchbycontent(content);
    }

    @Override
    public Boolean updatecommentcount(int articleid) {
        return articledao.updatecommentcount(articleid);
    }

    @Override
    public Boolean updateuserip(String ip, String from, int id) {
        return articledao.updateuserip(ip, from, id);
    }

    @Override
    public Boolean save(Article art) {
        return articledao.insertarticle(art)>0;
    }

    @Override
    public List<Price> getAllPrice() {
        return articledao.getAllPrice();
    }

    @Override
    public List<TheShit> getAllTheshit() {
        return articledao.getAllTheshit();
    }

    @Override
    public List<Comments> getAllComments(int id) {
        return articledao.getAllComments(id);
    }

    @Override
    public List<Comments> getAllxinComments(int id) {
        return articledao.getAllxinComments(id);
    }

    @Override
    public Boolean commentdianzan(int dianzanshu, int id) {
        return articledao.commentdianzan(dianzanshu, id);
    }

    @Override
    public Boolean savearticlecomments(Comments comments) {
        return articledao.insertarticlecomments(comments)>0;
    }

    @Override
    public User selectusername(String username) {
        return  articledao.selectusername(username);
    }

    @Override
    public Boolean updateuserwzcount(int wzcount, int id) {
        return articledao.updateuserwzcount(wzcount, id);
    }

    @Override
    public Boolean setuserheadico(String headico, int id) {
        return articledao.setuserheadico(headico, id);
    }

    @Override
    public Boolean insertuser(String username, String pwd) {
        Long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(Long.parseLong(String.valueOf(timeStamp)));
        return articledao.insertuser(username,pwd, String.valueOf(timeStamp),sd)>0;
    }

    @Override
    public int isusernameexist(String username) {
        return articledao.isusernameexist(username);
    }

    @Override
    public Boolean postimg(String id, MultipartFile[] file){
        for (int i = 0; i < file.length; i++) {
            System.out.println(id);
            String ofileName = file[i].getOriginalFilename();
            System.out.println(ofileName);
            String stffixName = ofileName.substring(ofileName.lastIndexOf("."));

            String fileName = id +"-"+System.currentTimeMillis() + stffixName;
            try{
//                file[i].transferTo(new File("C:\\Users\\Administrator\\Desktop\\ce\\"+fileName));
                file[i].transferTo(new File("/webpro/pt/"+fileName));
                articledao.insertimg(id,"/pt/"+fileName);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public List<Imgarr> getimgarr() {
        return articledao.getallimg();
    }

    @Override
    public List<Imgarr> getimgbyid(String id) {
        return articledao.getimgbyid(id);
    }
}
