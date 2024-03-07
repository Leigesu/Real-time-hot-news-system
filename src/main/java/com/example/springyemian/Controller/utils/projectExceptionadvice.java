package com.example.springyemian.Controller.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class projectExceptionadvice {
    @ExceptionHandler(Exception.class)
    public R doOtherException(Exception ex){
    ex.printStackTrace();
    return new R("系统错误请稍后再试！");
}

}
