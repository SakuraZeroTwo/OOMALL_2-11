package cn.edu.xmu.oomall.comment.dao.openfeign;

import cn.edu.xmu.javaee.core.model.InternalReturnObject;
import cn.edu.xmu.oomall.comment.dao.bo.Product;
import cn.edu.xmu.oomall.comment.mapper.openfeign.ProductMapper;
import cn.edu.xmu.oomall.comment.mapper.openfeign.po.OrderItem;
import cn.edu.xmu.oomall.comment.mapper.openfeign.po.ProductPo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductDao {
    private final ProductMapper productMapper;

    public ProductPo findById(Long id) {
        InternalReturnObject<ProductPo> ret = this.productMapper.findProductVoByOnsaleId(id);
        return ret.getData();
    }
}
