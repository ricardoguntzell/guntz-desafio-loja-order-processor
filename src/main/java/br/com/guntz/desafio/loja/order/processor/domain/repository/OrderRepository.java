package br.com.guntz.desafio.loja.order.processor.domain.repository;

import br.com.guntz.desafio.loja.order.processor.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Boolean existsByIdExternal(UUID idExternal);

    Optional<Order> findByIdExternal(UUID idExternal);
}
