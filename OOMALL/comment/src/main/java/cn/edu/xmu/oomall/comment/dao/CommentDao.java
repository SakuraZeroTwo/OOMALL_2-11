package cn.edu.xmu.oomall.comment.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.mapper.CommentPoMapper;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
@Slf4j

public class CommentDao {
    private final static Logger logger = LoggerFactory.getLogger(CommentDao.class);
    private final CommentPoMapper commentPoMapper;

    public Comment findById(Long id) {
        Optional<CommentPo> commentPo = commentPoMapper.findById(id);
        if(!commentPo.isPresent()){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST,"评论不存在");
        }
        else {
            Comment bo = new Comment();
            BeanUtils.copyProperties(commentPo.get(), bo);
            return bo;
        }
//        try {
//            Optional<CommentPo> po = commentPoMapper.findById(id);
//            if (po.isPresent()) {
//                Comment bo = new Comment();
//                BeanUtils.copyProperties(po.get(), bo);
//                return bo;
//            } else {
//                log.warn("No CommentPo found for id: {}", id);
//                return null;  // 没有找到记录时返回 null
//            }
//        } catch (Exception e) {
//            log.error("Error occurred while fetching Comment by id: {}", id, e);
//            throw new RuntimeException("Failed to fetch Comment by id", e);  // 可以根据需求抛出不同的异常
//        }
    }

    public Comment save(Comment comment) {
        CommentPo customerPo = new CommentPo();
        BeanUtils.copyProperties(comment, customerPo);
        commentPoMapper.save(customerPo);

        Comment bo = new Comment();
        BeanUtils.copyProperties(customerPo, bo);
        return bo;
    }

    //根据productId查询评论
    public Page <CommentVo> findCommentList(Long productId, int page, int pageSize)
    {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        int status = 1;
        Page<CommentPo> commentPoPage = commentPoMapper.findByProductIdAndStatus(productId, status,pageable);
        if (commentPoPage == null || commentPoPage.isEmpty()) {
            throw new BusinessException(ReturnNo.OK,"商品暂无评论");
        }
        Page<CommentVo> commentVoPage = commentPoPage.map(po -> {
            CommentVo vo = new CommentVo();
            BeanUtils.copyProperties(po, vo);
            return vo;
        });
        return commentVoPage;
//        return commentPoPage.stream().map(po -> {
//            CommentVo vo = new CommentVo();
//            BeanUtils.copyProperties(po, vo);
//            return vo;
//        }).collect(Collectors.toList());
    }
}
