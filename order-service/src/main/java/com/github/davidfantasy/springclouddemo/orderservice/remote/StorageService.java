package com.github.davidfantasy.springclouddemo.orderservice.remote;

import com.github.davidfantasy.springclouddemo.orderservice.dto.InventoryChangeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(name = "storage-service", fallback = StorageServiceFallback.class)
//测试全局事务回滚时需要注释掉fallback，否则接口会返回默认的值导致事务无法回滚
@FeignClient(name = "storage-service")
public interface StorageService {

    @PostMapping("/api/storage/change-inventory")
    Integer updateInventoryOfGood(InventoryChangeDTO inventoryChangeDTO);

}
