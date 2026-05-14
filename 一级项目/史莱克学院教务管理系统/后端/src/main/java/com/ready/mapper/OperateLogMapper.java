package com.ready.mapper;

import com.ready.pojo.OperateLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OperateLogMapper {

    //插入日志数据
    @Insert("insert into operate_log (operate_emp_id, operate_time, class_name, method_name, method_params, return_value, cost_time) " +
            "values (#{operateEmpId}, #{operateTime}, #{className}, #{methodName}, #{methodParams}, #{returnValue}, #{costTime});")
    public void insert(OperateLog log);

    //分页查询日志
    @Select("SELECT ol.*, e.username as operateEmpName FROM operate_log ol LEFT JOIN emp e ON ol.operate_emp_id = e.id ORDER BY ol.operate_time DESC LIMIT #{start}, #{pageSize}")
    List<OperateLog> queryPage(Integer start, Integer pageSize);

    //查询日志总数
    @Select("select count(*) from operate_log")
    Integer count();

}