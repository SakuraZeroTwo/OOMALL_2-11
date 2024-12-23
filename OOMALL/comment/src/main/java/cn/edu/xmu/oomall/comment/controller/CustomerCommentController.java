package cn.edu.xmu.oomall.comment.controller;
import cn.edu.xmu.javaee.core.aop.Audit;
import cn.edu.xmu.javaee.core.aop.LoginUser;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.javaee.core.model.vo.IdNameTypeVo;
import cn.edu.xmu.javaee.core.model.vo.PageVo;
import cn.edu.xmu.javaee.core.validation.NewGroup;
import cn.edu.xmu.oomall.comment.CommentApplication;
import cn.edu.xmu.oomall.comment.controller.dto.*;

import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CustomerCommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/{id}")
    public ReturnObject getCommentById(@PathVariable Long id) {
        Comment comment = this.commentService.getCommentById(id);
        return new ReturnObject(comment);
    }

    @PostMapping("/{id}/comment")
    public ReturnObject appendComment(@PathVariable Long id, @Validated(NewGroup.class) @RequestBody CommentDto dto) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(dto, comment);
        Comment newComment = this.commentService.appendComment(id, comment);
        CommentVo vo = new CommentVo();
        BeanUtils.copyProperties(newComment, vo);
        return new ReturnObject(ReturnNo.CREATED, vo);
    }

}
