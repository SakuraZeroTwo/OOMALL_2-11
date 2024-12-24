//School of Informatics Xiamen University, GPL-3.0 license

package cn.edu.xmu.oomall.order.dao.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.order.dao.openfeign.dto.OnsaleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service",url = "http://1.94.231.245:8081")
public interface GoodsDao {

    @GetMapping("/shops/{shopId}/onsales/{id}")
    InternalReturnObject<OnsaleDto> getOnsaleById(@PathVariable Long shopId, @PathVariable Long id);

}
