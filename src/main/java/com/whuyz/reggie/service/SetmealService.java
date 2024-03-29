package com.whuyz.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whuyz.reggie.dto.SetmealDto;
import com.whuyz.reggie.entity.Dish;
import com.whuyz.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    //新增套餐同时保存套餐和菜品的关联关系
    public void saveWithDish(SetmealDto setmealDto);
    public void removeWithDish(List<Long> ids);
}
