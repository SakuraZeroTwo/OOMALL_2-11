package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.customer.CustomerApplication;
import cn.edu.xmu.oomall.customer.dao.bo.Customer;
import cn.edu.xmu.oomall.customer.service.CustomerService;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = CustomerApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private CustomerService customerService;
    @Test
    void testGetUserById() throws Exception {
        Long Id = 123L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}",Id)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(Id));
    }
    @Test
    void testGetUserByNULLID() throws Exception{
        Long Id = 0L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}",Id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("登录用户id不存在")));
    }
    @Test
    void testUpdateUserInvalid() throws Exception {
        Long Id = 123L;
        // 测试封禁用户
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/{action}", Id, "ban")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));

        // 测试解封用户
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/{action}", Id, "release")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }
    @Test
    void testBanOrReleaseDeletedUser() throws Exception{
        Long Id = 12L;
        // 测试封禁或解封已被删除的用户
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/{action}", Id, "release")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.STATENOTALLOW.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("顾客对象（id=12）已删除状态禁止此操作")));
    }
    @Test
    void testDeleteUser() throws Exception {
        Long Id = 123L;

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/delete", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));

    }
    @Test
    void testretriveAllUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/getAllCustomers")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())  // 验证返回状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", is(24285)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].userName", is("699275")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].userName", is("105048")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[6].userName", is("696909")));
    }
}
