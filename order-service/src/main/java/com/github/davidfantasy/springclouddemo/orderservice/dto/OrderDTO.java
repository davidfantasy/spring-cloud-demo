package com.github.davidfantasy.springclouddemo.orderservice.dto;

import lombok.Data;

@Data
public class OrderDTO {

    private String customerCode;

    private String goodCode;

    private Integer quantity;

}
