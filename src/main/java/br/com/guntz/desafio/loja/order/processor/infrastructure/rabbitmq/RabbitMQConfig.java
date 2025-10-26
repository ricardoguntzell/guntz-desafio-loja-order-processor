package br.com.guntz.desafio.loja.order.processor.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    private static final String PROCESS_ORDER_PROCESSOR_PROCESSING_RESULT = "order-processor.order-received-result.v1";

    public static final String QUEUE_ORDER_PROCESSOR_PROCESSING_RESULT = PROCESS_ORDER_PROCESSOR_PROCESSING_RESULT + ".q";
    public static final String DEAD_LETTER_QUEUE_ORDER_PROCESSOR_PROCESSING_RESULT = PROCESS_ORDER_PROCESSOR_PROCESSING_RESULT + ".dlq";
    public static final String FONOUT_EXCHANGE_ORDER_PROCESSOR_RECEIVED = PROCESS_ORDER_PROCESSOR_PROCESSING_RESULT + ".e";

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public Queue queueOrderReceiveProcessingResult() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_ORDER_PROCESSOR_PROCESSING_RESULT);

        return QueueBuilder
                .durable(QUEUE_ORDER_PROCESSOR_PROCESSING_RESULT)
                .withArguments(args)
                .build();
    }

    @Bean
    public Queue deadLetterQueueOrderReceiveProcessingResult() {
        return QueueBuilder
                .durable(DEAD_LETTER_QUEUE_ORDER_PROCESSOR_PROCESSING_RESULT)
                .build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queueOrderReceiveProcessingResult()).to(exchangeOrderReceiveToReceived());
    }

    @Bean
    public FanoutExchange exchangeOrderReceiveToReceived() {
        return ExchangeBuilder
                .fanoutExchange(FONOUT_EXCHANGE_ORDER_PROCESSOR_RECEIVED)
                .build();
    }

}