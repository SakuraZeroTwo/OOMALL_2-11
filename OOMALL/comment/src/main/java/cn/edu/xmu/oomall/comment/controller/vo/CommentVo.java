package cn.edu.xmu.oomall.comment.controller.vo;

import cn.edu.xmu.oomall.comment.dao.bo.Audit;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentVo {
    private  String content;
    private  Long customerId;
    private  Long productId;
    private  Long orderId;
    private LocalDateTime gmtCreate;
    private  int rating;
    private  Byte appendStatus;
}
