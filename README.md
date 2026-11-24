# Bolonha e Milano

**Bolonha e Milano** é um jogo textual de estratégia econômica ambientado na Europa medieval. Desenvolvido em **Java 26**, o jogo permite que de 1 a 4 jogadores administrem suas próprias cidades, cuidem da economia e da população, fortaleçam o governo e disputem prestígio para alcançar os títulos mais altos da nobreza.

A partida começa no ano de **1400**. A cada rodada, os jogadores realizam suas ações e, quando todos encerram seus turnos, o ano avança. A disputa termina quando um governante alcança o título de Rei ou Rainha, ou quando o calendário ultrapassa o ano de 1410.

## Requisitos

- Java Development Kit (JDK) 26 ou superior.
- Terminal ou prompt de comando para compilar e executar o jogo.

Não são necessárias bibliotecas externas.

## Organização do projeto

```text
projeto-bolonha-e-milano-etapa-8/
├── src/
│   └── bolonhaemilano/
│       ├── Main.java
│       ├── Menu.java
│       ├── Jogador.java
│       ├── Cidade.java
│       ├── Partida.java
│       ├── Mercado.java
│       ├── Agricultura.java
│       ├── Governo.java
│       ├── Construcoes.java
│       ├── Nobreza.java
│       └── Testes.java
├── uml/
│   ├── classes-etapas-1-a-8.puml
│   └── sequencia-fim-do-ano.puml
├── casos-de-uso/
│   └── casos-de-uso-etapas-1-a-8.puml
└── README.md
```

O código está organizado no pacote `bolonhaemilano`. Os arquivos `.puml` contêm os diagramas do projeto e podem ser visualizados em ferramentas compatíveis com PlantUML.

## Como compilar e executar

Abra o terminal na pasta principal do projeto e execute:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
```

Depois, inicie o jogo com:

```bash
java -cp bin bolonhaemilano.Main
```

A pasta `bin` será utilizada para armazenar os arquivos compilados (`.class`).

## Como jogar

Ao iniciar, selecione **Novo jogo**. Informe a quantidade de participantes (de 1 a 4), o nome de cada jogador e o nome de sua cidade. Cada participante começa com uma cidade e recursos iniciais próprios.

O menu principal apresenta as seguintes opções:

| Opção | Funcionalidade | Descrição |
|---|---|---|
| 1 | Novo jogo | Inicia uma partida e cadastra os jogadores e suas cidades. |
| 2 | Ver status da cidade | Exibe os recursos, a população, o governo, os soldados, as construções e as ações anuais da cidade atual. |
| 3 | Mercado | Permite comprar e vender grãos e terras. |
| 4 | Agricultura | Permite realizar a colheita e distribuir alimentos à população. |
| 5 | Governo | Permite arrecadar impostos, definir a taxa tributária e escolher o nível de justiça. |
| 6 | Construções | Permite recrutar soldados e construir estruturas. |
| 7 | Encerrar turno | Finaliza o turno do jogador atual e passa a vez ao próximo. |
| 8 | Ver classificação | Exibe os títulos e o prestígio de todos os participantes. |
| 9 | Sobre o jogo | Apresenta uma breve descrição do jogo. |
| 0 | Sair | Encerra a execução do jogo. |

As opções que dependem de uma partida ativa só podem ser utilizadas depois de iniciar um novo jogo.

## Recursos iniciais

Cada cidade começa no ano de 1400 com os seguintes recursos e configurações:

| Recurso ou atributo | Valor inicial |
|---|---:|
| Florins | 1.000 |
| Terras | 10.000 hectares |
| Grãos | 500 |
| Servos | 200 |
| Taxa de imposto | 10% |
| Nível de justiça | Moderada |
| Soldados | 0 |
| Construções | 0 |
| Título do governante | Cavaleiro |

## Sistemas do jogo

### Mercado

O mercado permite negociar dois recursos:

- **Grãos:** 2 florins por unidade.
- **Terras:** 5 florins por hectare.

É possível comprar ou vender cada recurso. As operações dependem de quantidade válida e de saldo ou estoque suficiente.

### Agricultura e população

A agricultura possui duas ações principais:

- **Colheita:** a área que pode ser cultivada depende da quantidade de terras e de servos disponíveis. Cada servo permite cultivar até 5 hectares. O cultivo utiliza sementes equivalentes à metade da área plantada e produz 2 grãos por hectare. Se o estoque de sementes não for suficiente, a área cultivada é reduzida.
- **Distribuição de alimentos:** o jogador escolhe distribuir entre 20% e 80% dos grãos armazenados. A quantidade recebida pela população influencia seu crescimento ou sua redução. Quando a alimentação atende à necessidade calculada, a população cresce 5%; caso contrário, perde servos de acordo com o déficit.

A colheita só pode ser realizada uma vez por cidade em cada ano. A distribuição de alimentos pode ser feita durante o turno, respeitando o percentual permitido.

### Governo e impostos

O jogador pode configurar a taxa de imposto entre **0% e 50%** e escolher um dos três níveis de justiça:

- **Justa:** multiplicador de arrecadação de 0,8.
- **Moderada:** multiplicador de arrecadação de 1,0.
- **Severa:** multiplicador de arrecadação de 1,3.

A arrecadação considera a quantidade de servos, a taxa de imposto e o multiplicador correspondente ao nível de justiça. Os impostos só podem ser arrecadados uma vez por cidade em cada ano.

### Construções e soldados

O jogador pode investir seus florins para ampliar a cidade e sua força militar:

- **Recrutar soldados:** custa 5 florins e 1 servo por soldado.
- **Construir:** custa 20 florins por construção.

O recrutamento verifica se há florins e servos suficientes antes de efetuar a operação.

## Turnos, prestígio e vitória

Cada jogador realiza seu turno na ordem em que foi cadastrado. Ao escolher **Encerrar turno**, a vez passa ao próximo participante. Quando o último jogador encerra seu turno, o ano termina: todas as cidades avançam um ano, os indicadores de colheita e impostos são liberados para o novo período e os governantes podem receber promoções.

O prestígio de cada cidade é calculado pela seguinte fórmula:

```text
Prestígio = (construções × 10) + (soldados × 2) + servos + (terras ÷ 100)
```

Os títulos e seus requisitos de prestígio são:

| Título | Prestígio necessário |
|---|---:|
| Cavaleiro | Título inicial |
| Barão | 400 |
| Conde | 550 |
| Marquês | 700 |
| Duque | 850 |
| Rei/Rainha | 1.000 |

As promoções são verificadas no encerramento de cada ano, e cada governante pode subir no máximo um título por encerramento anual.

A partida termina quando um jogador alcança o título de **Rei/Rainha** ou quando o ano passa de **1410**. O vencedor é definido pelo título mais alto. Se houver empate no título, vence o participante com maior prestígio.

## Testes automatizados

O projeto inclui a classe `Testes`, que verifica o funcionamento das principais regras e operações, incluindo situações válidas e casos de erro. Os testes não dependem de JUnit nem de bibliotecas externas.

Para compilar o projeto e executar os testes, utilize:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Testes
```

A execução informa a quantidade de testes aprovados e reprovados. O resultado esperado para a versão atual é:

```text
Aprovados: 37 | Reprovados: 0
```

## Desenvolvimento

O projeto foi construído em etapas, evoluindo da estrutura inicial do jogo até a integração dos sistemas de economia, agricultura, governo, construções, múltiplos jogadores, classificação, vitória e testes automatizados.
