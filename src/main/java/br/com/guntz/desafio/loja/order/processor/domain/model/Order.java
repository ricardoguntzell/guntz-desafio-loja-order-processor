package br.com.guntz.desafio.loja.order.processor.domain.model;

import br.com.guntz.desafio.loja.order.processor.common.IdGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;

@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@Entity(name = "tb_order")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    private UUID id;

    @EqualsAndHashCode.Include
    @Column(unique = true, nullable = false)
    private UUID idExternal;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> items;

    private BigDecimal totalAmount;
    private Integer totalItems;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private OffsetDateTime createdAt;

    @Builder(builderClassName = "BrandNewOrderBuilder", builderMethodName = "brandNew")
    public static Order createBrandNew(UUID idExternal, Set<OrderItem> items) {
        Order order = new Order(
                idExternal,
                new HashSet<>(),
                BigDecimal.ZERO,
                0,
                OrderStatus.CREATED,
                OffsetDateTime.now()
        );

        for (OrderItem item : items) {
            order.addItem(item.getProduct(), item.getQuantity());
        }

        order.recalculateTotals();

        return order;
    }

    @Builder(builderClassName = "draftOrderBuilder", builderMethodName = "draftOrder")
    public static Order createDraft(UUID idExternal) {
        Order order = new Order(
                idExternal,
                new HashSet<>(),
                BigDecimal.ZERO,
                0,
                OrderStatus.CREATED,
                OffsetDateTime.now()
        );

        order.recalculateTotals();

        return order;
    }

    @Builder(builderClassName = "newOrderBuilder", builderMethodName = "newOrderBuilder")
    public Order(UUID idExternal, Set<OrderItem> items, BigDecimal totalAmount, Integer totalItems,
                 OrderStatus orderStatus, OffsetDateTime createdAt) {
        this.id = IdGenerator.generateTimeBasedUUID();
        this.idExternal = idExternal;
        this.items = items;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;

        this.recalculateTotals();
    }


    @Builder(builderClassName = "ExistingOrderBuilder", builderMethodName = "existing")
    public Order(UUID id, UUID idExternal, Set<OrderItem> items, BigDecimal totalAmount, Integer totalItems,
                 OrderStatus orderStatus, OffsetDateTime createdAt) {
        this.id = id;
        this.idExternal = idExternal;
        this.items = items;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;

        this.recalculateTotals();
    }

    public void addItem(Product product, Integer quantity) {
        Objects.requireNonNull(product);
        Objects.requireNonNull(quantity);

        OrderItem item = OrderItem.brandNew()
                .order(this)
                .product(product)
                .price(product.getPrice())
                .quantity(quantity)
                .build();

        item.recalculateTotals();

        this.items.add(item);
        this.recalculateTotals();
    }

    private void recalculateTotals() {
        BigDecimal totalItemsAmount = this.items.stream()
                .map(OrderItem::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Integer totalItemsQuantity = this.items.stream()
                .map(OrderItem::getQuantity)
                .reduce(0, Integer::sum);

        this.setTotalAmount(totalItemsAmount);
        this.setTotalItems(totalItemsQuantity);
    }

    public void markAsCalculated() {
        this.setOrderStatus(OrderStatus.CALCULATED);
    }

    public Set<OrderItem> getItems() {
        return Collections.unmodifiableSet(this.items);
    }
}