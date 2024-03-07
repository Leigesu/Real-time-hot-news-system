package com.example.springyemian.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.springyemian.Controller.utils.JedisConnectionFactory;
import com.example.springyemian.dao.Goodsdao;
import com.example.springyemian.domain.Article;
import com.example.springyemian.domain.Dingdan;
import com.example.springyemian.domain.Goods;
import com.example.springyemian.domain.History;
import com.example.springyemian.service.Goodservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class GoodserviceImpl implements Goodservice {
    @Autowired
    private Goodsdao gooddao;
    private Jedis jedis;
    @Override
    public List<Goods> getgoodsbyclassname(String classname) {
        jedis = JedisConnectionFactory.getjedis();
        jedis.auth("s1172944356");
        jedis.select(1);

        if (jedis.get(classname)!=null){
            List<Goods> goods =  (List<Goods>) JSONObject.parseArray(jedis.get(classname),Goods.class);
            jedis.close();
            System.out.println("\n\n\n*----------------走的redis啊啊啊啊啊-------------\n\n\n");
            return goods;
        }
        List<Goods> goods =  gooddao.getgoodsbyclassname(classname);
        jedis.setex(classname,60*30,JSONObject.toJSON(goods).toString());
        jedis.close();
        return goods;
    }
    @Override
    public List<Goods> getgoodshot() {
        jedis = JedisConnectionFactory.getjedis();
        jedis.auth("s1172944356");
        jedis.select(1);

        if (jedis.get("hot")!=null){
            List<Goods> goods =  (List<Goods>) JSONObject.parseArray(jedis.get("hot"),Goods.class);
            jedis.close();
            System.out.println("\n\n\n*----------------走的redis啊啊啊啊啊-------------\n\n\n");
            return goods;
        }
        List<Goods> goods =  gooddao.getgoodshot();
        jedis.setex("hot",60*30,JSONObject.toJSON(goods).toString());
        jedis.close();
        return goods;
    }
    @Override
    public Boolean setdingdan(Dingdan dingdan) {
        Boolean flag = gooddao.setdingdan(dingdan);
        if (flag){
            jedis = JedisConnectionFactory.getjedis();
            jedis.auth("s1172944356");
            jedis.select(2);

            String key = "historydingdan_"+dingdan.getUserid();
            jedis.del(key);
            jedis.close();
        }
        return flag;
    }
    @Override
    public Dingdan getdingdanbyid(String ddid) {
        return gooddao.getdingdanbyid(ddid);
    }

    @Override
    public List<History> getdingdanbyuserid(String userid) {
        jedis = JedisConnectionFactory.getjedis();
        jedis.auth("s1172944356");
        jedis.select(2);

        String key = "historydingdan_"+userid;

        if (jedis.get(key)!=null){
            List<History> histories =  (List<History>) JSONObject.parseArray(jedis.get(key),History.class);
            jedis.close();
            System.out.println("\n\n*----------------走的redis啊啊啊啊啊-------------\n\n");
            return histories;
        }

        List<Dingdan> goods =  gooddao.getdingdanbyuserid(userid);
        List<History> ret = new ArrayList<>();
        List<Dingdan> fm1 = goods;
        for (int i = 0; i < fm1.size(); i++) {
            History history = new History();
            history.setId(fm1.get(i).getId());
            history.setDingdanid(fm1.get(i).getDingdanid());
            history.setUserid(fm1.get(i).getUserid());
            history.setTime(fm1.get(i).getTime());
            history.setUsername(fm1.get(i).getUsername());
            history.setCreatetime(fm1.get(i).getCreatetime());
            history.setGoodidlist(fm1.get(i).getGoodidlist());
            history.setAllcount(fm1.get(i).getAllcount());

            List<String>  reallist = new ArrayList<>();
            reallist = List.of(fm1.get(i).getGoodidlist().split(","));
            List<Goods> re = new ArrayList<>();

            for (int j = 0; j < reallist.size(); j++) {
                Goods good = gooddao.getgoodsbyid(Integer.parseInt(reallist.get(j)));
                re.add(good);
            }
            history.setIkun(re);
            ret.add(history);
        }

        jedis.setex(key,5*60,JSONObject.toJSON(ret).toString());
        jedis.close();
        return ret;
    }

    @Override
    public Goods getgoodsbyid(int id) {
        return gooddao.getgoodsbyid(id);
    }
}
