package br.com.guntz.desafio.loja.order.processor.domain.model;

import br.com.guntz.desafio.loja.order.processor.common.IdGenerator;

public class OrderTestDataBuilder {

    private OrderTestDataBuilder() {
    }

    public static Order aOrderDraftBuilder() {
        return Order.draftOrder()
                .idExternal(IdGenerator.generateTimeBasedUUID())
                .build();
    }
}
