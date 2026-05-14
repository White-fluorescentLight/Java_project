package com.ready.service;

import com.ready.pojo.Clazz;
import com.ready.pojo.PageResult;
import java.time.LocalDate;
import java.util.List;

public interface ClazzService {

    /**
     * 添加班级信息
     */
    void save(Clazz clazz);

    /**
     * 分页查询班级
     */
    PageResult page(String name, LocalDate begin, LocalDate end, Integer page, Integer pageSize);

    /**
     * 根据ID查询班级详情
     */
    Clazz getInfo(Integer id);

    /**
     * 修改班级信息
     */
    void update(Clazz clazz);

    /**
     * 删除班级
     */
    void deleteById(Integer id);

    /**
     * 查询全部班级
     */
    List<Clazz> findAll();
}
