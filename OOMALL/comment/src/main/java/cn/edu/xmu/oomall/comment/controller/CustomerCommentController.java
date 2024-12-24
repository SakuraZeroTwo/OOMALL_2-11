package cn.edu.xmu.oomall.comment.controller;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.oomall.comment.CommentApplication;
import cn.edu.xmu.oomall.comment.controller.dto.*;

import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.mapper.openfeign.ProductMapper;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import cn.edu.xmu.javaee.core.model.ReturnNo;

import java.util.List;
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CustomerCommentController {
    @Autowired
    private CommentService commentService;

    private final ProductMapper productMapper;
    /**
     * 根据评论id获取评论
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ReturnObject getCommentById(@PathVariable Long id) {
        CommentVo commentVo = this.commentService.getCommentById(id);
        return new ReturnObject(commentVo);
    }

    /**
     * 获取所有评论
     * @return
     *
     */
    @GetMapping("/retrieveCommentList/{productId}")
    public  ReturnObject retrieveCommentList(@PathVariable Long productId,
                                             @RequestParam int page,
                                             @RequestParam int pageSize)
    {
        Page<CommentVo> comments =  commentService.retrieveCommentList(productId, page, pageSize);
        return new ReturnObject(comments);
    }

    //一个示例的跨模块调用方法，调用我们docker集群中product-service的findProductById方法
//    @GetMapping("/hello")
//    public InternalReturnObject hello(Long id) {
//        return commentService.getProductId(id);
//    }

    @PostMapping("/{id}/comment")
    public ReturnObject appendComment(@PathVariable Long id, @Validated @RequestBody CommentDto dto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(dto, comment);
        Comment newComment = this.commentService.appendComment(id, comment);
        CommentVo vo = new CommentVo();
        BeanUtils.copyProperties(newComment, vo);
        return new ReturnObject(ReturnNo.CREATED, comment);
    }

}
