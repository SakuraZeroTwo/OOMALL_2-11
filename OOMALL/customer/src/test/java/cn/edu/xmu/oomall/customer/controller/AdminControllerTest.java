package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.customer.CustomerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = CustomerApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void testGetUserById() throws Exception {
        Long Id = 123L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}",Id)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customer.id").value(Id));
    }
    @Test
    void testGetUserByNULLID() throws Exception{
        Long Id = 0L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}",Id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.INTERNAL_SERVER_ERR.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("User not Found!")));
    }
    @Test
    void testUpdateUserInvalid() throws Exception {
        Long Id = 123L;
        // 测试封禁用户
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/{action}", Id, "ban")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Customer " + Id + " has been banned."));

        // 测试解封用户
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/{action}", Id, "release")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Customer " + Id + " has been released."));
    }
    @Test
    void testBanOrReleaseDeletedUser() throws Exception{
        Long Id = 12L;
        // 测试封禁或解封已被删除的用户
        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/{action}", Id, "release")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("用户状态错误"));
    }
    @Test
    void testDeleteUser() throws Exception {
        Long Id = 123L;

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customers/{id}/delete", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Customer " + Id + " has been deleted."));
    }


}
