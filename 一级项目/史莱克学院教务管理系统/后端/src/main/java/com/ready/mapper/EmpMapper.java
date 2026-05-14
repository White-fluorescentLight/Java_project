package com.ready.mapper;

import com.ready.pojo.Emp;
import com.ready.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/*
 * 员工信息
 */
public interface EmpMapper {

    /**
     * 条件查询员工基本信息
     */
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息
     */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 获取到生成的主键 -- 主键返回
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            " values (#{username}, #{name}, #{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime})")
    void insert(Emp emp);


    /**
     * 根据ID批量删除员工基本信息
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据ID查询员工基本信息及工作经历信息
     */
    Emp getById(Integer id);

    /**
     * 根据ID更新员工基本信息
     */
    void updateById(Emp emp);

    /**
     * 统计员工职位人数
     */
    @MapKey("pos")
    List<Map<String, Object>> countEmpJobData();

    /**
     * 统计员工性别人数
     */
    @MapKey("name")
    List<Map<String, Object>> countEmpGenderData();

    /**
     * 根据用户名和密码查询员工信息
     */
    @Select("select id, username, name from emp where username=#{username} and password=#{password}")
    Emp selectByUsernameAndPassword(Emp emp);
}
