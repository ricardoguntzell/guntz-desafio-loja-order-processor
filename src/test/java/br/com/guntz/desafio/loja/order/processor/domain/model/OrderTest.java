package br.com.guntz.desafio.loja.order.processor.domain.model;


import br.com.guntz.desafio.loja.order.processor.common.IdGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class OrderTest {

    @Test
    public void given_NewOrder_whenAddValidOrder_shouldNotGenerateException() {
        Product product = ProductTestDataBuilder.aProductPS5();

        Order order = OrderTestDataBuilder.aOrderDraftBuilder();

        order.addItem(product, 1);

        Assertions.assertWith(order,
                o -> Assertions.assertThat(o.getId()).isNotNull(),
                o -> Assertions.assertThat(o.getIdExternal()).isNotNull(),
                o -> Assertions.assertThat(o.getTotalAmount()).isNotZero(),
                o -> Assertions.assertThat(o.getTotalItems()).isNotZero(),
                o -> Assertions.assertThat(o.getItems()).isNotEmpty(),
                o -> Assertions.assertThat(o.getCreatedAt()).isNotNull()
        );

    }

    @Test
    public void given_newOrder_whenValidYourTotalAmountAndTotalItems_shouldSumIsCorrect() {
        Order order = Order.draftOrder()
                .idExternal(IdGenerator.generateTimeBasedUUID())
                .build();

        order.addItem(ProductTestDataBuilder.aProductPS5(), 2);
        order.addItem(ProductTestDataBuilder.aProductNoteASUS(), 1);

        var totalAmount = OrderItemBuildPs5().getPrice().multiply(new BigDecimal(2));
        var finalTotalAmount = totalAmount.add(OrderItemBuildNotebookAsus().getPrice());

        Assertions.assertWith(order,
                o -> Assertions.assertThat(o.getTotalItems()).isEqualTo(3),
                o -> Assertions.assertThat(o.getTotalAmount()).isEqualTo(finalTotalAmount)
        );
    }

    private static OrderItem OrderItemBuildPs5() {
        return OrderItemTestDataBuilder.aOrderItemPS5();
    }

    private static OrderItem OrderItemBuildNotebookAsus() {
        return OrderItemTestDataBuilder.aOrderItemNoteASUS();
    }

}