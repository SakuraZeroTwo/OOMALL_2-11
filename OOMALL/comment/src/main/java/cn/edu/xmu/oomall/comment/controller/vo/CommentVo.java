package cn.edu.xmu.oomall.comment.controller.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVo {
    private  Long CustomerId;
    private  Long ProductId;
    private  Long OrderId;
    private  String content;
    private  int rating;
    private  Byte status;
}
