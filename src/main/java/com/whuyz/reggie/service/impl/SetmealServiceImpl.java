package com.whuyz.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whuyz.reggie.common.CustomException;
import com.whuyz.reggie.dto.SetmealDto;
import com.whuyz.reggie.entity.Employee;
import com.whuyz.reggie.entity.Setmeal;
import com.whuyz.reggie.entity.SetmealDish;
import com.whuyz.reggie.mapper.EmployeeMapper;
import com.whuyz.reggie.mapper.SetmealMapper;
import com.whuyz.reggie.service.EmployeeService;
import com.whuyz.reggie.service.SetmealDishService;
import com.whuyz.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;
    //新增套餐同时保存套餐和菜品的关联关系
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
     this.save(setmealDto);
     List<SetmealDish> list = setmealDto.getSetmealDishes();
     list.stream().map((item)->{
         item.setSetmealId(setmealDto.getId());
         return item;
     }).collect(Collectors.toList());
     setmealDishService.saveBatch(list);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(queryWrapper);
        if(count > 0){
            throw new CustomException("套餐正在售卖中，不能删除！");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getDishId, ids);
        setmealDishService.remove(queryWrapper1);


    }
}