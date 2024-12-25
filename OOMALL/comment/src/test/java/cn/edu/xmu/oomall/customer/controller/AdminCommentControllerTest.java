package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.CommentApplication;
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

@SpringBootTest(classes = CommentApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdminCommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAuditCommentPass() throws Exception {
        Long Id = 1L;
        String body = "{\"auditResult\":\"1\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/adminComment/{commentId}/audit", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));

    }
    @Test
    void testAuditCommentNOTPass() throws Exception {
        Long Id = 1L;
        String body = "{\"auditResult\":\"2\"}";

        this.mockMvc.perform(MockMvcRequestBuilders.put("/adminComment/{commentId}/audit", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(body))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));

    }
    @Test
    void testdeleteCommentByIdSuccess() throws Exception {
        Long Id = 2L;
        this.mockMvc.perform(MockMvcRequestBuilders.put("/adminComment/{commentId}/delete", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.OK.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));
    }
    @Test
    void testdeleteCommentById_CommentNotExist() throws Exception {
        Long Id = 1L;
        this.mockMvc.perform(MockMvcRequestBuilders.put("/adminComment/{commentId}/delete", Id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("评论不存在或已删除")));
    }
    /**
     * 测试根据id获取评论
     */
    @Test
    void testgetCommentByIdSuccess() throws Exception {
        Long id = 1L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/adminComment/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 期望返回 HTTP 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0))) // 期望 errno 为 0 (表示成功)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功"))) // 期望 errmsg 为 "成功"/ 期望 creatorName 为 "1"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtCreate", is("2024-12-21T16:59:01"))) // 期望 gmtCreate 为指定时间
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("泰裤辣"))) // 期望 content 为 "泰裤辣"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.rating", is(0))) // 期望 rating 为 0// 期望 appendStatus 为 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId", is(1))) // 期望 orderId 为 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1553))) // 期望 productId 为 1553
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customerId", is(1))); // 期望 customerId 为 1
    }
    @Test
    void testgetCommentById_IDNotExist() throws Exception {
        Long id = 999L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/adminComment/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound()) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("评论不存在")));
    }
}
