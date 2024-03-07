package com.example.springyemian.Controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import com.example.springyemian.Controller.utils.JedisConnectionFactory;
import com.example.springyemian.Controller.utils.R;
import com.example.springyemian.domain.*;
import com.example.springyemian.utils.HttpURLConnectionUtils;
import com.example.springyemian.utils.IpUtil;
import com.example.springyemian.service.Articleservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/articles")

public class ArticleController {

    @Autowired
    private Articleservice articleservice;
    private Article article;
    private static Jedis jedis;
    @Autowired
    JavaMailSenderImpl javaMailSender;


    //    文章相关
    @GetMapping
    public R getAll(){
        return new R(true,articleservice.getAll());
    }

    @GetMapping("/time/{times}")
    public R getrefresh(@PathVariable int times){
        return new R(true,articleservice.getrefresh(times));
    }

    @GetMapping("/{id}")
    public R getbyid(@PathVariable String id){
        return new R(true,articleservice.getbyid(id));
    }

    @PostMapping("/dianzan/{count}/{id}")
    public R savedianzancount(@PathVariable int count,@PathVariable int id) {
        Boolean flag = false;
        flag = articleservice.updateartdianzancount(count,id);
        return new R(flag,flag?"添加成功！":"添加失败");
    }

    @GetMapping("/my/{id}")
    public R getuserarticlebyid(@PathVariable String id){
        return new R(true,articleservice.getuserarticlebyid(Integer.parseInt(id)));
    }
    @GetMapping("/search/{content}")
    public R getsearcharticlebyid(@PathVariable String content){
        return new R(true,articleservice.getsearchbycontent(content));
    }

    @PostMapping("/wzcount/{wzcount}/{id}")
    public R updateuserwzcount(@PathVariable  int wzcount,@PathVariable int id){
        return new R(true,articleservice.updateuserwzcount(wzcount, id));
    }
    @PostMapping
    public R save(@RequestBody Article art) {
        Boolean flag = true;

        Long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(Long.parseLong(String.valueOf(timeStamp)));

        art.setCreatetime(timeStamp);
        art.setCreatetimedetail(sd);

        System.out.println("\n\n\n\n"+art);

        flag = articleservice.save(art);
        return new R(flag,flag?"添加成功！":"添加失败");
    }

    //    用户相关
    @PostMapping("/users/headico/{id}")
    public R setuserheadico(@PathVariable int id, @RequestBody Strurl headico){
//        headico = headico.split("%2F")[1] + "/" + headico.split("%2F")[2];
//        headico = headico.split("\"")[0];
//        headico = headico.split("=")[0];
        System.out.println(headico);
        return new R(true,articleservice.setuserheadico(headico.getFm1(), id));
    }
    @GetMapping("/users/{name}/{pwd}")
    public R save(@PathVariable String name , @PathVariable String pwd, HttpServletRequest httpServletRequest,HttpSession httpSession) {
        boolean flag = false;
        User user = articleservice.selectusername(name);
        IpUtil ipUtil = new IpUtil();
        String ip = ipUtil.getIpAddr(httpServletRequest);
        System.out.println("\n\n\n"+ip+"\n\n\n");

        if (user!=null&& user.getPwd().equals(pwd)) {
            flag = true;
            httpSession.setAttribute("user",true);
            if (!user.getIp().equals(ip)){
                HttpURLConnectionUtils httpURLConnectionUtils = new HttpURLConnectionUtils();
                String from = httpURLConnectionUtils.doGet("http://opendata.baidu.com/api.php?query="+ip+"&co=&resource_id=6006&oe=utf8&qq-pf-to=pcqq.c2c");
//                System.out.println(from);
                try {
                    from = from.split("location\":\"")[1].split("\",\"origip")[0].split(" ")[0];
                }catch (Exception e){
                    from = "未知";
                }

//                System.out.println(from);
//                执行更新
                user.setIp(ip);
                user.setUserfrom(from);
                articleservice.updateuserip(user.getIp(), user.getUserfrom(), user.getId());
//                查询最新数据返回
                user = articleservice.selectusername(user.getUsername());
            }
        }
        else {
            user = new User();
        }

        return new R(flag,user);
    }

    @PostMapping("/users/code/{name}")
    public R getcode(@PathVariable String name){
     String src = getimgsrc(name);
     return new R(true,src);
    }

    @GetMapping("/userscode/{name}/{code}")
    public R checkcode(@PathVariable String name,@PathVariable String code){
        String realcode = getrediscode(name);
        if (realcode.equals("false")){
            return new R(false,"超时");
        }
        if (!realcode.equals(code) ){
            return new R(false,"错误");
        }
        return new R(true);
    }

    @GetMapping("/price")
    public R getAllPrice(){
        return new R(true,articleservice.getAllPrice());
    }

