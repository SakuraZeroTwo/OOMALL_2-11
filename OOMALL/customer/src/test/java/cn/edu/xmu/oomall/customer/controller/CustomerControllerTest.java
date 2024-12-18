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

    @Test
    void getCouponList() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(CUSTOMER_HAS_COUPONS,1001)
                    .param("page","1")
                    .param("pageSize","100")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].couponName", is("店铺1-优惠活动3-2件9折"))) // 验证第一个元素的 couponName 是否为 "店铺1-优惠活动3-2件9折"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].couponName", is("店铺1-优惠活动3-2件9折"))); // 验证第二个元素的 couponName 是否为 "店铺1-优惠活动3-2件9折"

    }
}
