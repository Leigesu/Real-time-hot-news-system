package com.example.springyemian.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.logging.Handler;

public class MyInterceptor implements HandlerInterceptor  {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        try {
//            System.out.println(request.getRequestURL());
//            System.out.println(request.getRemoteAddr());
//            System.out.println("拦截到了---------");
//            if (request.getSession().getAttribute("user")!=null)
//            return true;
//            response.sendRedirect("/index.html");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    @Override
//    上面这一步是在请求处理完成之后进行的调用(Controller方法之后)
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("执行了Posthandler方法");
    }

    @Override
//    最后一步是在请求结束之后被调用,渲染对应的视图之后执行,一般是用于资源清理.
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("执行了afterCompletion");
    }
}
