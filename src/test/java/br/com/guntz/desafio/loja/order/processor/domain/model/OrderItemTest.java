package br.com.guntz.desafio.loja.order.processor.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemTest {

    @Test
    void given_NewOrderItem_whenAddValidOrderItem_shouldNotGenerateException() {
        Order order = OrderTestDataBuilder.aOrderDraftBuilder();

        Assertions.assertThatNoException()
                .isThrownBy(() -> OrderItem.brandNew()
                        .order(order)
                        .product(ProductTestDataBuilder.aProductNoteASUS())
                        .price(ProductTestDataBuilder.aProductNoteASUS().getPrice())
                        .quantity(1)
                        .build());
    }

    @Test
    void given_NewOrderItem_whenAddInvalidOrderItem_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> OrderItem.brandNew()
                        .product(null)
                        .quantity(null)
                        .build());
    }
}