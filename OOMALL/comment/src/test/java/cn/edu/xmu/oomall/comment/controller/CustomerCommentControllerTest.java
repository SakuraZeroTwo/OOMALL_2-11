package cn.edu.xmu.oomall.comment.controller;

import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.service.CommentService;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerCommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

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
