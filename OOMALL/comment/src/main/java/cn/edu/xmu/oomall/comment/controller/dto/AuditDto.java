package cn.edu.xmu.oomall.comment.controller.dto;
import io.lettuce.core.StrAlgoArgs;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Data
public class AuditDto {
    private Long id;
    private Long commentId;
    private Long adminId;
    private Byte auditResult;
    private LocalDateTime gmtCreate;

}
