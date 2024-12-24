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
    public Long id;
    public String content;
    public Long customerId;
    public Long productId;
    public Long orderId;
    public int rating;
    public Byte status;
    public Byte appendStatus;
    public Long creatorId;
    public String creatorName;
    public Long modifierId;
    public String modifierName;
    public LocalDateTime gmtCreate;
    public LocalDateTime gmtModifier;

}
