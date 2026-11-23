# Bolonha e Milano — Etapas 1 a 8

Jogo textual de estratégia econômica medieval, desenvolvido em Java 17 para 1 a 4 jogadores. Cada jogador administra uma cidade e disputa prestígio até alguém alcançar o título de Rei/Rainha ou o ano final ser ultrapassado.

## Estrutura

- `../../Downloads/bolonha-e-milano-etapas-1-a-8 (1)/bolonha_etapa8_build/src/bolonhaemilano`: código-fonte Java.
- `uml/classes-etapas-1-a-8.puml`: diagrama de classes cumulativo.
- `uml/sequencia-fim-do-ano.puml`: sequência de encerramento do ano.
- `casos-de-uso/casos-de-uso-etapas-1-a-8.puml`: casos de uso finais.

## Compilar e executar

Requer Java 17 ou superior. Na raiz do projeto:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Main
```

## Como jogar

1. **Novo jogo**: escolha de 1 a 4 jogadores e informe o nome de cada governante e cidade.
2. **Ver status**: consulta recursos, governo, defesa e ações anuais da cidade da vez.
3. **Mercado**: compra e venda de grãos e terras.
4. **Agricultura**: colheita (uma vez por ano) e distribuição de alimentos (20% a 80%).
5. **Governo**: arrecadação de impostos (uma vez por ano), taxa de 0% a 50% e nível de justiça.
6. **Construções**: recruta soldados (5 florins e 1 servo por soldado) e constrói (20 florins por construção).
7. **Encerrar turno**: passa a vez; quando o último jogador encerra, todas as cidades avançam um ano, os governantes podem ser promovidos e a partida é verificada.
8. **Classificação**: mostra governante, título, prestígio e distância para o próximo título.
9. **Sobre**: informações do jogo.
0. **Sair**.

## Regras de rodada e vitória

- O jogo começa em 1400; cada cidade joga uma vez por ano.
- A colheita e a arrecadação de impostos só podem ocorrer uma vez por cidade em cada ano. `Cidade.avancarAno()` libera ambas novamente.
- Prestígio = construções × 10 + soldados × 2 + servos + terras ÷ 100. Florins não contam.
- Limiares: Cavaleiro (início), Barão (400), Conde (550), Marquês (700), Duque (850), Rei/Rainha (1000). No máximo uma promoção por fechamento de ano.
- A partida termina quando alguém vira Rei/Rainha ou quando o ano passa de 1410. O vencedor é quem tem o título mais alto; em empate, vence quem tem maior prestígio.

## Rodar os testes automatizados

A classe `Testes` é independente do jogo e não usa JUnit nem bibliotecas externas:

```bash
javac -encoding UTF-8 -d bin src/bolonhaemilano/*.java
java -cp bin bolonhaemilano.Testes
```

A suíte contém 37 verificações cobrindo caminhos válidos e casos de erro. O resultado esperado é `Aprovados: 37 | Reprovados: 0`. Se houver reprovação, o processo termina com código de saída 1.

## Git

Para registrar o marco final em um repositório Git já existente, use a mensagem `Etapa 8: integração, testes e entrega final` e crie a tag `etapa-8`. Os commits históricos das Etapas 1 a 7 precisam existir no repositório original; eles não podem ser reconstruídos fielmente a partir apenas do código final.
