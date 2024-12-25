package cn.edu.xmu.oomall.order.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.order.OrdersApplication;
import cn.edu.xmu.oomall.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = OrdersApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private OrderService orderService;
    @Test
    void testgetShopOrderByIdSuccess() throws Exception{
        Long shopId = 1L;
        Long orderId = 1L;
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOjEsImRlcGFydElkIjowLCJ0b2tlbklkIjoiMjAyNDEyMjUxOTU1NTgzRVdBIiwidXNlck5hbWUiOiIxMzA4OGFkbWluIiwidXNlckxldmVsIjoxLCJpc3MiOiJPT0FEIiwic3ViIjoidGhpcyBpcyBhIHRva2VuIiwiYXVkIjoiTUlOSUFQUCIsImlhdCI6MTczNTEyNzc1OCwiZXhwIjoxNzM1MTMxMzU4fQ.fUnFWiicK07l7lCgvFA-rVX3e5b0bJ-k3YVYw9bddvs";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/shops/{shopId}/orders/{orderId}",shopId,orderId)
                        .header("authorization", token)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(orderId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderSn").value("2016102361242"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.consignee").value("赵永波"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value("人民北路"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.mobile").value("13959235540"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.message").value("等待所有商品备齐后再发"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.expressFee").value(545))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.discountPrice").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.originPrice").value(799121))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status").value(300))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.creatorName").value("赵永波"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtCreate").value("2016-10-23T12:07:00"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtModified").doesNotExist());
    }
    @Test
    void testgetShopOrderById_OrderNotFound() throws Exception{
        Long shopId = 1L;
        Long orderId = 10099L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/orders/shops/{shopId}/orders/{orderId}",shopId,orderId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("订单不存在或不属于该商户")));
    }
}
