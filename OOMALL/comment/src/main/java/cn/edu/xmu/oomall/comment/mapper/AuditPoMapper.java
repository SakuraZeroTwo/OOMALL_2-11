package cn.edu.xmu.oomall.comment.mapper;

import cn.edu.xmu.oomall.comment.mapper.po.AuditPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface AuditPoMapper extends JpaRepository<AuditPo, Long> {

}
