package br.com.guntz.desafio.loja.order.processor.infrastructure.rabbitmq;

import br.com.guntz.desafio.loja.order.processor.api.model.OrderInputReceived;
import br.com.guntz.desafio.loja.order.processor.domain.service.OrderProcessorMessageService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static br.com.guntz.desafio.loja.order.processor.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_ORDER_PROCESSOR_PROCESSING_RESULT;


@AllArgsConstructor
@Component
public class RabbitMQListener {

    private final OrderProcessorMessageService orderProcessorMessageService;

    @SneakyThrows
    @RabbitListener(queues = QUEUE_ORDER_PROCESSOR_PROCESSING_RESULT, concurrency = "2-3")
    public void handleProcessOrder(@Payload OrderInputReceived orderInputReceived) {
        orderProcessorMessageService.processOrderReading(orderInputReceived);
    }

}
