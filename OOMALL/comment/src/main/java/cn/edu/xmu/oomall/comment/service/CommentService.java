package cn.edu.xmu.oomall.comment.service;

import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
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


    public Comment appendComment(Long commentId,Comment appendComment,UserDto user) {

        Comment comment = commentDao.findById(commentId);
        Comment newComment = comment.appendComment(appendComment);
        commentDao.insert(newComment,user);
        return newComment;
    }
}
