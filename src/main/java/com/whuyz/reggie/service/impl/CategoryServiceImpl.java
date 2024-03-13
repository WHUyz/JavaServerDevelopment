package com.whuyz.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whuyz.reggie.common.CustomException;
import com.whuyz.reggie.common.R;
import com.whuyz.reggie.entity.Category;
import com.whuyz.reggie.entity.Dish;
import com.whuyz.reggie.entity.Employee;
import com.whuyz.reggie.entity.Setmeal;
import com.whuyz.reggie.mapper.CategoryMapper;
import com.whuyz.reggie.mapper.EmployeeMapper;
import com.whuyz.reggie.service.CategoryService;
import com.whuyz.reggie.service.DishService;
import com.whuyz.reggie.service.EmployeeService;
import com.whuyz.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    //根据id删除分类，删除之前需要进行判断
    @Override
    public void remove(Long id) {
        //查询分类是否已经关联了菜品，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        if(dishService.count(dishLambdaQueryWrapper) > 0)
          //已经关联了菜品，抛出业务异常
           throw  new CustomException("当前分类下关联了菜品！无法删除");
        //查询分类是否已经关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        if(setmealService.count(setmealLambdaQueryWrapper) > 0)
        {//已经关联了套餐，抛出业务异常}
            throw new CustomException("当前分类下关联了套餐！无法删除");
        }
            super.removeById(id);

    }

    @Override
    public void modify(Category category) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, category.getId());
        if(dishService.count(dishLambdaQueryWrapper) > 0){
            throw new CustomException("该种类下关联了菜品，请先对菜品信息进行修改！");
        }

    }
}

