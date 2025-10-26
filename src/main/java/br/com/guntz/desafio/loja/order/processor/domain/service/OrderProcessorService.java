package br.com.guntz.desafio.loja.order.processor.domain.service;

import br.com.guntz.desafio.loja.order.processor.api.config.exception.OrderDuplicatedException;
import br.com.guntz.desafio.loja.order.processor.api.model.OrderInputReceived;
import br.com.guntz.desafio.loja.order.processor.domain.model.Order;
import br.com.guntz.desafio.loja.order.processor.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderProcessorService {

    private final OrderRepository orderRepository;

    public void save(OrderInputReceived orderInputReceived) {
        verifyIfDuplicatedOrder(orderInputReceived.idExternal());

        Order order = Order.brandNew()
                .idExternal(orderInputReceived.idExternal())
                .items(orderInputReceived.items())
                .build();

        order.markAsCalculated();
        orderRepository.save(order);

        log.info("Order save: {}", orderInputReceived.idExternal());

    }

    private void verifyIfDuplicatedOrder(UUID idExternal) {
        if (isDuplicateOrder(idExternal)) {
            throw new OrderDuplicatedException(idExternal.toString());
        }
    }

    private Boolean isDuplicateOrder(UUID idExternal) {
        return orderRepository.existsByIdExternal(idExternal);
    }

}
