package cn.edu.xmu.oomall.comment.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentDao commentDao;
    public void deleteCommentById(Long commentId) {
        Comment comment = commentDao.findById(commentId);
        comment.setStatus(Comment.DELETED);
        this.commentDao.save(comment);
    }

    public Comment getCommentById(Long commentId) {
        return this.commentDao.findById(commentId);
    }


    public Comment appendComment(Long commentId, Comment appendComment) {
        Comment comment = commentDao.findById(commentId);
        if(comment==null) {
            log.error("Comment not found for commentId: {}", commentId);
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), comment.getId()));
        }
        else{
            Comment newComment = comment.appendComment(appendComment);
            newComment = commentDao.insert(newComment);
            return newComment;
        }
    }
}
