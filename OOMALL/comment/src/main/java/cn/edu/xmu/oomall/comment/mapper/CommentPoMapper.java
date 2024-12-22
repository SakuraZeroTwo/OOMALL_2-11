package cn.edu.xmu.oomall.comment.mapper;

import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentPoMapper extends JpaRepository<CommentPo, Long> {
    public Optional<CommentPo> findById(Long id);
}
