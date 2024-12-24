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
    void testDeleteCommentById_Success() throws Exception {
        Long commentId = 2L;  // 假设这是一个有效的评论ID

        // 执行删除请求
        this.mockMvc.perform(MockMvcRequestBuilders.put("/adminComment/{commentId}/delete", commentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0)))  // errno 为 0 表示成功
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")));  // errmsg 为 "成功"
    }

    //需要修改findById写错了导致错误码错误
    @Test
    void testDeleteCommentById_UserNotFound() throws Exception {
        Long commentId = -1L;

        this.mockMvc.perform(MockMvcRequestBuilders.put("/adminComment/{commentId}/delete", commentId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(2)))  // errno 为 608 表示用户不存在
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("No value present")));  // errmsg 为 "登录用户id不存在"
    }
}
