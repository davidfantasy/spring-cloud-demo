package com.github.davidfantasy.springclouddemo.storageservice.service;

import com.github.davidfantasy.springclouddemo.storageservice.dto.InventoryChangeDTO;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

    public Integer changeInventory(InventoryChangeDTO req) {
        return 98;
    }

}
