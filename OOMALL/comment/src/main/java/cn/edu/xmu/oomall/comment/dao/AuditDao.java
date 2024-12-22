package cn.edu.xmu.oomall.comment.dao;
import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.dao.bo.Audit;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.mapper.AuditPoMapper;
import cn.edu.xmu.oomall.comment.mapper.CommentPoMapper;
import cn.edu.xmu.oomall.comment.mapper.po.AuditPo;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@Slf4j
@RequiredArgsConstructor
public class AuditDao {
    private final AuditPoMapper auditPoMapper;

    public Audit findById(Long id) {
        Optional<AuditPo> auditPo = auditPoMapper.findById(id);
        if (!auditPo.isPresent()) {
            throw new BusinessException(ReturnNo.CUSTOMERID_NOTEXIST);
        } else {
            Audit bo = new Audit();
            BeanUtils.copyProperties(auditPo.get(), bo);
            return bo;
        }
    }

    public Audit save(Audit audit) {
        AuditPo auditPo = new AuditPo();
        BeanUtils.copyProperties(audit, auditPo);
        auditPoMapper.save(auditPo);

        Audit bo = new Audit();
        BeanUtils.copyProperties(auditPo, bo);
        return bo;
    }
}
