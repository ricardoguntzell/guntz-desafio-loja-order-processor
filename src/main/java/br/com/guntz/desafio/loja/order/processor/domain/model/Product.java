package br.com.guntz.desafio.loja.order.processor.domain.model;

import br.com.guntz.desafio.loja.order.processor.common.IdGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Entity(name = "tb_product")
public class Product {

    @EqualsAndHashCode.Include
    @Id
    private UUID id;

    private String name;
    private BigDecimal price;

    @Builder(builderClassName = "BrandNewProductBuilder", builderMethodName = "brandNew")
    private static Product createBrandNew(String name, BigDecimal price) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(price);

        return new Product(
                IdGenerator.generateTimeBasedUUID(),
                name,
                price
        );
    }

    @Builder(builderClassName = "ExistingProductBuilder", builderMethodName = "existing")
    private Product(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