    @GetMapping("/theshit")
    public R getAllTheshit(){
        return new R(true,articleservice.getAllTheshit());
    }

    @PostMapping("/users/registcode/{name}")
    public R getregistcode(@PathVariable String name){
        if (articleservice.isusernameexist(name)==0){
            try {
                Random random = new Random();
                int r = random.nextInt(100000,999999);
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom("2700337833@qq.com");
                simpleMailMessage.setTo(name);
                simpleMailMessage.setSubject("磊哥科技");
                simpleMailMessage.setText("【磊哥科技】您本次注册的验证码为"+r+",请在三十分钟内完成注册！");
                javaMailSender.send(simpleMailMessage);

                jedis = JedisConnectionFactory.getjedis();
                jedis.auth("s1172944356");
                jedis.select(0);
                jedis.setex(name,60*30, String.valueOf(r));
                jedis.close();
                return new R(true,true);
            }catch (Exception e){
                System.out.println(e);
            }
        }else {
            return new R(true,"已有账号！",false);
        }
        return new R(false,false);
    }

    @GetMapping("/users/registcode/{name}/{code}")
    public R isregistcodetight(@PathVariable String name,@PathVariable String code){
        Boolean flag = false;
        try {
            jedis = JedisConnectionFactory.getjedis();
            jedis.auth("s1172944356");

            String realcode = jedis.get(name);
            flag = realcode.equals(code);
            jedis.close();

        }catch (Exception e){
            return new R(false,"服务器错误",false);
        }
        return new R(true,flag);
    }

    @PostMapping("/users/regist/{name}/{pwd}")
    public R getregist(@PathVariable String name,@PathVariable String pwd){

        return new R(true,articleservice.insertuser(name,pwd));
    }

//用户上传图片
    @PostMapping("/img/{id}")
    public R postimg(@PathVariable String id,@RequestParam("file") MultipartFile[] file) throws IOException {
        Boolean i = articleservice.postimg(id,file);
//        return new R(true);
        return new R(i);
    }
    @GetMapping("/img/{id}")
    public R  getimgbyid(@PathVariable String id){
        return new R(true,articleservice.getimgbyid(id));
    }


    //    文章评论
    @GetMapping("/comments/{articleid}")
    public R getAllComments(@PathVariable int articleid){
        return new R(true,articleservice.getAllComments(articleid));
    }
    @GetMapping("/comments/xin/{articleid}")
    public R getAllxinComments(@PathVariable int articleid){
        return new R(true,articleservice.getAllxinComments(articleid));
    }
    @PostMapping("/comments")
    public R save(@RequestBody Comments comment) {
        Boolean flag = true;

        Long timeStamp = System.currentTimeMillis();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(Long.parseLong(String.valueOf(timeStamp)));

        comment.setCreatetime(timeStamp);
        comment.setTime(sd);

        System.out.println("\n\n\n");
        System.out.println(comment);
        System.out.println("\n\n\n");

        flag = articleservice.savearticlecomments(comment) && articleservice.updatecommentcount(comment.getArticleid());

        return new R(flag,flag?"添加成功！":"添加失败");
    }
    @PostMapping("/comments/dianzan/{dianzanshu}/{id}")
    public R commentdianzan(@PathVariable int dianzanshu,@PathVariable int id){
        return new R(true,articleservice.commentdianzan(dianzanshu, id));
    }

    public static String getimgsrc(String name){
//        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100,4,30);
        // 定义图形验证码的长、宽、验证码字符数、干扰线宽度
        ShearCaptcha lineCaptcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 1);

        Long timeStamp = System.currentTimeMillis();
        String src = "yzm-"+timeStamp+".png";
//        String path = "C://Users//Administrator//Desktop//yzm//"+src;
        String path = "/webpro/yzm/"+src;

        lineCaptcha.write(path);
//        String name = "cxk117";
        String code = lineCaptcha.getCode();
        String change = "117294";
        String pathandcode = src +change +code;

        jedis = JedisConnectionFactory.getjedis();
        jedis.auth("s1172944356");
        jedis.select(0);
        String codeandpath = jedis.get(name);

        if (codeandpath != null){
            jedis.del(name);
        }
        jedis.setex(name,60, pathandcode);
        codeandpath = jedis.get(name);
        String realpath = codeandpath.split(change)[0];
        String realcode = codeandpath.split(change)[1];
        System.out.println(realcode.equals(code));
        jedis.close();
        return realpath;
    }

    public static String getrediscode(String name){
        String change = "117294";
        jedis = JedisConnectionFactory.getjedis();
        jedis.select(0);
        String codeandpath = jedis.get(name);
        if (codeandpath == null){
            return "false";
        }
        String realpath = codeandpath.split(change)[0];
        String realcode = codeandpath.split(change)[1];
        jedis.close();
        return realcode;
    }
}
