package br.com.guntz.desafio.loja.order.processor.domain.model;

public class OrderItemTestDataBuilder {

    private OrderItemTestDataBuilder() {
    }

    public static OrderItem aOrderItemPS5() {
        Order order = OrderTestDataBuilder.aOrderDraftBuilder();

        return OrderItem.brandNew()
                .order(order)
                .product(ProductTestDataBuilder.aProductPS5())
                .price(ProductTestDataBuilder.aProductPS5().getPrice())
                .quantity(1)
                .build();
    }

    public static OrderItem aOrderItemNoteASUS() {
        Order order = OrderTestDataBuilder.aOrderDraftBuilder();

        return OrderItem.brandNew()
                .order(order)
                .product(ProductTestDataBuilder.aProductNoteASUS())
                .price(ProductTestDataBuilder.aProductNoteASUS().getPrice())
                .quantity(1)
                .build();
    }


}
