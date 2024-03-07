package com.example.springyemian;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.alibaba.fastjson.JSONObject;
import com.example.springyemian.Controller.utils.JedisConnectionFactory;
import com.example.springyemian.dao.Articledao;
import com.example.springyemian.dao.Resoudao;
import com.example.springyemian.domain.Resou;
import com.example.springyemian.domain.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import redis.clients.jedis.Jedis;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringTestsApplication{
    private Jedis jedis;

    @Autowired
    private Resoudao resoudao;
    @Autowired
    private Articledao articledao;

    @Test
    void contextLoads() {
        List<Resou> mingrens = resoudao.getAll();
        for (int i = 0; i < mingrens.size(); i++) {
            System.out.println(mingrens.get(i).getContent());
        }
    }
    @Test
    void contextLoads2(){

        articledao.getAll();

    }
    @Test
    void contextLoads3(){
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100,4,30);
//         图形验证码写出到指定文件

//        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
//        // 自定义验证码内容为四则运算方式
//        lineCaptcha.setGenerator(new MathGenerator());
//        // 重新生成code
//        lineCaptcha.createCode();
//        // 重新生成code
//        lineCaptcha.createCode();

        Long timeStamp = System.currentTimeMillis();
        System.out.println(timeStamp);
        String path = "C:\\Users\\Administrator\\Desktop\\yzm\\yzm-"+timeStamp+".png";

        lineCaptcha.write(path);
        String name = "cxk117";
        String code = lineCaptcha.getCode();
        String change = "117294";
        String pathandcode = path +change +code;
        // verify()验证图形验证码的有效性，返回boolean值
//        System.out.println(lineCaptcha.verify("ABCD"));
//        System.out.println(lineCaptcha.verify(lineCaptcha.getCode()));

        jedis = JedisConnectionFactory.getjedis();
        jedis.select(0);
        String codeandpath = jedis.get(name);

        if (codeandpath != null){
            jedis.del(name);
        }
        jedis.setex("cxk117",60, pathandcode);
        codeandpath = jedis.get(name);
        String realpath = codeandpath.split(change)[0];
        String realcode = codeandpath.split(change)[1];
        System.out.println(realcode.equals(code));
    }
    @Test
    void contextLoads4(){
        //最小路径和
        int[][] grid = {{1,3,1},{1,5,1},{4,2,1}};
        int[][] dp = new int[grid.length][grid[0].length];

        dp[0][0] = grid[0][0];
        for (int i = 1; i < grid[0].length; i++) {
            dp[0][i]  = dp[0][i-1]+ grid[0][i];
        }
        for (int i = 1; i <grid.length ; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }

        for (int i = 1; i <grid.length; i++) {
            for (int j = 1; j <grid[0].length ; j++) {
                dp[i][j] = grid[i][j] + Math.min(dp[i-1][j],dp[i][j-1]);
            }
        }

        System.out.println(Arrays.deepToString(dp));
        System.out.println(dp[grid.length-1][grid[0].length-1]);

    }
    @Test
    void ti1(){
        String[] words = {"cool","lock","cook"};
        int count = 1;
        ArrayList arrayList = new ArrayList();
        for (int j = 0; j < words[0].length(); j++) {
            for (int k = 1; k < words.length; k++) {
                if (words[k].contains(String.valueOf(words[0].charAt(j)))){
                    count++;
                }
            }
            if (count==words.length) arrayList.add(words[0].charAt(j));
            count = 0;
        }
        System.out.println(arrayList);






    }
    @Test
    void ti2(){
        String s = "abecdfe";
        char c = 'e';
        int[] nums = new int[s.length()];

        int left = 0;
        int right = 0;
        int temp = 0;
        int temp2 = 0;
        int count = 0;

        while (right<s.length()){
            if (s.charAt(right)==c){
                while (left<=right){
                    if (count==0)nums[left] = right - left;
                    else nums[left] = Math.min(right - left,left-temp2);
                    left++;
                }
                count = 1;
                temp2 = left-1;
            }
            if (right==s.length()-1){
                temp = left-1;
                while (left<=right){
                    nums[left] = left - temp;
                    left++;
                }
            }
            right++;
        }

//        int[] nums2 = new int[s.length()];
//        left = s.length()-1;
//        right = s.length()-1;
//        while (left>=0){
//            if (s.charAt(left)==c){
//                while (right>=left){
//                    nums2[right] = right - left;
//                    right--;
//                }
//            }
//            if (left==0){
//                while (right>=left){
//                    nums2[right] = right - left;
//                    right--;
//                }
//            }
//            left--;
//        }

        System.out.println(Arrays.toString(nums));
//        System.out.println(Arrays.toString(nums2));









    }
    @Test
    public void getFormatDate(){

        Long timeStamp = System.currentTimeMillis();  //获取当前时间戳
        System.out.println(timeStamp);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(Long.parseLong(String.valueOf(timeStamp)));
        String sd2 = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        String sd3 = sdf.format(timeStamp);
        System.out.println("格式化结果：" + sd);
        System.out.println("格式化结果：" + sd2);
        System.out.println("格式化结果：" + sd3);


    }
    @Test
    public void asd(){
        jedis = JedisConnectionFactory.getjedis();
        jedis.select(0);


        List<Article> articles = articledao.getuserarticlebyid(1);
        String article = JSONObject.toJSON(articles).toString();
//        jedis.set("user",article);

        article = jedis.get("user");
        System.out.println((List<Article>) JSONObject.parseArray(article,Article.class));
        jedis.close();

    }

    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Test
    public void sendmailtest(){
        String useremail = "1172944356@qq.com";
        Random random = new Random();
        int r = random.nextInt(100000,999999);

        jedis = JedisConnectionFactory.getjedis();
        jedis.auth("s1172944356");
        jedis.select(0);
        jedis.setex(useremail,60*30, String.valueOf(r));

        jedis.close();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("2700337833@qq.com");
        simpleMailMessage.setTo(useremail);
        simpleMailMessage.setSubject("磊哥科技");
        simpleMailMessage.setText("【磊哥科技】您本次注册的验证码为"+r+",请在三十分钟内完成注册！");

        javaMailSender.send(simpleMailMessage);


    }

    @Test
    public void xiancheng(){
        Thread t1 = new Thread(()->{
            System.out.println("t1111111111111");
        });
        Thread t2 = new Thread(()->{
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t2222222222222222");
        });
        Thread t3 = new Thread(()->{
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("t333333333333333333333");
        });

        t3.start();
        t1.start();
        t2.start();
    }


    Object lock = new Object();
    int ticketnum = 20;
    void getticket(){
        synchronized (lock){
            if (ticketnum <=0){
                return;
            }
            System.out.println("抢票成功！还剩"+ ticketnum--+"张票。");
        }
    }

    @Test
    public void qiangpiao(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
              getticket();
//              sendmailtest();
            }).start();
        }
