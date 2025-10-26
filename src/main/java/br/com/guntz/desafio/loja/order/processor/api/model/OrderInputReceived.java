package br.com.guntz.desafio.loja.order.processor.api.model;


import br.com.guntz.desafio.loja.order.processor.domain.model.OrderItem;
import br.com.guntz.desafio.loja.order.processor.domain.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;


public record OrderInputReceived(

        @NotNull
        UUID idExternal,

        @NotNull
        Set<OrderItem> items,

        @NotNull
        OrderStatus orderStatus,

        @NotNull
        OffsetDateTime createdAt

) {

}
