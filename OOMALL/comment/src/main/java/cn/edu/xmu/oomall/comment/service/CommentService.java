package cn.edu.xmu.oomall.comment.service;

import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    //查询所有评论
    public List<CommentVo> retrieveCommentList()
    {
        List <CommentVo> commentListVo = commentDao.findCommentList();
        return commentListVo;
    }
}
