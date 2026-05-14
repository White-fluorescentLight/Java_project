package com.ready.controller;

import com.ready.pojo.Dept;
import com.ready.pojo.Result;
import com.ready.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询部门列表
     */
    @GetMapping
    public Result list() {
        log.info("查询部门列表");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }

    /**
     * 删除部门
     */
    @DeleteMapping
    public Result delete(Integer id) {
        log.info("删除部门：{}", id);
        deptService.deleteById(id);
        return Result.success();
    }

    /**
     * 新增部门
     */
     @PostMapping
     public Result add(@RequestBody Dept dept) {
         log.info("新增部门：{}", dept);
         deptService.add(dept);
         return Result.success();
     }

     /**
      * 根据ID查询部门
      */
     @GetMapping("/{id}")
      public Result get(@PathVariable Integer id) {
          log.info("查询部门：{}", id);
          Dept dept = deptService.getById(id);
          return Result.success(dept);
     }

     /**
      * 修改部门
      */
      @PutMapping
      public Result update(@RequestBody Dept dept) {
          log.info("修改部门：{}", dept);
          deptService.update(dept);
          return Result.success();
      }
}

