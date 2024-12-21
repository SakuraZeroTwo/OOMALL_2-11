package cn.edu.xmu.oomall.comment.service;
import cn.edu.xmu.oomall.comment.controller.dto.AuditDto;
import cn.edu.xmu.oomall.comment.dao.AuditDao;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.bo.Audit;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional
@RequiredArgsConstructor
public class AuditService {
    private final CommentDao commentDao;
    private final AuditDao auditDao ;
    public  void  auditComment(Long commentId, AuditDto auditDto)
    {
        Comment comment = commentDao.findById(commentId);
        Audit newaudit=comment.auditComment(auditDto);
        commentDao.save(comment);
        auditDao.save(newaudit);
    }

}
