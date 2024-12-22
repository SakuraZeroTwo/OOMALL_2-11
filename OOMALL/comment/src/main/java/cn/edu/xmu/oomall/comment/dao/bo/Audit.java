package cn.edu.xmu.oomall.comment.dao.bo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.edu.xmu.javaee.core.model.Constants.MAX_RETURN;
@Slf4j
@Data
public class Audit extends OOMallObject implements Serializable{
    private Long commentId;
    private Long adminId;
    private Byte auditResult;
    


    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate=gmtCreate;
    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified=gmtModified;
    }
}
