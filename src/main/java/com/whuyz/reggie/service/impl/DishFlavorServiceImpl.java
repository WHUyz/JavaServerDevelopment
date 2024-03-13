package com.whuyz.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whuyz.reggie.entity.Dish;
import com.whuyz.reggie.entity.DishFlavor;
import com.whuyz.reggie.mapper.DishFlavorMapper;
import com.whuyz.reggie.mapper.DishMapper;
import com.whuyz.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
