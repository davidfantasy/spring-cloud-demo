package com.github.davidfantasy.springclouddemo.orderservice.dto;

import lombok.Data;

@Data
public class InventoryChangeDTO {

    String goodCode;

    Integer quantity;

}
