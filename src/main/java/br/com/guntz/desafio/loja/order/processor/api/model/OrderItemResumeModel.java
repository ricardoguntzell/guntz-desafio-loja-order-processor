package br.com.guntz.desafio.loja.order.processor.api.model;

import br.com.guntz.desafio.loja.order.processor.domain.model.OrderItem;
import br.com.guntz.desafio.loja.order.processor.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class OrderItemResumeModel {

    private UUID id;

    private Product product;

    private BigDecimal price;
    private Integer quantity;

    private BigDecimal totalAmount;

    public OrderItemResumeModel(OrderItem item) {
        BeanUtils.copyProperties(item, this);
    }
}
