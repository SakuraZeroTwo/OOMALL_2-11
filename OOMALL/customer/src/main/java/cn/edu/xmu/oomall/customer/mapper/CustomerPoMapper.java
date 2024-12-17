package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CustomerPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerPoMapper extends JpaRepository<CustomerPo, Long> {
    // 根据用户名查找顾客
    CustomerPo findByUserName(String userName);

    List<CustomerPo> findAll();

}
