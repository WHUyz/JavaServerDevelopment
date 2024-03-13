package com.whuyz.reggie.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whuyz.reggie.entity.ShoppingCart;
import com.whuyz.reggie.mapper.ShoppingCartMapper;
import com.whuyz.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
