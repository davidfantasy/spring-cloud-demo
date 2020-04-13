package com.github.davidfantasy.springclouddemo.storageservice.controller;

import com.github.davidfantasy.springclouddemo.storageservice.dto.InventoryChangeDTO;
import com.github.davidfantasy.springclouddemo.storageservice.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/storage")
public class Controller {


    @Autowired
    private StorageService storageService;

    @PostMapping("/change-inventory")
    public Integer changeInventory(@RequestBody InventoryChangeDTO req) {
        return storageService.changeInventory(req);
    }

}
