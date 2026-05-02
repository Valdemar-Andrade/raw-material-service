# Serviço de Matéria-Prima

Parte de um **Simulador de Pipeline Industrial da Indústria 4.0** distribuído, construído com arquitetura de micro-serviços orientada a eventos.

---

## 🧠 Visão Geral do Sistema

Este projeto simula um pipeline de produção industrial real, onde serviços independentes colaboram para produzir bens.

Fluxo do pipeline:

Matéria-Prima → Processamento → Produção de Componentes → Montagem do Produto

Cada etapa opera como um microsserviço isolado, comunicando através de eventos Kafka.

---

## 🎯 Função deste Serviço

O **Serviço Matéria-Prima** é o ponto de entrada do pipeline de produção.

É responsável por simular a extração de matérias-primas (por exemplo, ferro, látex, areia) que serão utilizadas pelos serviços subsequentes.

Sem este serviço, toda a cadeia de produção não pode ser iniciada.

---

## ⚙️ Responsabilidades

- Simular a extração de matéria-prima utilizando pipelines baseados no tempo
- Validar os pedidos de produção recebidos
- Persistir os materiais extraídos
- Publicar eventos no Kafka para processamento subsequente

---

## 🔄 Posição no Pipeline
[ Serviço de Matéria-Prima ] → [ Serviço de Processamento ] → [ Serviço de Componentes ] → [ Serviço de Montagem ]

---

## 📡 Comunicação Orientada a Eventos

### Eventos Produzidos

- `RAW_MATERIAL_EXTRACTED`

### Estrutura do Evento

```
{

"eventId": "uuid",

"eventType": "RAW_MATERIAL_EXTRACTED",

"timestamp": 1710000000,

"sourceService": "raw-material-service",

"targetService": "component-service",

"payload": {
"nome": "Ferro",

"quantidade": 10

}
}
```

## ⏱️ Pipeline de Produção (Simulação de Latência)

A produção não é instantânea. Cada pedido passa por um pipeline com durações definidas.

Exemplo
```
[
{ "nome": "EXTRAÇÃO", "duraçãoMs": 10000 },

{ "nome": "TRANSPORTE", "duraçãoMs": 5000 }
]
```

Isto simula atrasos industriais reais.

## 🔄 Fluxo Interno
- Receber pedido HTTP
- Validar dados de entrada
- Executar pipeline de produção (com atraso)
- Persistir matéria-prima na base de dados
- Publicar evento Kafka

## 🗄️ Propriedade dos Dados

Este serviço segue as melhores práticas de micro-serviços:

- Base de dados própria
- Sem acesso direto aos dados de outros serviços
- Comunicação estritamente via eventos

## 🧱 Tecnologias
- Java + Spring Boot
- Apache Kafka
- PostgreSQL
- Docker

## Executar o Serviço
docker-compose up --build

## 🧠 Conceitos-chave Demonstrados
- Arquitetura orientada a eventos
- Design de sistemas distribuídos
- Simulação de pipeline de produção com latência
- Isolamento do serviço e propriedade dos dados

## Outros Serviços:
- [processing-service](https://github.com/Valdemar-Andrade/processing-service.git)

## 👤 Autor
- GitHub: [@Valdemar-Andrade]
- LinkedIn: [Valdemar Andrade](https://www.linkedin.com/in/valdemar-andrade-8b0b45189)

