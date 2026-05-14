package com.ready.service;

import com.ready.pojo.ClazzCountOption;
import com.ready.pojo.JobOption;

import java.util.List;
import java.util.Map;

public interface ReportService {

    /**
     * 统计员工职位人数
     */
    JobOption getEmpJobData();

    /**
     * 统计员工性别人数
     */
    List<Map<String, Object>> getEmpGenderData();

    /**
     * 统计学员学历人数
     */
    List<Map<String, Object>> getStudentDegreeData();

    /**
     * 统计班级人数
     */
    ClazzCountOption getStudentCountData();

}