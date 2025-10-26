package br.com.guntz.desafio.loja.order.processor.api.config;

import br.com.guntz.desafio.loja.order.processor.api.config.exception.OrderDuplicatedException;
import br.com.guntz.desafio.loja.order.processor.api.config.exception.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handler(MethodArgumentNotValidException ex) {

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("One or more fields are invalid");
        problemDetail.setType(URI.create("/errors/fields-invalids"));

        var errors = ex.getFieldErrors().stream()
                .map(ErrorValidation::new)
                .toList();

        problemDetail.setProperty("fields", errors);

        return problemDetail;
    }

    @ExceptionHandler(OrderDuplicatedException.class)
    public ProblemDetail handler(OrderDuplicatedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problemDetail.setTitle("Order already exist.");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/order-duplicated"));

        return problemDetail;
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ProblemDetail handler(OrderNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problemDetail.setTitle("Not Found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/not-found"));

        return problemDetail;
    }

    private record ErrorValidation(String field, String message) {

        public ErrorValidation(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
