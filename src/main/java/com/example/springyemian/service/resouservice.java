package com.example.springyemian.service;

import com.example.springyemian.domain.Resou;

import java.util.List;

public interface resouservice {
    Boolean save(Resou resou);
    Boolean update(Resou resou);
//    Boolean delete(int id);
//    Resou getById(int id);
    List<Resou> getAll();
}
