package com.github.davidfantasy.springclouddemo.orderservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_order")
public class Order {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String customerCode;

    private String goodCode;

    private Integer goodQuantity;

}
