package cn.edu.xmu.oomall.customer.mapper.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.customer.mapper.openfeign.po.OnsalePo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",url = "http://1.94.231.245:8081")
public interface OnsaleMapper {
    @GetMapping("/onsales/{id}")
    InternalReturnObject<OnsalePo> findOnsaleById(@PathVariable Long id);
}
