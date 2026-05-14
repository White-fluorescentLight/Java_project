package com.ready.mapper;

import com.ready.pojo.EmpExpr;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工工作经历
 */
@Mapper
public interface EmpExprMapper {
    /**
     * 批量保存员工工作经历信息
     * @param exprList 工作经历列表
     */
    void insertBatch(@Param("exprList") List<EmpExpr> exprList);

    /**
     * 根据员工ID批量删除员工工作经历信息
     */
    void deleteByEmpId(List<Integer> empids);
}
