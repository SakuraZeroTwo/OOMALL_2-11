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
     * 测试获取评论列表
     */
    @Test
    void testretrieveCommentList() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/retrieveCommentList")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Expect HTTP 200 OK
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()", is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].productId", is(1550)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].productId", is(1551)));
    }
    /**
     * 测试根据id获取评论
     */
    @Test
    void testgetCommentById() throws Exception {
        Long id = 1L;
        this.mockMvc.perform(MockMvcRequestBuilders.get("/comment/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk()) // 期望返回 HTTP 200
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(0))) // 期望 errno 为 0 (表示成功)
                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is("成功"))) // 期望 errmsg 为 "成功"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(1))) // 期望评论的 id 为 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.creatorId", is(1))) // 期望 creatorId 为 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.creatorName", is("1"))) // 期望 creatorName 为 "1"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.gmtCreate", is("2024-12-21T16:59:01"))) // 期望 gmtCreate 为指定时间
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("泰裤辣"))) // 期望 content 为 "泰裤辣"
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.rating", is(0))) // 期望 rating 为 0
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(2))) // 期望 status 为 2
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.appendStatus", is(1))) // 期望 appendStatus 为 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId", is(1))) // 期望 orderId 为 1
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1550))) // 期望 productId 为 1550
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customerId", is(1))); // 期望 customerId 为 1
    }

    @Test
    void testAppendComment() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"content\": \"这是追加的评论\", \"rating\": 5 }"))
//                .andExpect(MockMvcResultMatchers.status().isCreated())  // 验证返回状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("这是追加的评论")))  // 验证返回内容
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.rating", is(5)))  // 验证返回评分
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(0)))  // 验证返回状态
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customerId", is(1)))  // 验证 customerId
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(1550)))  // 验证 productId
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId", is(1)));  // 验证 orderId
    }

    @Test
    void testAppendCommentWithInvalidDto() throws Exception {
        // 创建一个非法的 CommentDto 对象，content 为空，应该触发验证失败
        CommentDto invalidDto = new CommentDto();
        invalidDto.setContent("");  // 违反 @NotBlank 约束
        invalidDto.setRating(5);

        // 执行 POST 请求
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"content\": \"\", \"rating\": 5 }"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // 验证返回状态码是 400
    }
    @Test
    void testAppendCommentWhenOriginCommentNotFound() throws Exception {
        // 创建一个合法的 CommentDto 对象
        CommentDto validDto = new CommentDto();
        validDto.setContent("这是追加的评论");
        validDto.setRating(5);

        // 执行 POST 请求
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", 1L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"content\": \"这是追加的评论\", \"rating\": 5 }"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());  // 验证返回的错误消息

    }

}
