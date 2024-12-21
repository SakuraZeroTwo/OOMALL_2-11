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
import cn.edu.xmu.oomall.comment.controller.dto.*;

import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static cn.edu.xmu.javaee.core.model.Constants.PLATFORM;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CustomerCommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/{id}/comment")
    public ReturnObject appendComment(@PathVariable Long commentId, @LoginUser UserDto user,
                                         @Validated(NewGroup.class) @RequestBody CommentDto dto) {

        Comment comment = CloneFactory.copy(new Comment(), dto);
        Comment newComment = this.commentService.appendComment(id, region, user);
        IdNameTypeVo vo = IdNameTypeVo.builder().id(newRegion.getId()).name(newRegion.getName()).build();
        return new ReturnObject(ReturnNo.CREATED, vo);
    }
}
