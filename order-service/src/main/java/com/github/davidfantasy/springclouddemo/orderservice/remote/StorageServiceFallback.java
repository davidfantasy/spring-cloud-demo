package com.github.davidfantasy.springclouddemo.orderservice.remote;

import com.github.davidfantasy.springclouddemo.orderservice.dto.InventoryChangeDTO;
import org.springframework.stereotype.Component;

@Component
public class StorageServiceFallback implements StorageService {

    public Integer updateInventoryOfGood(InventoryChangeDTO inventoryChangeDTO) {
        return -1;
    }

}
