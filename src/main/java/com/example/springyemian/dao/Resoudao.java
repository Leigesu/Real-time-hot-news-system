package com.example.springyemian.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springyemian.domain.Resou;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface Resoudao extends BaseMapper<Resou> {
    @Select("select * from resou order by hot desc")
    public List<Resou> getAll();
    @Update("update resou set hot =#{hot} where content =#{content}")
    public Integer update(Resou resou);

}
