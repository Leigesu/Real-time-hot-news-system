package com.example.springyemian.Controller;


import com.example.springyemian.Controller.utils.R;
import com.example.springyemian.domain.Dingdan;
import com.example.springyemian.domain.Goods;
import com.example.springyemian.domain.History;
import com.example.springyemian.service.Goodservice;
import com.example.springyemian.service.Mesdataservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private Goodservice goodservice;
    @Autowired
    private Mesdataservice mesdataservice;


    @GetMapping("/class")
    public R getAll(HttpServletRequest request){
        String classname = request.getParameter("classname");
        System.out.println(classname);
        if (classname.equals("热门推荐")){
            return new R(true,goodservice.getgoodshot());
        }
        return new R(true,goodservice.getgoodsbyclassname(classname));
    }

    @PostMapping("/dingdan")
    public R setdingdan(@RequestBody Dingdan dingdan){
        System.out.println(dingdan);

        Long timeStamp = dingdan.getCreatetime();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(Long.parseLong(String.valueOf(timeStamp)));

        dingdan.setTime(sd);


        Boolean flag = goodservice.setdingdan(dingdan);

        return new R(true,flag);
    }

    @PostMapping("/dingdan/{num}/{id}")
    public R setusersur(@PathVariable int num,@PathVariable int id){
        return new R(true,mesdataservice.updateusersur(num,id));
    }

    @GetMapping("/dingdan/{ddid}")
    public R getdingdanbyid(@PathVariable String ddid){
        Dingdan dingdan = goodservice.getdingdanbyid(ddid);
        System.out.println(dingdan);
        return new R(true,dingdan);
    }

    @GetMapping("/dingdan/list/{idlist}")
    public R getdingdanlist(@PathVariable String idlist){
        List<String>  reallist = new ArrayList<>();
        reallist = List.of(idlist.split(","));
        List<Goods> re = new ArrayList<>();
        System.out.println(reallist);
        for (int i = 0; i < reallist.size(); i++) {
            Goods goods = goodservice.getgoodsbyid(Integer.parseInt(reallist.get(i)));
            re.add(goods);
        }
//        goodservice.getgoodsbyid();
        return new R(true,re);
    }

    @GetMapping("/dingdan/history/{userid}")
    public R gethistorydd(@PathVariable String userid){
        return new R(true,goodservice.getdingdanbyuserid(userid));
    }

}
