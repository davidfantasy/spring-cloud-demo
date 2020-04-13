package com.github.davidfantasy.springclouddemo.orderservice.remote;

import com.github.davidfantasy.springclouddemo.orderservice.dto.InventoryChangeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "storage-service", fallback = StorageServiceFallback.class)
public interface StorageService {

    @PostMapping("/api/storage/change-inventory")
    Integer updateInventoryOfGood(InventoryChangeDTO inventoryChangeDTO);

}
