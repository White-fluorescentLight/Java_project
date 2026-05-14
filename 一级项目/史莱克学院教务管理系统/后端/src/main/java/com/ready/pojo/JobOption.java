package com.ready.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装员工职位统计数据的
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOption {
    private List jobList; //职位列表
    private List dataList; //数据列表
}
