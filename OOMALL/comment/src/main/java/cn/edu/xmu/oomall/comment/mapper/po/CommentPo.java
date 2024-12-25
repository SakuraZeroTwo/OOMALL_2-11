package cn.edu.xmu.oomall.comment.mapper.po;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "comment_comment")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long customerId;
    private Long productId;
    private Long orderId;
    private int rating;
    private Byte status;
    private Byte appendStatus;
    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

}
