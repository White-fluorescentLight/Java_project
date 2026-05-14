package com.ready.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装后端返回给前端的分页查询结果
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private Long total;      // 总记录数
    private List<T> rows;    // 当前页数据列表
}