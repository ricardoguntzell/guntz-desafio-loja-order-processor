package br.com.guntz.desafio.loja.order.processor.api.config.exception;

public class OrderNotFoundException extends DomainException {

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException() {
        super("");
    }
}