//        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    public static void quickSort(int[] arry,int left,int right){
        //运行判断，如果左边索引大于右边是不合法的，直接return结束次方法
        if(left>right){
            return;
        }
        //定义变量保存基准数
        int base = arry[left];
        //定义变量i，指向最左边
        int i = left;
        //定义j ,指向最右边
        int j = right;
        //当i和j不相遇的时候，再循环中进行检索
        while(i!=j){
            //先由j从右往左检索比基准数小的，如果检索到比基准数小的就停下。
            //如果检索到比基准数大的或者相等的就停下
            while(arry[j]>=base && i<j){
                j--; //j从右往左检索

            }
            while(arry[i]<=base && i<j){
                i++; //i从左往右检索
            }
            //代码走到这里i停下，j也停下，然后交换i和j位置的元素
            int tem = arry[i];
            arry[i] = arry[j];
            arry[j] = tem;


        }
        //如果上面while条件不成立就会跳出这个循环，往下执行
        //如果这个条件不成立就说明 i和j相遇了
        //如果i和j相遇了，就交换基准数这个元素和相遇位置的元素
        //把相遇元素的值赋给基准数这个位置的元素
        arry[left] = arry[i];
        //把基准数赋给相遇位置的元素
        arry[i] = base;
        //基准数在这里递归就为了左边的数比它小，右边的数比它大
        //排序基准数的左边
        quickSort(arry,left,i-1);
        //排右边
        quickSort(arry,j+1,right);

    }


    @Test
    public  void kuaisupaixu(){
        int[] nums = new int[100000];
        for (int i = 0; i < nums.length; i++) {
            nums[i] = new Random().nextInt(1,999999);
        }
        int[] num = new int[nums.length];
        for (int i = 0; i < num.length; i++) {
            num[i] = nums[i];
        }

        {
            Date startTime = new Date();

            int maopaocount = 0;
            for (int i = 0; i < num.length; i++) {
                for (int j = i + 1; j < num.length; j++) {
                    if (num[i] > num[j]) {
                        int temp = num[i];
                        num[i] = num[j];
                        num[j] = temp;
                    }
                    maopaocount++;
                }
                maopaocount++;
            }
//            System.out.println(Arrays.toString(num));
            System.out.println("循环次数：" + maopaocount);

            Date endTime = new Date();
            long duration = endTime.getTime() - startTime.getTime();
            System.out.println("间隔时间：" + duration + "ms");
        }

        {
            for (int i = 0; i < num.length; i++) {
                num[i] = nums[i];
            }
            Date startTime = new Date();
        int begin = 0;int end = num.length;
        int xuanzecount= 0;
        int min = Integer.MIN_VALUE;
        int maxi = begin;
        while (begin<end){
            int mini = begin;
            for (int i = begin; i <end ; i++) {
                if (num[i] < num[mini]){
                    mini = i;
                }
                xuanzecount++;
            }
            int temp = num[begin];
            num[begin] = num[mini];
            num[mini] = temp;
            begin++;
            xuanzecount++;
        }
//        System.out.println(Arrays.toString(num));
        System.out.println("循环次数："+xuanzecount);

            Date endTime = new Date();
            long duration = endTime.getTime() - startTime.getTime();
            System.out.println("间隔时间：" + duration + "ms");
    }

        {
            for (int i = 0; i < num.length; i++) {
                num[i] = nums[i];
            }
            Date startTime = new Date();
            quickSort(num,0,num.length-1);
            Date endTime = new Date();
            long duration = endTime.getTime() - startTime.getTime();
            System.out.println("间隔时间：" + duration + "ms");

        }
    }

























}
