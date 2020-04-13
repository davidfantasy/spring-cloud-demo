package com.github.davidfantasy.springclouddemo.orderservice;

import com.github.davidfantasy.springclouddemo.orderservice.dto.OrderDTO;
import com.github.davidfantasy.springclouddemo.orderservice.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = OrderServiceApplication.class)
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCreateOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerCode("cus001");
        orderDTO.setGoodCode("tc-1");
        orderDTO.setQuantity(100);
        this.mvc.perform(post("/api/order/create-order").content(JsonUtil.obj2json(orderDTO)).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));
    }

}
