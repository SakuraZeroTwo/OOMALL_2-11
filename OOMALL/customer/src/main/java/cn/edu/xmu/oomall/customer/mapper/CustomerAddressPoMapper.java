package cn.edu.xmu.oomall.customer.mapper;

import cn.edu.xmu.oomall.customer.mapper.po.CustomerAddressPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAddressPoMapper extends JpaRepository<CustomerAddressPo, Integer> {
    Optional<CustomerAddressPo> findById(Long id);
}
