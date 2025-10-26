# Microsserviço: Order Processor

**Repositório:** [guntz-desafio-loja-order-processor](https://github.com/ricardoguntzell/guntz-desafio-loja-order-processor)

Este microsserviço é o coração do processamento de pedidos. Sua responsabilidade é consumir os pedidos da fila do RabbitMQ, aplicar as regras de negócio e persistir os dados.

## Funcionalidades

- **Consumidor RabbitMQ**: Escuta a fila de pedidos e consome as mensagens de forma assíncrona.
- **Processamento de Pedidos**:
  - Calcula o valor total do pedido.
  - Calcula a quantidade total de itens.
  - Realiza qualquer outra lógica de negócio necessária.
- **Persistência de Dados**: Salva os pedidos processados em um banco de dados H2.
- **Resiliência**: Utiliza um mecanismo de retentativas (retry) com backoff exponencial para lidar com falhas temporárias no processamento, garantindo que nenhuma mensagem seja perdida facilmente.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring for RabbitMQ
- Spring Data JPA
- H2 Database
- Lombok

## Como Executar

Certifique-se de que o RabbitMQ esteja em execução e as variáveis de ambiente (`RABBITMQ_DEFAULT_USER` e `RABBITMQ_DEFAULT_PASS`) estejam configuradas.

```bash
# A partir da raiz do projeto (guntz-desafio-loja)
cd microservices/order-processor
./mvnw spring-boot:run
```

O serviço estará disponível na porta `8081`.

## Documentação e Observabilidade

### Documentação de Endpoints (Swagger)

Embora este serviço não exponha uma API de negócio principal, ele possui endpoints administrativos (como `/actuator/health`) que são documentados via Swagger para facilitar a inspeção.

**URL**: http://localhost:8081/swagger-ui.html

### Observabilidade (Prometheus)

O serviço expõe métricas para o Prometheus no endpoint `/actuator/prometheus`. Essas métricas são cruciais para monitorar a saúde do consumidor, a taxa de processamento de mensagens e o uso de recursos.

---

## Acesso ao Banco de Dados H2

Com a aplicação em execução, você pode acessar o console do banco de dados H2 para inspecionar os dados.

- **URL do Console H2**: http://localhost:8081/h2-console
- **JDBC URL**: `jdbc:h2:file:~/guntz-desafio-loja-db`
- **User Name**: `sa`
- **Password**: `123`

Após conectar, você poderá executar queries nas tabelas `ORDERS` e `ORDER_ITEMS` para verificar os pedidos que foram processados e salvos.

## Configuração do Consumidor

O consumidor RabbitMQ está configurado para otimizar o processamento:
- `prefetch: 4`: Busca até 4 mensagens da fila por vez para processamento local.
- `concurrency: 2-3`: Utiliza entre 2 e 3 threads para consumir mensagens em paralelo, aumentando a vazão.