
# Order

Microsserviço de pedidos que irá funcionar de forma assíncrona. Com uma arquitetura Hexagonal.

Assim que um pedido é efetuado, os dados do pedido devem ser gravados em uma collection 
no Mongo com o status AGUARDANDO_ENVIO e uma mensagem deve ser postada em um tópico Kafka informando que um novo pedido foi efetuado.
 
Existe um consumidor para esse tópico Kafka que seja capaz de ler os dados, 
realizar a busca desse pedido no Mongo e alterar o status do mesmo para 
ENVIADO_TRANSPORTADORA.

Ao consultar o pedido através do endpoint de consulta, ele deve retornar o pedido com o 
status devidamente atualizado.
## Documentação da API

#### Cria um novo pedido

```http
  POST /micro-order/v1/orders
```

#### Retorna todos os pedidos

```http
  GET /micro-order/v1/orders
```

#### Retorna um pedido

```http
  GET /micro-order/v1/orders/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `string` | **Obrigatório**. O ID do pedido que você quer |







## Rodando os testes

Para rodar os testes, rode o seguinte comando

```bash
  mvn test
```


## Variáveis de Ambiente

Para rodar esse projeto, você vai precisar adicionar as seguintes variáveis de ambiente no seu ENVIRONMENT rodando o projeto local usando o docker-compose-local.yml e env.conf

`PORT=9000`

`ENVIRONMENT=dev`

`BOOTSTRAP_SERVERS=localhost:29092`

`MONGO_URI=mongodb://root:root@localhost:27017`

## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/sergiogprazeres/order.git
```

Entre no diretório do projeto

```bash
  cd order
```

Instale as dependências e build (gerando a imagem do projeto)

```bash
  mvn install
```

Importar a imagem para o Docker

```bash
  docker-compose build
```

Inicie o servidor

```bash
  docker-compose up -d
```

Acesse pelo browser:

```bash
  http://localhost:9000/swagger-ui/index.htm
```


## Autores

- [@sergiogprazeres](https://github.com/sergiogprazeres)


## Suporte

Para suporte, mande um email para sergiogprazeres@gmail.com.


## Licença

[GPL-3.0 license](https://www.gnu.org/licenses/quick-guide-gplv3)

