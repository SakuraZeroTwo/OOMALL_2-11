package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.util.JwtHelper;
import cn.edu.xmu.oomall.customer.CustomerApplication;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = CustomerApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final String CUSTOMER_HAS_COUPONS = "/customers/{id}/coupon";

//    @Test
//    void getCouponList() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_HAS_COUPONS,1001)
//                    .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("success")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", is(2)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].couponName", is("店铺1-优惠活动3-2件9折"))) // 验证第一个元素的 couponName 是否为 "店铺1-优惠活动3-2件9折"
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].couponName", is("店铺1-优惠活动3-2件9折"))); // 验证第二个元素的 couponName 是否为 "店铺1-优惠活动3-2件9折"
//
//    }
//    @Test
//    void testgetCartListSuccess() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/cart", 706)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.items.length()", is(3)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.items[0].productId", is(4163)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.items[1].productId", is(1901)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.items[2].productId", is(3148)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPrice", is(157156)));
//    }
//    @Test
//    void testgetCartList_UserNotFound() throws Exception {
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}/cart", 9999) // 假设 9999 是不存在的用户 ID
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(MockMvcResultMatchers.status().isInternalServerError()) // 状态码 500
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(2))) // 验证错误码
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("User not found!"))); // 验证错误消息
//    }
    @Test
    void testUpdateCustomerMessage() throws Exception {
        Long Id = 123L;
        String body = "{\"name\":\"新名字\", \"mobile\":\"1223455\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }
    @Test
    void testUpdateCustomerPassword() throws Exception {
        Long Id = 123L;
        String body = "{\"password\":\"32145\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/password", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }
}
