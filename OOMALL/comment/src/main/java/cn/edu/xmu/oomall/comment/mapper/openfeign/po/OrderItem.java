package cn.edu.xmu.oomall.comment.mapper.openfeign.po;

import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.service.CommentService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderItem {
    private Long id;
    private Long onsaleId;
    private Long orderId;


    public Comment createComment(CommentDto commentDto,Long productId) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);
        comment.setOrderId(this.orderId);
        comment.setProductId(productId);
        comment.setStatus(Comment.TOBEAUDIT);
        comment.setGmtCreate(LocalDateTime.now());
        return comment;
    }
}
