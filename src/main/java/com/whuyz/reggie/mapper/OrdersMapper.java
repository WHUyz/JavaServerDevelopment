package com.whuyz.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whuyz.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
