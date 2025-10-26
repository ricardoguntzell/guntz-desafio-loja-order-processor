package br.com.guntz.desafio.loja.order.processor.api.model;

import br.com.guntz.desafio.loja.order.processor.domain.model.Product;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductDetailModel {

    private UUID id;

    private String name;
    private BigDecimal price;

    public ProductDetailModel(Product product) {
        BeanUtils.copyProperties(product, this);
    }
}
