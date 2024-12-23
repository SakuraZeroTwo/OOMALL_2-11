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


    public Comment appendComment(Long id, Comment comment) {
        Comment originComment = commentDao.findById(id);
        if(originComment==null) {
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        else{
            Comment newComment = originComment.appendComment(comment);
            commentDao.insert(newComment);
            return newComment;
        }
    }
}
