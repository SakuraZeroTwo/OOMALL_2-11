package cn.edu.xmu.oomall.comment.dao.bo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import cn.edu.xmu.oomall.comment.controller.dto.AuditDto;
import cn.edu.xmu.oomall.comment.dao.AuditDao;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.edu.xmu.javaee.core.model.Constants.MAX_RETURN;
@Slf4j
@Data

public class Comment extends OOMallObject implements Serializable {
    private  String content;
    private  Long CustomerId;
    private  Long ProductId;
    private  Long OrderId;
    private  int rating;
    private  Byte status;
    private  Byte appendStatus;
    private  Audit audit;
    public static final Byte TOBEAUDIT = 0;//待审核
    public static final Byte VALID = 1;//有效
    public static final Byte DELETED = 2;//隐藏

    public Comment() {
    }

    public Audit auditComment(AuditDto auditDto)
    {

        Audit newAudit = new Audit();
        newAudit.setCommentId(this.id);
        newAudit.setAuditResult(auditDto.getAuditResult());
        this.setStatus(auditDto.getAuditResult());
        newAudit.setGmtCreate(LocalDateTime.now());
        this.audit=newAudit;
        return newAudit;
    }

    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate=gmtCreate;
    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified=gmtModified;
    }
}