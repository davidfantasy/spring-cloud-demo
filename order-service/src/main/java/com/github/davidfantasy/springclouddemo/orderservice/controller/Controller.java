package com.github.davidfantasy.springclouddemo.orderservice.controller;

import com.github.davidfantasy.springclouddemo.orderservice.dto.InventoryChangeDTO;
import com.github.davidfantasy.springclouddemo.orderservice.dto.OrderDTO;
import com.github.davidfantasy.springclouddemo.orderservice.remote.StorageService;
import com.github.davidfantasy.springclouddemo.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class Controller {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StorageService storageService;


    @PostMapping("/create-order")
    public String createOrder(@RequestBody OrderDTO order) {
        //创建新订单
        orderService.createNewOrder(order);
        InventoryChangeDTO req = new InventoryChangeDTO();
        req.setGoodCode(order.getGoodCode());
        req.setQuantity(order.getQuantity());
        //调用仓储服务变更库存
        Integer remainQuantity = storageService.updateInventoryOfGood(req);
        log.info("剩余数量：" + remainQuantity);
        return "ok";
    }


    @GetMapping("/orders")
    public List<OrderDTO> listOrder(HttpServletRequest req) {
        log.info("X-user-account:{}", req.getHeader("X-user-account"));
        return new ArrayList<OrderDTO>();
    }


}
