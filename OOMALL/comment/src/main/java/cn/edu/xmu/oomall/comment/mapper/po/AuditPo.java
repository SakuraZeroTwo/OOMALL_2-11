package cn.edu.xmu.oomall.comment.mapper.po;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment_audit")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuditPo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Long commentId;
    public Long adminId;
    public Byte auditResult;
    public Long creatorId;
    public String creatorName;
    public Long modifierId;
    public String modifierName;
    public LocalDateTime gmtCreate;
    public LocalDateTime gmtModified;
}
