package cn.edu.xmu.oomall.comment.controller;
import cn.edu.xmu.javaee.core.model.ReturnObject;
import cn.edu.xmu.oomall.comment.controller.dto.*;

import cn.edu.xmu.oomall.comment.service.AuditService;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminComment")
@RequiredArgsConstructor
public class AdminCommentController {
    private final CommentService commentService;
    private final AuditService auditService;
    @PutMapping("{commentId}/delete")
    public ReturnObject deleteCommentById(@PathVariable Long commentId)
    {
        this.commentService.deleteCommentById(commentId);
        return new ReturnObject();
    }
    @PutMapping("{commentId}/audit")
    public  ReturnObject auditComment(@PathVariable Long commentId,@RequestBody AuditDto auditdto)
    {
        auditService.auditComment(commentId,auditdto);
        return  new ReturnObject();
    }
}
