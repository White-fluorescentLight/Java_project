package com.ready.controller;

import com.ready.mapper.OperateLogMapper;
import com.ready.pojo.OperateLog;
import com.ready.pojo.PageResult;
import com.ready.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 操作日志控制器
 * 提供日志查询接口，用于前端展示
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class OperateLogController {

    @Autowired
    private OperateLogMapper operateLogMapper;

    /**
     * 分页查询操作日志
     * GET /log/page
     * @param page 页码（从1开始）
     * @param pageSize 每页大小
     */
    @GetMapping("/page")
    public Result queryPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        
        log.info("分页查询操作日志: page={}, pageSize={}", page, pageSize);

        // 计算起始索引
        int start = (page - 1) * pageSize;

        // 查询分页数据
        List<OperateLog> logs = operateLogMapper.queryPage(start, pageSize);

        // 查询总记录数并转换为Long类型
        Long total = operateLogMapper.count().longValue();

        // 封装分页结果
        PageResult<OperateLog> pageResult = new PageResult<>();
        pageResult.setTotal(total);
        pageResult.setRows(logs);

        return Result.success(pageResult);
    }
}