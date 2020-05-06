package com.github.davidfantasy.springclouddemo.orderservice.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.davidfantasy.springclouddemo.orderservice.dao.OrderMapper;
import com.github.davidfantasy.springclouddemo.orderservice.dto.InventoryChangeDTO;
import com.github.davidfantasy.springclouddemo.orderservice.dto.OrderDTO;
import com.github.davidfantasy.springclouddemo.orderservice.model.Order;
import com.github.davidfantasy.springclouddemo.orderservice.remote.StorageService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    @Autowired
    private StorageService storageService;

    @GlobalTransactional
    public Integer createNewOrder(OrderDTO orderDTO) {
        log.info("[createNewOrder] 当前 XID: {}", RootContext.getXID());
        Order newOrder = new Order();
        newOrder.setCustomerCode(orderDTO.getCustomerCode());
        newOrder.setGoodCode(orderDTO.getGoodCode());
        newOrder.setGoodQuantity(orderDTO.getQuantity());
        //向本地数据库插入订单信息
        this.save(newOrder);
        InventoryChangeDTO req = new InventoryChangeDTO();
        req.setGoodCode(orderDTO.getGoodCode());
        req.setQuantity(orderDTO.getQuantity());
        //调用远程仓储服务变更库存
        Integer remainQuantity = storageService.updateInventoryOfGood(req);
        return remainQuantity;
    }

}
