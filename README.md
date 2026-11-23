# Bolonha e Milano

Projeto escolar em Java 17 para desenvolver, em etapas incrementais, um jogo de estratégia econômica medieval.

## Estrutura

- `src/bolonhaemilano/`: código-fonte Java.
- `casos-de-uso/`: diagramas de casos de uso.
- `uml/`: diagramas UML de classes e outros diagramas.
- `bin/`: arquivos compilados.

## Requisitos

- Java 17 ou superior
- Terminal/linha de comando

## Compilar

Na pasta raiz do projeto:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
```

## Executar

```bash
java -cp bin bolonhaemilano.Main
```

## Etapa 1

- Classe `Main` cria um objeto `Menu` e delega a execução.
- Classe `Menu` exibe as opções Novo jogo, Sobre o jogo e Sair.
- Entradas inválidas são tratadas sem encerrar o programa.
- O menu permanece em execução até a opção `0`.
- A lógica real do jogo será adicionada nas próximas etapas.
