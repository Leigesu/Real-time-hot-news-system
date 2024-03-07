package com.example.springyemian.Controller;

import com.example.springyemian.service.Articleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class ArticleJumpController {

    @Autowired
    private Articleservice articleservice;


    @RequestMapping(value = "/jump/{id}",method = RequestMethod.GET)
    public String hehe(@PathVariable String id, HttpSession session) {
//        System.out.println(id);
        session.setAttribute("myid",id);
        return "redirect:/detail.html";
    }

//    @RequestMapping("/users/{name}")
//    public R save(@PathVariable String name , HttpSession session) {
//        Boolean flag = true;
//
////        Long timeStamp = System.currentTimeMillis();
////        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
////        String sd = sdf.format(Long.parseLong(String.valueOf(timeStamp)));
////
////        comment.setArticleid(id);
////        comment.setHeadicosrc("imgs/youke.jpg");
////        comment.setCreatetime(timeStamp);
////        comment.setTime(sd);
////
////        System.out.println(comment);
//
////        flag = articleservice.savearticlecomments(comment);
//
//        flag = articleservice.selectusername(name);
//        System.out.println(flag);
//
//        return new R(flag,flag?"添加成功！":"添加失败");
//
//    }

}
