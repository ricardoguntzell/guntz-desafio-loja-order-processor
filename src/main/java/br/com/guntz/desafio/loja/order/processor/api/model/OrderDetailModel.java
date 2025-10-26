package br.com.guntz.desafio.loja.order.processor.api.model;

import br.com.guntz.desafio.loja.order.processor.domain.model.Order;
import br.com.guntz.desafio.loja.order.processor.domain.model.OrderItem;
import br.com.guntz.desafio.loja.order.processor.domain.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class OrderDetailModel {

    private UUID id;
    private UUID idExternal;

    private Set<OrderItemResumeModel> items = new HashSet<>();

    private BigDecimal totalAmount;
    private Integer totalItems;

    private OrderStatus orderStatus;
    private OffsetDateTime createdAt;

    public OrderDetailModel(Order order) {
        BeanUtils.copyProperties(order, this);

        for (OrderItem item : order.getItems()) {
            OrderItemResumeModel orderItemResumeModel = new OrderItemResumeModel(item);
            this.items.add(orderItemResumeModel);
        }
    }
}
