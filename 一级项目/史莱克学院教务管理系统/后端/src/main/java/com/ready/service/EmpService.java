package com.ready.service;

import com.ready.pojo.Emp;
import com.ready.pojo.EmpQueryParam;
import com.ready.pojo.LoginInfo;
import com.ready.pojo.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {

    /**
     * 分页查询
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 新增员工
     */
    void save(Emp emp);

    /**
     * 删除员工
     */
    void deleteById (List<Integer> id);

    /**
     * 根据ID查询员工
     */
    Emp getInfo(Integer id);

    /**
     * 修改员工
     */
    void update(Emp emp);

    /**
     * 登录
     */
    LoginInfo login(Emp emp);
}
