package cn.edu.xmu.oomall.customer.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.CommentApplication;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
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

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = CommentApplication.class)
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CustomerCommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    /**
     * 测试根据商品id获取评论列表
     */
    @Test
    void testRetrieveCommentListSuccess() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/retrieveCommentList/1551")
                        .param("page", "1")  // 添加分页参数 page
                        .param("pageSize", "10")  // 添加分页参数 pageSize
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect HTTP 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0))) // Expect errno == 0
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功"))) // Expect errmsg == "成功"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content.length()", is(1))) // Expect content length == 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].productId", is(1551))) // Expect productId == 1551
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].content", is("还可以"))) // Expect content == "还可以"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalPages", is(1))) // Expect totalPages == 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.totalElements", is(1))) // Expect totalElements == 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.size", is(10))) // Expect size == 10
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.number", is(0))) // Expect page number == 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.first", is(true))) // Expect first == true (this is the first page)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.last", is(true))) // Expect last == true (this is the last page)
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.empty", is(false))); // Expect empty == false
    }
    @Test
    void testRetrieveCommentList_CommentNotExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/retrieveCommentList/1570")
                        .param("page", "1")  // 添加分页参数 page
                        .param("pageSize", "10")  // 添加分页参数 pageSize
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0))) // Expect errno == 0
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("商品暂无评论")));// Expect HTTP 200 OK
    }
    /**
     * 测试根据id获取评论
     */
    @Test
    void testgetCommentByIdSuccess() throws Exception {
        Long id = 1L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/{id}",id)
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
        this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound()) //
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("评论不存在")));
    }

    @Test
    void testAppendComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"content\": \"这是追加的评论\", \"rating\": 5 }"))
                .andExpect(MockMvcResultMatchers.status().isCreated())  // 验证返回状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("这是追加的评论")))  // 验证返回内容
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.rating", is(5)))  // 验证返回评分
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(0)))  // 验证返回状态
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customerId", is(1)))  // 验证 customerId
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1553)))  // 验证 productId
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId", is(1)));  // 验证 orderId
    }

    @Test
    void testAppendCommentWhenOriginCommentNotFound() throws Exception {
        // 执行 POST 请求
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", 5L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"content\": \"这是追加的评论\", \"rating\": 5 }"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());  // 验证返回的错误消息

    }

}
