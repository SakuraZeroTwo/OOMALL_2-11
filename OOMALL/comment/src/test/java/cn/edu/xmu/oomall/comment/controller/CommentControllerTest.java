package cn.edu.xmu.oomall.comment.controller;

import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.CommentApplication;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.service.CommentService;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
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

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(classes = CommentApplication.class)
@AutoConfigureMockMvc
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    private static String userToken;
    private static Long validCommentId = 1L;
    private static Long invalidCommentId = 999L;

    @BeforeAll
    static void setUp() {
        // 模拟用户的 JWT Token
        userToken = "Bearer someValidToken";
    }

    @Test
    void appendCommentSuccessfully() throws Exception {
        // 模拟成功添加评论
        CommentDto dto = new CommentDto();
        dto.setContent("This is a valid comment");
        dto.setRating(5);

        // 创建一个业务层的 Comment 对象
        Comment newComment = new Comment();
        newComment.setId(100L);  // 设置新评论的 ID

        // 模拟 service 层的 appendComment 方法
        Mockito.when(commentService.appendComment(Mockito.anyLong(), Mockito.any(), Mockito.any()))
                .thenReturn(newComment);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", validCommentId)
                        .header("authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"This is a valid comment\", \"rating\": 5}")
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())  // 201 状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.CREATED.getErrNo())))  // 确保 errno 正确
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id", is(100)));  // 返回新评论的 ID
    }

    @Test
    void appendCommentWithoutLogin() throws Exception {
        // 用户未登录
        CommentDto dto = new CommentDto();
        dto.setContent("This is a valid comment");
        dto.setRating(5);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", validCommentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"This is a valid comment\", \"rating\": 5}")
                )
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())  // 401 状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_NEED_LOGIN.getErrNo())));  // 确保 errno 为 AUTH_NEED_LOGIN
    }

    @Test
    void appendCommentForNonExistentCommentId() throws Exception {
        // 评论 ID 不存在
        CommentDto dto = new CommentDto();
        dto.setContent("This is a valid comment");
        dto.setRating(5);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", invalidCommentId)
                        .header("authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"This is a valid comment\", \"rating\": 5}")
                )
                .andExpect(MockMvcResultMatchers.status().isNotFound())  // 404 状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.RESOURCE_ID_NOTEXIST.getErrNo())));  // 确保 errno 为 RESOURCE_ID_NOTEXIST
    }

    @Test
    void appendCommentWithInvalidContent() throws Exception {
        // 评论内容无效，例如缺少字段
        CommentDto dto = new CommentDto();
        dto.setRating(5);  // 缺少 content 字段

        this.mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", validCommentId)
                        .header("authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rating\": 5}")  // 传入的 JSON 缺少 content 字段
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // 400 状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.FIELD_NOTVALID.getErrNo())));  // 确保 errno 为 FIELD_NOTVALID
    }

    @Test
    void appendCommentWithInvalidRating() throws Exception {
        // 评论内容无效，评分不合法
        CommentDto dto = new CommentDto();
        dto.setContent("This is a valid comment");
        dto.setRating(6); // 超过最大评分限制

        this.mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", validCommentId)
                        .header("authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"This is a valid comment\", \"rating\": 6}")  // 传入无效的评分
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())  // 400 状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.FIELD_NOTVALID.getErrNo())));  // 确保 errno 为 FIELD_NOTVALID
    }

    @Test
    void appendCommentWithForbiddenUser() throws Exception {
        // 模拟权限不足的用户
        CommentDto dto = new CommentDto();
        dto.setContent("This is a valid comment");
        dto.setRating(5);

        Mockito.when(commentService.appendComment(Mockito.anyLong(), Mockito.any(), Mockito.any()))
                .thenThrow(new RuntimeException("Permission Denied"));

        this.mockMvc.perform(MockMvcRequestBuilders.post("/comment/{id}/comment", validCommentId)
                        .header("authorization", userToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"This is a valid comment\", \"rating\": 5}")
                )
                .andExpect(MockMvcResultMatchers.status().isForbidden())  // 403 状态码
                .andExpect(MockMvcResultMatchers.jsonPath("$.errno", is(ReturnNo.AUTH_NO_RIGHT.getErrNo())));  // 确保 errno 为 AUTH_NO_RIGHT
    }
}
