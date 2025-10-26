package br.com.guntz.desafio.loja.order.processor.domain.model;

import java.math.BigDecimal;

public class ProductTestDataBuilder {

    private ProductTestDataBuilder() {
    }

    public static Product aProductPS5() {
        return Product.brandNew()
                .name("PS5 Slim")
                .price(new BigDecimal("4000"))
                .build();
    }

    public static Product aProductNoteASUS() {
        return Product.brandNew()
                .name("Notebook ASUS Express")
                .price(new BigDecimal("9500"))
                .build();
    }


}
