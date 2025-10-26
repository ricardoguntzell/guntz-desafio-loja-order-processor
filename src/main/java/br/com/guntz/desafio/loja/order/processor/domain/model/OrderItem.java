package br.com.guntz.desafio.loja.order.processor.domain.model;

import br.com.guntz.desafio.loja.order.processor.common.IdGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter(AccessLevel.PRIVATE)
@Entity(name = "tb_order_items")
public class OrderItem {

    @EqualsAndHashCode.Include
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    private Product product;

    private BigDecimal price;
    private Integer quantity;

    private BigDecimal totalAmount;

    @Builder(builderClassName = "BrandNewOrderItemBuilder", builderMethodName = "brandNew")
    public static OrderItem createBrandNew(Order order, Product product, BigDecimal price, Integer quantity) {
        Objects.requireNonNull(order);
        Objects.requireNonNull(product);
        Objects.requireNonNull(price);
        Objects.requireNonNull(quantity);

        OrderItem orderItem = new OrderItem(
                order,
                IdGenerator.generateTimeBasedUUID(),
                product,
                price,
                quantity,
                BigDecimal.ZERO
        );

        orderItem.recalculateTotals();

        return orderItem;
    }

    @Builder(builderClassName = "ExistingOrderItemBuilder", builderMethodName = "existing")
    public OrderItem(Order order, UUID id, Product product, BigDecimal price, Integer quantity, BigDecimal totalAmount) {
        this.order = order;
        this.id = id;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void recalculateTotals() {
        this.setTotalAmount(this.price.multiply(new BigDecimal(this.quantity)));
    }


}
