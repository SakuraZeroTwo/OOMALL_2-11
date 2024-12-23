package cn.edu.xmu.oomall.comment.controller;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.comment.CommentApplication;
import cn.edu.xmu.oomall.comment.controller.dto.*;

import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CustomerCommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 根据id获取评论
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ReturnObject getCommentById(@PathVariable Long id) {
        Comment comment = this.commentService.getCommentById(id);
        return new ReturnObject(comment);
    }

    /**
     * 获取所有评论
     * @return
     *
     */
    @GetMapping("/retrieveCommentList")
    public  ReturnObject retrieveCommentList()
    {
        List<CommentVo> comments =  commentService.retrieveCommentList();
        return new ReturnObject(comments);
    }
}
