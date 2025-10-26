package br.com.guntz.desafio.loja.order.processor.api.controller;

import br.com.guntz.desafio.loja.order.processor.api.config.exception.OrderNotFoundException;
import br.com.guntz.desafio.loja.order.processor.api.model.OrderDetailModel;
import br.com.guntz.desafio.loja.order.processor.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    @GetMapping
    public ResponseEntity<Page<OrderDetailModel>> listAll(@PageableDefault Pageable pageable) {
        log.info("List orders");

        return ResponseEntity.ok(orderRepository.findAll(pageable).map(OrderDetailModel::new));
    }

    @GetMapping("/{idExternal}")
    public ResponseEntity<OrderDetailModel> findOrderByidExternal(@PathVariable UUID idExternal) {
        log.info("Executing search the order: {}", idExternal);

        System.out.println("kiko");

        var orderDetailModel = orderRepository.findByIdExternal(idExternal)
                .map(OrderDetailModel::new)
                .orElseThrow(OrderNotFoundException::new);

        return ResponseEntity.ok(orderDetailModel);
    }
}
