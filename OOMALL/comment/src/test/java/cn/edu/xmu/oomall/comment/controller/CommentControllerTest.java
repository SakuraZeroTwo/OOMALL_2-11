package cn.edu.xmu.oomall.comment.controller;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.service.CommentService;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    @Mock
    private CommentDao commentDao;

    private CommentService commentService; // 不使用 @Spy 注解

    @BeforeEach
    void setup() {
        // 手动创建 CommentService 实例并用 spy 包装
        commentService = Mockito.spy(new CommentService(commentDao));
    }

    @InjectMocks
    private CustomerCommentController customerCommentController;

    private static final String COMMENT_URL = "/comment/{id}/comment";

    @Test
    void testAppendCommentWithSpy() throws Exception {
        // 模拟原始评论对象
        Comment originalComment = new Comment();
        originalComment.setId(123L);
        originalComment.setContent("原始评论内容");
        originalComment.setCustomerId(1L);
        originalComment.setProductId(2L);
        originalComment.setOrderId(3L);
        originalComment.setRating(4);
        originalComment.setStatus(Comment.TOBEAUDIT);

        // 模拟追加评论对象
        Comment appendComment = new Comment();
        appendComment.setContent("这是追加的评论");
        appendComment.setRating(5);

        // 新的追加评论
        Comment newComment = new Comment();
        newComment.setContent("这是追加的评论");
        newComment.setRating(5);
        newComment.setStatus(Comment.TOBEAUDIT);
        newComment.setAppendStatus((byte) 0);
        newComment.setCustomerId(1L);
        newComment.setProductId(2L);
        newComment.setOrderId(3L);

        // 使用 Spy 部分模拟 CommentService 的 appendComment 方法
        doReturn(newComment).when(commentService).appendComment(eq(123L), any(Comment.class));
        doReturn(originalComment).when(commentDao).findById(eq(123L));

        // 执行 POST 请求并进行验证
        mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment",123L)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{ \"content\": \"这是追加的评论\", \"rating\": 5 }"))
//                .andExpect(MockMvcResultMatchers.status().isCreated())  // 期望返回 201 状态
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))  // 验证 errno
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errmsg", is(ReturnNo.CREATED.getMessage())))  // 验证 errmsg
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content", is("这是追加的评论")))  // 验证追加评论的内容
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.rating", is(4)))  // 验证追加评论的评分
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.status", is(0)))  // 验证追加评论的状态
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.customerId", is(1)))  // 验证 customerId
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.productId", is(2)))   // 验证 productId
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId", is(3)));   // 验证 orderId
    }
}
