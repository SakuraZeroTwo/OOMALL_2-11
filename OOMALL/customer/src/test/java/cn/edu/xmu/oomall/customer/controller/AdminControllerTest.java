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
    void getUserById() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}",123)
            .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("success")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customer.id").value(123));
    }
    @Test
    void getUserByNULLID() throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/{id}",0)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.INTERNAL_SERVER_ERR.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("User not Found!")));

    }

}
