package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerPoMapper extends JpaRepository<CustomerPo, Long> {
    // 根据用户名查找顾客
    CustomerPo findByUserName(String userName);

    // 根据状态查询顾客列表
    List<CustomerPo> findByInvalidEquals(Byte invalid);

    // 查询已删除的顾客
    List<CustomerPo> findByBeDeletedEquals(Byte beDeleted);
}
