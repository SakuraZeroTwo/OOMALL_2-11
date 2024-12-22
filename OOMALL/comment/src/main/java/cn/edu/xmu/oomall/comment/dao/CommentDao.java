package cn.edu.xmu.oomall.comment.dao;

import cn.edu.xmu.javaee.core.exception.BusinessException;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;
import cn.edu.xmu.javaee.core.model.ReturnNo;
import cn.edu.xmu.javaee.core.model.dto.UserDto;
import cn.edu.xmu.oomall.comment.dao.bo.Comment;
import cn.edu.xmu.javaee.core.config.OpenFeignConfig;
import cn.edu.xmu.oomall.comment.mapper.CommentPoMapper;
import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Objects;
import cn.edu.xmu.javaee.core.mapper.RedisUtil;

import static cn.edu.xmu.javaee.core.model.Constants.IDNOTEXIST;

@Repository
@RequiredArgsConstructor
public class CommentDao {
    @Autowired
    private CommentPoMapper commentPoMapper;

    public Comment findById(Long id) {
        Optional<CommentPo> po = commentPoMapper.findById(id);
        if (po.isPresent()) {
            Comment bo = new Comment();
            BeanUtils.copyProperties(po.get(), bo);
            return bo;
        }
        else{
            throw new BusinessException(ReturnNo.RESOURCE_ID_NOTEXIST, String.format(ReturnNo.RESOURCE_ID_NOTEXIST.getMessage(), "评论", id));
        }
    }

    public Comment insert(Comment bo, UserDto user) throws RuntimeException {
        bo.setId(null);
        bo.setCreator(user);
        bo.setGmtCreate(LocalDateTime.now());
        CommentPo po = new CommentPo();
        BeanUtils.copyProperties(bo, po);
        po = commentPoMapper.save(po);
        bo.setId(po.getId());
        return bo;
    }
}
