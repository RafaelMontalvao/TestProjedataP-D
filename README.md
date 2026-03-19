# TestProjedataP&D
Este projeto é um sistema de gerenciamento de manufatura desenvolvido para otimizar a produção com base no estoque atual de matéria-prima e no maior valor total de venda.

O Desafio: Lógica de Produção
O "coração" desta aplicação é um algoritmo que analisa o estoque disponível e sugere quais produtos fabricar para obter o Maior Retorno Financeiro.

Como o Algoritmo Funciona:
Para resolver conflitos de recursos (ex: dois produtos que usam a mesma matéria-prima), implementei uma estratégia de Densidade de Valor:

Cálculo de Rentabilidade: O sistema calcula a razão entre o preço do produto e a quantidade de materiais necessária.

Priorização: Os produtos são ordenados do maior para o menor valor gerado por unidade de recurso.

Alocação Linear: O algoritmo percorre a lista e abate o estoque real (em memória), garantindo que os produtos mais lucrativos sejam fabricados primeiro.

🛠️ Tecnologias Utilizadas
Back-end:

Java 17 / Spring Boot 3

Spring Data JPA (Banco de Dados H2)

Lombok & ModelMapper (Código limpo e mapeamento de DTOs)

JUnit 5 & Mockito (Testes unitários da lógica de negócio)

Front-end:

Vue.js 3 / Vite

Vuetify 3 (Componentes de UI)

ApexCharts 

Pinia

 Como Rodar o Projeto Localmente
Pré-requisitos:
JDK 21 instalado.

Node.js (v18 ou superior) e npm.

Maven (opcional, se usar o wrapper ./mvnw).

1. Clonar o Repositório
 ```bash
   git clone https://github.com/seu-usuario/nome-do-repositorio.git](https://github.com/RafaelMontalvao/TestProjedataP-D.git)
   cd nome-do-repositorio
   ```
2. Rodar o Back-end (Spring Boot)
  ```bash
   cd backend/industria
  ./mvnw spring-boot:run
```
A API estará disponível em http://localhost:8080

3. Rodar o Front-end (Vue/Vite)
 ```bash
  cd frontend/industriaFrontEnd
  npm install
  npm run dev
```
Front End disponivel em  http://localhost:5173

#Testes Unitários
Para validar a lógica do cálculo de produção e garantir que o sistema prioriza o lucro corretamente:
 ```bash
  cd backend
  mvn test
```







   
