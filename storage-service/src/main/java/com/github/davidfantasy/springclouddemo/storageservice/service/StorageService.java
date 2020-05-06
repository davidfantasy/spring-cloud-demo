package com.github.davidfantasy.springclouddemo.storageservice.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.davidfantasy.springclouddemo.storageservice.dao.InventoryMapper;
import com.github.davidfantasy.springclouddemo.storageservice.dto.InventoryChangeDTO;
import com.github.davidfantasy.springclouddemo.storageservice.model.Inventory;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class StorageService extends ServiceImpl<InventoryMapper, Inventory> {

    public Integer changeInventory(InventoryChangeDTO req) {
        log.info("[changeInventory] 当前 XID: {}", RootContext.getXID());
        Inventory inventory = this.getOne(Wrappers.<Inventory>lambdaQuery().eq(Inventory::getGoodCode, req.getGoodCode()));
        if (inventory == null) {
            inventory = new Inventory();
            inventory.setGoodQuantity(0);
            inventory.setGoodCode(req.getGoodCode());
        }
        inventory.setGoodQuantity(inventory.getGoodQuantity() - req.getQuantity());
        this.saveOrUpdate(inventory);
        return inventory.getGoodQuantity();
    }

}
