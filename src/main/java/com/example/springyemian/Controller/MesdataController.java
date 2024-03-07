package com.example.springyemian.Controller;

import com.example.springyemian.Controller.utils.R;
import com.example.springyemian.domain.Guess;
import com.example.springyemian.domain.User;
import com.example.springyemian.domain.Xiazhu;
import com.example.springyemian.service.Mesdataservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/mesdatas")
public class MesdataController {
    @Autowired
    private Mesdataservice mesdataservice;

    @GetMapping
    public R getAll(){
        return new R(true,mesdataservice.getAll());
    }

    @GetMapping("/{classid}/{detailid}")
    public R getbyid(@PathVariable String classid,@PathVariable String detailid){
        if (classid.equals("0")){
            if (detailid.equals("1")){
                return new R(true,mesdataservice.getbyclassid("sex","男"));
            }else {
                return new R(true,mesdataservice.getbyclassid("sex","女"));
            }
        }else if (classid.equals("1")){
            if (detailid.equals("3")){
                return new R(true,mesdataservice.getbyclassid("major","生命科学与化学学院"));
            } else if (detailid.equals("4")) {
                return new R(true,mesdataservice.getbyclassid("major","材料与先进制造学院"));
            }else if (detailid.equals("5")) {
                return new R(true,mesdataservice.getbyclassid("major","文学与新闻传播学院"));
            }
            else if (detailid.equals("6")) {
                return new R(true,mesdataservice.getbyclassid("major","城市与环境学院"));
            }
            else if (detailid.equals("7")) {
                return new R(true,mesdataservice.getbyclassid("major","包装与材料工程学院"));
            }
            else if (detailid.equals("8")) {
                return new R(true,mesdataservice.getbyclassid("major","法学院"));
            }
            else if (detailid.equals("9")) {
                return new R(true,mesdataservice.getbyclassid("major","现代教育技术中心"));
            }
            else if (detailid.equals("10")) {
                return new R(true,mesdataservice.getbyclassid("major","城环学院"));
            }
            else if (detailid.equals("13")) {
                return new R(true,mesdataservice.getbyclassid("major","计算机学院"));
            }
            else if (detailid.equals("14")) {
                return new R(true,mesdataservice.getbyclassid("major","包装设计艺术学院"));
            }
        } else if (classid.equals("2")) {
            if (detailid.equals("11")){
//               湖南
                return new R(true,mesdataservice.getbyclassid("province","湖南"));
            }else if (detailid.equals("12")) {
//                外省
                return new R(true,mesdataservice.getbyclassid1("province","湖南"));
            }
        }

        return new R(true,"error!");
    }

    @GetMapping("/guess")
    public R getrate(){
        return new R(true,mesdataservice.getrate());
    }

    @GetMapping("/user")
    public R getuser(){
        return new R(true,mesdataservice.getuser());
    }

    @PostMapping("guess/{position}/{num}/{id}/{userid}")
    public R touzhu1(@PathVariable String position,@PathVariable int num,@PathVariable int id,@PathVariable int userid){
        System.out.println(position+"----------"+num);

        chulitouzhu(mesdataservice,position,num,id,userid);

        return new R(true,"true");
    }

    @PostMapping("guess/submit/{position}/{saishiid}")
    public R jiesuanbyid(@PathVariable String position,@PathVariable int saishiid){
        List<Xiazhu> xiazhu = mesdataservice.jiesuanbyid(position,saishiid);
        for (int i = 0; i < xiazhu.size(); i++) {
            Xiazhu xiazhu1 =  xiazhu.get(i);
            User user = new User();
            user = mesdataservice.getuser();

//            实际上
//            mesdataservice.getuserbyid(xiazhu1.getUserid());

            mesdataservice.updateusersur(
            (int) (user.getSurplus()+ (xiazhu1.getXiazhujine() * (xiazhu1.getXiazhurate()+1))),
            xiazhu1.getUserid());
        }
        mesdataservice.jiesuanshanku(saishiid);

        mesdataservice.icmoney(1000,saishiid);
        mesdataservice.icmoney2(1000,saishiid);
        mesdataservice.icmoneytotal(2000,saishiid);
        mesdataservice.updaterate(1,1,saishiid);

        return new R(true,true);
    }


    public static void chulitouzhu(Mesdataservice mesdataservice, String position, int num,int id,int userid){
        Guess guess = new Guess();
        guess = mesdataservice.selectsur(id);

        User user = new User();
        user = mesdataservice.getuser();
        mesdataservice.updateusersur((int) (user.getSurplus()-num),userid);


        if (position.equals("l")){
//              查询左池总金额并更新左池及总池
            mesdataservice.icmoney(guess.getLeftsur()+num,id);
            mesdataservice.icmoneytotal(guess.getTotal()+num,id);
            guess = mesdataservice.selectsur(id);
            System.out.println(guess);

            mesdataservice.insertguess_user(userid,num,id,"l",guess.getLeftrate());

            float lrate  = 1/((float)guess.getLeftsur()/ guess.getTotal());
            float rrate  = (float)1/((float)guess.getRightsur()/ guess.getTotal());


            lrate  = (float)Math.round(lrate*100)/100 - 1;
            rrate  = (float)Math.round(rrate*100)/100 - 1;

            if (lrate <0){
                lrate = (float) 0.01;
            } else if (lrate>2) {
                lrate = (float) 2;
            }

            if (rrate < 0){
                rrate = (float) 0.01;
            } else if (rrate>2) {
                rrate = (float) 2;
            }

            mesdataservice.updaterate(lrate,rrate,id);

        }
        else if (position.equals("r")){

            mesdataservice.icmoney2(guess.getRightsur()+num,id);
            mesdataservice.icmoneytotal(guess.getTotal()+num,id);
            guess = mesdataservice.selectsur(id);

            mesdataservice.insertguess_user(userid,num,id,"r",guess.getRightrate());

            float lrate  = 1/((float)guess.getLeftsur()/ guess.getTotal());
            float rrate  = (float)1/((float)guess.getRightsur()/ guess.getTotal());

            lrate  = (float)Math.round(lrate*100)/100 - 1;
            rrate  = (float)Math.round(rrate*100)/100 - 1;

            if (lrate < 0){
                lrate = (float) 0.01;
            } else if (lrate > 2) {
                lrate = (float) 2;
            }

            if (rrate < 0){
                rrate = (float) 0.01;
            } else if (rrate>2) {
                rrate = (float) 2;
            }

            //再次进行查询并重新计算比率
            mesdataservice.updaterate(lrate,rrate,id);

        }
    }
}
