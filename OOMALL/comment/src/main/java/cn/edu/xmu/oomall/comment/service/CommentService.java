package cn.edu.xmu.oomall.comment.service;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.oomall.comment.controller.dto.CommentDto;
import cn.edu.xmu.oomall.comment.controller.vo.CommentVo;
import cn.edu.xmu.oomall.comment.dao.CommentDao;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.oomall.comment.dao.bo.Product;
import cn.edu.xmu.oomall.comment.dao.openfeign.OrderItemDao;
import cn.edu.xmu.oomall.comment.dao.openfeign.ProductDao;
import cn.edu.xmu.oomall.comment.mapper.openfeign.OrderItemMapper;
import cn.edu.xmu.oomall.comment.mapper.openfeign.ProductMapper;
import cn.edu.xmu.oomall.comment.mapper.openfeign.po.OrderItem;
import cn.edu.xmu.oomall.comment.mapper.openfeign.po.ProductPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentDao commentDao;
    private final ProductMapper productMapper;
    private final OrderItemDao orderItemDao;
    private final ProductDao productDao;
    @Autowired
    private Product product;

    /**
     * 用户创建评论
     */
    public CommentVo createComment(Long orderItemId, CommentDto commentDto) throws BusinessException {
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto, comment);

        OrderItem orderItem = orderItemDao.findById(orderItemId);
        ProductPo productPo = productDao.findById(orderItem.getOnsaleId());
        comment.setProductId(productPo.getId());
        comment.setStatus(Comment.TOBEAUDIT);
        comment.setGmtCreate(LocalDateTime.now());

        this.commentDao.save(comment);
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        return commentVo;
    }

    public void deleteCommentById(Long commentId) {
        Comment comment = commentDao.findById(commentId);
        if(comment.getStatus() == Comment.DELETED){
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST,"评论不存在或已删除");
        }
        comment.setStatus(Comment.DELETED);
        this.commentDao.save(comment);
    }

    public CommentVo getCommentById(Long commentId) {
        Comment comment = this.commentDao.findById(commentId);
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment,commentVo);

        return commentVo;
    }

    //查询所有评论
    public Page<CommentVo> retrieveCommentList(Long productId, int page, int pageSize)
    {
        InternalReturnObject<ProductPo> productCheck = productMapper.findProductById(productId);
        Page<CommentVo> commentListVo = commentDao.findCommentList(productId, page, pageSize);
        return commentListVo;
    }

    public InternalReturnObject getProductId(Long productId) {
        product.setId(productId);
        return product.getProductId();
    }
}
