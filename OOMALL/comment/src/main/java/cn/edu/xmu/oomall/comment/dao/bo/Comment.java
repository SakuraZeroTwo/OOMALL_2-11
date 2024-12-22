package cn.edu.xmu.oomall.comment.dao.bo;
import cn.edu.xmu.javaee.core.model.bo.OOMallObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static cn.edu.xmu.javaee.core.model.Constants.MAX_RETURN;
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Comment extends OOMallObject implements Serializable {
    private  String content;
    private  Long CustomerId;
    private  Long ProductId;
    private  Long OrderId;
    private  int rating;
    private  Byte status;
    private  Byte appendStatus;
    @Override
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate=gmtCreate;
    }

    @Override
    public void setGmtModified(LocalDateTime gmtModified) {
        this.gmtModified=gmtModified;
    }

    public Comment appendComment(Comment comment)
    {
        this.appendStatus=(byte)1;
        comment.setCustomerId(this.CustomerId);
        comment.setProductId(this.ProductId);
        comment.setOrderId(this.OrderId);
        comment.setStatus((byte)2);
        return comment;
    }
}