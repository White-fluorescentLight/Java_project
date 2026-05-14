package com.ready.service.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ready.mapper.EmpExprMapper;
import com.ready.mapper.EmpMapper;
import com.ready.pojo.*;
import com.ready.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements com.ready.service.EmpService {

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private EmpExprMapper empExprMapper;

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        // 1.设置分页参数
        PageHelper.startPage(empQueryParam.getPage(), empQueryParam.getPageSize());

        // 2.执行查询
        List<Emp> empList = empMapper.list(empQueryParam);

        // 3.解析查询结果，并封装为PageResult对象
        Page<Emp> p = (Page<Emp>) empList;
        return new PageResult<Emp>(p.getTotal(), p.getResult());
    }

    @Transactional //事务管理
    @Override
    public void save(Emp emp) {
        // 1.保存基本信息
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        //2. 保存员工工作经历信息
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            // 遍历集合为，为empId赋值
            exprList.forEach(expr -> expr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(List<Integer> ids) {
        // 1.删除员工基本信息
        empMapper.deleteByIds(ids);

        // 2.删除员工工作经历信息
        empExprMapper.deleteByEmpId(ids);
    }

    @Override
    public Emp getInfo(Integer id) {
        return empMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(Emp emp) {
        //1. 根据ID修改员工的基本信息
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.updateById(emp);

        //2. 根据ID修改员工的工作经历信息
        //2.1 先根据员工ID删除原有的工作经历
        empExprMapper.deleteByEmpId(Arrays.asList(emp.getId()));

        //2.2 再添加这个员工新的工作经历
        List<EmpExpr> exprList = emp.getExprList();
        if(!CollectionUtils.isEmpty(exprList)){
            exprList.forEach(empExpr -> empExpr.setEmpId(emp.getId()));
            empExprMapper.insertBatch(exprList);
        }
    }

    @Override
    public LoginInfo login(Emp emp) {
        //1. 调用mapper接口，根据用户名和密码查询员工信息
        Emp e = empMapper.selectByUsernameAndPassword(emp);

        //2. 判断：判断是否存在这个员工，如果存在，组装登录成功信息
        if(e != null){
            log.info("登录成功，员工信息：{}", e);
            //生成JWT令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", e.getId());
            claims.put("username", e.getUsername());
            String jwt = JwtUtils.generateJwt(claims);

            return new LoginInfo(e.getId(), e.getUsername(), e.getName(), jwt);
        }

        //3. 不存在，返回null
        return null;
    }
}
