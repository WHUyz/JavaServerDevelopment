package com.whuyz.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whuyz.reggie.entity.OrderDetail;
import com.whuyz.reggie.entity.Orders;
import com.whuyz.reggie.mapper.OrderDetailMapper;
import com.whuyz.reggie.mapper.OrdersMapper;
import com.whuyz.reggie.service.OrderDetailService;
import com.whuyz.reggie.service.OrdersService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService{
}

//@Service
//public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
//}
