package cn.edu.xmu.oomall.comment.mapper.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.comment.mapper.openfeign.po.OrderItem;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "order-service",url = "http://localhost:8085")
public interface OrderItemMapper {
    @GetMapping("/orders/orderItem/{orderItemId}/onsale")
    InternalReturnObject<OrderItem> getOnsaleByOrderItemId(@PathVariable Long orderItemId);
}
