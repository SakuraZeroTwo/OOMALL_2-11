package cn.edu.xmu.oomall.comment.mapper;

import cn.edu.xmu.oomall.comment.mapper.po.CommentPo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentPoMapper extends JpaRepository<CommentPo, Long> {
    public Optional<CommentPo> findById(Long id);

////    List<CommentPo> selectCommentsByProductId(@Param("productId") Long productId,
////                                    @Param("offset") int offset,
////                                    @Param("pageSize") int pageSize);
//    List<CommentPo> findByProductId(@Param("productId") Long productId, Pageable pageable);
    Page<CommentPo> findByProductId(Long productId, Pageable pageable); // 确保返回 Page 类型
}
