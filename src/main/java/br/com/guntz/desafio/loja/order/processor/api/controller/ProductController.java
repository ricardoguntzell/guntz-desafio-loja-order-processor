package br.com.guntz.desafio.loja.order.processor.api.controller;

import br.com.guntz.desafio.loja.order.processor.api.model.ProductDetailModel;
import br.com.guntz.desafio.loja.order.processor.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductDetailModel>> listAll() {
        log.info("List products");

        return ResponseEntity.ok(productRepository.findAll().stream()
                .map(ProductDetailModel::new)
                .toList()
        );
    }

}
