package cn.edu.xmu.oomall.order.mapper.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.order.mapper.openfeign.po.ShopPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shop-service",url = "http://localhost:8080")
public interface ShopMapper {
    @GetMapping("/shops/{id}")
    InternalReturnObject<ShopPo> findShopById(@PathVariable Long id);
}