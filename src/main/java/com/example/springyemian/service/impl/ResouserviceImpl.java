package com.example.springyemian.service.impl;

import com.example.springyemian.dao.Resoudao;
import com.example.springyemian.domain.Resou;
import com.example.springyemian.service.resouservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResouserviceImpl implements resouservice {

    @Autowired
    private Resoudao resoudao;

    @Override
    public Boolean save(Resou resou) {
        return resoudao.insert(resou)>0;
    }

    @Override
    public Boolean update(Resou resou) {
        return resoudao.update(resou)>0;
    }
//
//    @Override
//    public Boolean delete(int id) {
//        return resoudao.deleteById(id)>0;
//    }
//
//    @Override
//    public Resou getById(int id) {
//        return resoudao.selectById(id);
//    }

    @Override
    public List<Resou> getAll() {
        return  resoudao.getAll();
    }

//    @Override
//    public IPage getPage(int currentPage, int PageSize, Mingren mingren){
//        IPage<Mingren> page = new Page(currentPage,PageSize);
//
//        LambdaQueryWrapper<Mingren> lqw = new LambdaQueryWrapper<>();
//
//        lqw.like(!Strings.isEmpty(mingren.getSta()),Mingren::getSta,mingren.getSta());
//        lqw.like(!Strings.isEmpty(mingren.getName()),Mingren::getName,mingren.getName());
//        lqw.like(!Strings.isEmpty(mingren.getCompany()),Mingren::getCompany,mingren.getCompany());
//        lqw.like(!Strings.isEmpty(mingren.getKouhao()),Mingren::getKouhao,mingren.getKouhao());
//
//
//        return mingrendao.selectPage(page,lqw);
//    }


}
