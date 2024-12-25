package cn.edu.xmu.oomall.comment.controller.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
public class CommentDto {
    private Long customerId;
    private String customerName;
    private String content;
    private int rating;
    private LocalDateTime gmtCreate;

}
