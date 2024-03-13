package com.whuyz.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whuyz.reggie.dto.DishDto;
import com.whuyz.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表
    public void saveWithFlavor(DishDto dishDto);
    //根据菜品id查询口味信息
    public DishDto getByIdWithFlavor(Long id);
    public void updateWithFlavor(DishDto dishDto);
}
