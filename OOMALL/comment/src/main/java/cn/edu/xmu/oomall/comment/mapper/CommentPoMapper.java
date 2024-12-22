package cn.edu.xmu.oomall.comment.mapper;

import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentPoMapper extends JpaRepository<CommentPo, Long> {

}
