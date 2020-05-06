package com.github.davidfantasy.springclouddemo.orderservice.remote;

import com.github.davidfantasy.springclouddemo.orderservice.dto.InventoryChangeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StorageServiceFallback implements StorageService {
    @Override
    public Integer updateInventoryOfGood(InventoryChangeDTO inventoryChangeDTO) {
        log.error("StorageServiceFallback.updateInventoryOfGood暂不可用");
        return -1;
    }
}
