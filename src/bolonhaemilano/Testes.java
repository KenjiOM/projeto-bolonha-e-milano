package bolonhaemilano;

/** Testes automatizados simples, sem bibliotecas externas (preparar, executar, verificar). */
public class Testes {
    private static int aprovados = 0;
    private static int reprovados = 0;
    private static void verificar(String descricao, boolean condicao) {
        if (condicao) { aprovados++; System.out.println("[OK]    " + descricao); }
        else { reprovados++; System.out.println("[FALHA] " + descricao); }
    }
    private static Cidade novaCidade() { return new Cidade("Bolonha", new Jogador("Teste")); }
    public static void main(String[] args) {
        System.out.println("===== TESTES BOLONHA E MILANO =====");
        testarCidade(); testarMercado(); testarAgricultura(); testarGoverno();
        testarConstrucoes(); testarJogadorENobreza(); testarPartida();
        System.out.println("Aprovados: " + aprovados + " | Reprovados: " + reprovados);
        if (reprovados > 0) System.exit(1);
    }
    private static void testarCidade() {
        System.out.println("-- Cidade --");
        Cidade c = novaCidade();
        verificar("cidade nasce com 1000 florins", c.getFlorins() == 1000);
        verificar("cidade nasce no ano 1400", c.getAnoAtual() == 1400);
        verificar("removerFlorins acima do saldo devolve false", !c.removerFlorins(1001));
        verificar("e não altera o saldo", c.getFlorins() == 1000);
        c.marcarColheitaFeita(); c.marcarImpostosArrecadados(); c.avancarAno();
        verificar("avancarAno soma 1 ao ano", c.getAnoAtual() == 1401);
        verificar("avancarAno libera colheita e impostos", !c.isColheitaFeita() && !c.isImpostosArrecadados());
    }
    private static void testarMercado() {
        System.out.println("-- Mercado --");
        Cidade c = novaCidade(); Mercado m = new Mercado();
        verificar("comprar 100 grãos dá certo", m.comprarGraos(c, 100));
        verificar("e custa 200 florins", c.getFlorins() == 800);
        verificar("e soma 100 grãos", c.getGraos() == 600);
        verificar("comprar quantidade negativa é recusado", !m.comprarGraos(c, -1));
        verificar("comprar sem florins suficientes é recusado", !m.comprarTerras(c, 1000));
    }
    private static void testarAgricultura() {
        System.out.println("-- Agricultura --");
        Cidade c = novaCidade(); Agricultura a = new Agricultura();
        verificar("primeira colheita do ano cultiva 1000 hectares", a.processarColheita(c) == 1000);
        verificar("e deixa 2000 grãos (500 - 500 sementes + 2000)", c.getGraos() == 2000);
        verificar("segunda colheita no mesmo ano devolve -1", a.processarColheita(c) == -1);
        verificar("distribuir 10% é recusado (mínimo 20%)", !a.distribuirAlimentos(c, 10));
    }
    private static void testarGoverno() {
        System.out.println("-- Governo --");
        Cidade c = novaCidade(); Governo g = new Governo();
        verificar("taxa de 60% é recusada", !g.definirTaxaImposto(c, 60));
        verificar("justiça Branda é recusada", !g.definirJustica(c, "Branda"));
        g.definirJustica(c, "Severa");
        verificar("200 servos, 10%, Severa arrecada 26 florins", g.arrecadarImpostos(c) == 26);
        verificar("segunda arrecadação no mesmo ano devolve -1", g.arrecadarImpostos(c) == -1);
    }
    private static void testarConstrucoes() {
        System.out.println("-- Construções --");
        Cidade c = novaCidade(); Construcoes obra = new Construcoes();
        c.removerServos(150);
        verificar("recrutar 100 soldados com só 50 servos é recusado", !obra.recrutarSoldados(c, 100));
        verificar("e NÃO debita florins pela metade", c.getFlorins() == 1000);
        verificar("construir 10 dá certo", obra.construir(c, 10));
        verificar("e custa 200 florins", c.getFlorins() == 800);
    }
    private static void testarJogadorENobreza() {
        System.out.println("-- Jogador e Nobreza --");
        Cidade c = novaCidade(); Jogador j = c.getDono(); Nobreza n = new Nobreza();
        verificar("jogador começa como Cavaleiro", j.getTitulo().equals("Cavaleiro"));
        verificar("prestígio inicial é 300 (200 servos + 10000/100 terras)", n.calcularPrestigio(c) == 300);
        verificar("com prestígio 300 não sobe de título", !n.verificarPromocao(c));
        c.adicionarConstrucoes(50);
        verificar("com 50 construções sobe para Barão", n.verificarPromocao(c) && j.getTitulo().equals("Barão"));
        verificar("sobe no máximo um título por chamada", j.getTitulo().equals("Barão"));
        j.promover(); j.promover(); j.promover(); j.promover();
        verificar("promover para além de Rei/Rainha é recusado", j.ehMonarca() && !j.promover());
    }
    private static void testarPartida() {
        System.out.println("-- Partida --");
        Partida p = new Partida(); Nobreza n = new Nobreza();
        Cidade bolonha = new Cidade("Bolonha", new Jogador("Ana"));
        Cidade milano = new Cidade("Milano", new Jogador("Bia"));
        p.adicionarCidade(bolonha); p.adicionarCidade(milano);
        verificar("primeiro turno é de Bolonha", p.getCidadeAtual() == bolonha);
        verificar("passar turno de Bolonha NÃO fecha o ano", !p.passarTurno());
        verificar("agora é a vez de Milano", p.getCidadeAtual() == milano);
        verificar("passar turno de Milano fecha o ano", p.passarTurno());
        p.encerrarAno(n);
        verificar("encerrarAno avança todas as cidades", bolonha.getAnoAtual() == 1401 && milano.getAnoAtual() == 1401);
        verificar("partida ainda não terminou em 1401", !p.terminou());
        milano.adicionarConstrucoes(50);
        verificar("vencedor parcial é Milano (título mais alto)", p.determinarVencedor(n) == milano);
        verificar("quinta cidade é recusada (máximo 4)", p.adicionarCidade(new Cidade("Veneza", new Jogador("C")))
                && p.adicionarCidade(new Cidade("Florença", new Jogador("D")))
                && !p.adicionarCidade(new Cidade("Roma", new Jogador("E"))));
    }
}
