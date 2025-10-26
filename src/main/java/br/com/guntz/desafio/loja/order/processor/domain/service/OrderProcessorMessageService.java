package br.com.guntz.desafio.loja.order.processor.domain.service;

import br.com.guntz.desafio.loja.order.processor.OrderProcessorApplication;
import br.com.guntz.desafio.loja.order.processor.api.model.OrderInputReceived;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class OrderProcessorMessageService {

    private final RabbitTemplate rabbitTemplate;
    private final OrderProcessorService orderProcessorService;

    public void processOrderReading(OrderInputReceived orderInputReceived) {
        orderProcessorService.save(orderInputReceived);
        log.info("Order Processed: {}", orderInputReceived.idExternal());
    }
}
