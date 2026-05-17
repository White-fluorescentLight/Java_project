package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {
    /**
     * 根据菜品id集合查询关联的套餐id集合
     */
    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);
}
