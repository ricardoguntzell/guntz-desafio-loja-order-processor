package br.com.guntz.desafio.loja.order.processor.api.config.exception;

public class OrderDuplicatedException extends DomainException {
    public OrderDuplicatedException(String message) {
        super(String.format(ErrorMessages.ERROR_ORDER_DUPLICATED, message));
    }

}
