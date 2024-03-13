package com.whuyz.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whuyz.reggie.dto.DishDto;
import com.whuyz.reggie.entity.Dish;
import com.whuyz.reggie.entity.DishFlavor;
import com.whuyz.reggie.entity.Employee;
import com.whuyz.reggie.mapper.DishMapper;
import com.whuyz.reggie.mapper.EmployeeMapper;
import com.whuyz.reggie.service.DishFlavorService;
import com.whuyz.reggie.service.DishService;
import com.whuyz.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    //新增菜品同时保存对应的口味数据
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
       //保存菜品的基本信息到菜品表dish
        this.save(dishDto);
        Long dishId = dishDto.getId();
        //保存菜品口味数据到菜品口味表
        List<DishFlavor> falvors = dishDto.getFlavors();
        falvors = falvors.stream().map(s -> {
            s.setDishId(dishId);
            return s;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(falvors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
       //查询菜品基本信息
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
       //查询当前菜品的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);
        //清理当前菜品对应口味数据    --delete操作
        //构造查询器
        LambdaQueryWrapper<DishFlavor> queryWrapper= new LambdaQueryWrapper<>();
        //构造查询条件并调用Service层的remove
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        //添加当前提交过来的口味数据
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) ->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);

    }
}
