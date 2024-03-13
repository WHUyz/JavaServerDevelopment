package com.whuyz.reggie.dto;

import com.whuyz.reggie.entity.OrderDetail;
import com.whuyz.reggie.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
