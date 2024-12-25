package cn.edu.xmu.oomall.comment.mapper.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.comment.mapper.openfeign.po.ProductPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",url = "http://1.94.231.245:8081")
public interface ProductMapper {
    @GetMapping("/products/{id}")
    InternalReturnObject<ProductPo> findProductById(@PathVariable Long id);

    @GetMapping("/onsales/{id}")
    InternalReturnObject<ProductPo> findProductVoByOnsaleId(@PathVariable Long id);

}
