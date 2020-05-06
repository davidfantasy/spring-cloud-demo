package com.github.davidfantasy.springclouddemo.storageservice.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_inventory")
public class Inventory {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String goodCode;

    private Integer goodQuantity;

}
