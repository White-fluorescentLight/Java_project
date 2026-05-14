package com.ready.mapper;

import com.ready.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 部门管理 Mapper 接口
 */
public interface DeptMapper {

    /**
     * 查询所有部门
     */
    @Select("select * from dept order by update_time desc")
    List<Dept> findAll();

    /**
     * 根据ID删除部门
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    /**
     * 新增部门
     */
    @Insert("insert into dept (name, create_time, update_time) values (#{name}, #{createTime}, #{updateTime})")
    void add(Dept dept);

    /**
     * 根据ID查询部门
     */
    @Select("select * from dept where id = #{id}")
    Dept getById(Integer id);

    /**
     * 修改部门
     */
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
