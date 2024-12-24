//School of Informatics Xiamen University, GPL-3.0 license
package cn.edu.xmu.oomall.product.mapper.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.product.mapper.openfeign.po.Shop;
import cn.edu.xmu.oomall.product.mapper.openfeign.po.Template;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "shop-service",url = "http://116.205.114.101:8080")
public interface ShopMapper {

    @GetMapping("/shops/{id}")
    InternalReturnObject<Shop> getShopById(@PathVariable Long id);

    @GetMapping("/shops/{shopId}/templates/{id}")
    InternalReturnObject<Template> getTemplateById(@PathVariable Long shopId, @PathVariable Long id);

}
