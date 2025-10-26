package br.com.guntz.desafio.loja.order.processor.domain.model;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class ProductTest {

    @Test
    void given_NewProduct_whenAddValidProduct_shouldNotGenerateException() {
        Assertions.assertThatNoException()
                .isThrownBy(() -> Product.brandNew()
                        .name("PS5 Slim")
                        .price(new BigDecimal("4000"))
                        .build());
    }

    @Test
    void given_NewProduct_whenAddInvalidproduct_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Product.brandNew()
                        .name(null)
                        .price(null)
                        .build());
    }

}