package bolonhaemilano;

import java.util.List;
import java.util.Scanner;

/** Interface textual do jogo e coordenação dos turnos. */
public class Menu {
    private final Scanner scanner = new Scanner(System.in);
    private boolean emExecucao = true;
    private Partida partida;
    private Cidade cidade;
    private final Mercado mercado = new Mercado();
    private final Agricultura agricultura = new Agricultura();
    private final Governo governo = new Governo();
    private final Construcoes construcoes = new Construcoes();
    private final Nobreza nobreza = new Nobreza();

    public void executar() {
        while (emExecucao) { exibirOpcoes(); processarOpcao(lerOpcao()); }
    }
    private void exibirOpcoes() {
        System.out.println("\n==========================================");
        System.out.println("             BOLONHA E MILANO");
        System.out.println("  Estratégia econômica medieval | Ano " + (partida == null ? "—" : partida.getAnoAtual()));
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construções");
        System.out.println("7 - Encerrar turno");
        System.out.println("8 - Ver classificação");
        System.out.println("9 - Sobre o jogo");
        System.out.println("0 - Sair");
        if (cidade != null) System.out.println("Vez de: " + cidade.getDono().getNome() + " — " + cidade.getNome());
        System.out.print("Escolha uma opção: ");
    }
    private int lerInteiro() {
        String linha = scanner.nextLine();
        try { return Integer.parseInt(linha.trim()); }
        catch (NumberFormatException e) { return -1; }
    }
    private int lerOpcao() { return lerInteiro(); }
    private int lerQuantidade(String mensagem) { System.out.print(mensagem); return lerInteiro(); }
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1: iniciarNovoJogo(); break;
            case 2: exibirStatus(); break;
            case 3: abrirMercado(); break;
            case 4: abrirAgricultura(); break;
            case 5: abrirGoverno(); break;
            case 6: abrirConstrucoes(); break;
            case 7: encerrarTurno(); break;
            case 8: exibirClassificacao(); break;
            case 9: exibirSobre(); break;
            case 0: emExecucao = false; System.out.println("Até a próxima!"); break;
            default: System.out.println("Opção inválida.");
        }
    }
    private void iniciarNovoJogo() {
        int quantidade = lerQuantidade("Quantidade de jogadores (1 a 4; inválido = 2): ");
        if (quantidade < 1 || quantidade > Partida.MAXIMO_JOGADORES) quantidade = 2;
        Partida novaPartida = new Partida();
        for (int i = 1; i <= quantidade; i++) {
            System.out.print("Nome do jogador " + i + ": ");
            String nome = scanner.nextLine().trim();
            while (nome.isEmpty()) { System.out.print("Nome não pode ficar vazio. Digite novamente: "); nome = scanner.nextLine().trim(); }
            System.out.print("Nome da cidade de " + nome + ": ");
            String nomeCidade = scanner.nextLine().trim();
            while (nomeCidade.isEmpty()) { System.out.print("Cidade não pode ficar vazia. Digite novamente: "); nomeCidade = scanner.nextLine().trim(); }
            Jogador jogador = new Jogador(nome);
            novaPartida.adicionarCidade(new Cidade(nomeCidade, jogador));
        }
        partida = novaPartida;
        cidade = partida.getCidadeAtual();
        System.out.println("\nPartida iniciada! Ano 1400. Começa " + cidade.getDono().getNome() + " em " + cidade.getNome() + ".");
        cidade.exibirStatus();
    }
    private boolean exigirJogoEmAndamento() {
        if (partida == null) { System.out.println("Nenhum jogo em andamento. Escolha 'Novo jogo' primeiro."); return false; }
        return true;
    }
    private void exibirStatus() { if (exigirJogoEmAndamento()) cidade.exibirStatus(); }

    private void abrirMercado() {
        if (!exigirJogoEmAndamento()) return;
        boolean ativo = true;
        while (ativo) {
            System.out.println("\n--- MERCADO | " + cidade.getNome() + " ---");
            System.out.println("Florins: " + cidade.getFlorins() + " | Grãos: " + cidade.getGraos() + " | Terras: " + cidade.getTerras());
            System.out.println("1 Comprar grãos (" + mercado.getPrecoGrao() + ")  2 Vender grãos");
            System.out.println("3 Comprar terras (" + mercado.getPrecoTerra() + ")  4 Vender terras  0 Voltar");
            switch (lerQuantidade("Opção: ")) {
                case 1: operarMercado(1); break; case 2: operarMercado(2); break;
                case 3: operarMercado(3); break; case 4: operarMercado(4); break;
                case 0: ativo = false; break; default: System.out.println("Opção inválida.");
            }
        }
    }
    private void operarMercado(int operacao) {
        int q = lerQuantidade("Quantidade: ");
        boolean ok = operacao == 1 ? mercado.comprarGraos(cidade, q)
                : operacao == 2 ? mercado.venderGraos(cidade, q)
                : operacao == 3 ? mercado.comprarTerras(cidade, q) : mercado.venderTerras(cidade, q);
        System.out.println(ok ? "Operação realizada." : "Operação recusada. Confira quantidade e saldo/estoque.");
    }
    private void abrirAgricultura() {
        if (!exigirJogoEmAndamento()) return;
        boolean ativo = true;
        while (ativo) {
            System.out.println("\n--- AGRICULTURA ---  Grãos: " + cidade.getGraos() + " | Servos: " + cidade.getServos());
            System.out.println("1 Colher  2 Distribuir alimentos  0 Voltar");
            switch (lerQuantidade("Opção: ")) {
                case 1: int hectares = agricultura.processarColheita(cidade);
                    if (hectares == -1) System.out.println("A colheita deste ano já foi realizada.");
                    else System.out.println(hectares > 0 ? "Colheita realizada em " + hectares + " hectares." : "Não foi possível colher."); break;
                case 2: int percentual = lerQuantidade("Percentual de grãos para distribuir (20 a 80): ");
                    System.out.println(agricultura.distribuirAlimentos(cidade, percentual) ? "Alimentos distribuídos." : "Percentual inválido."); break;
                case 0: ativo = false; break; default: System.out.println("Opção inválida.");
            }
        }
    }
    private void abrirGoverno() {
        if (!exigirJogoEmAndamento()) return;
        boolean ativo = true;
        while (ativo) {
            System.out.println("\n--- GOVERNO --- Florins: " + cidade.getFlorins() + " | Imposto: " + cidade.getTaxaImposto() + "% | Justiça: " + cidade.getNivelJustica());
            System.out.println("1 Arrecadar impostos  2 Definir taxa  3 Definir justiça  0 Voltar");
            switch (lerQuantidade("Opção: ")) {
                case 1: int valor = governo.arrecadarImpostos(cidade);
                    System.out.println(valor == -1 ? "Impostos já arrecadados neste ano." : "Arrecadados " + valor + " florins."); break;
                case 2: int taxa = lerQuantidade("Nova taxa (0 a 50): ");
                    System.out.println(governo.definirTaxaImposto(cidade, taxa) ? "Taxa atualizada." : "Taxa inválida."); break;
                case 3: System.out.print("Justiça (Justa, Moderada ou Severa): ");
                    System.out.println(governo.definirJustica(cidade, scanner.nextLine()) ? "Justiça atualizada." : "Nível inválido."); break;
                case 0: ativo = false; break; default: System.out.println("Opção inválida.");
            }
        }
    }
    private void abrirConstrucoes() {
        if (!exigirJogoEmAndamento()) return;
        boolean ativo = true;
        while (ativo) {
            System.out.println("\n--- CONSTRUÇÕES --- Florins: " + cidade.getFlorins() + " | Servos: " + cidade.getServos());
            System.out.println("1 Recrutar soldados (5 florins + 1 servo cada)  2 Construir (20 florins cada)  0 Voltar");
            switch (lerQuantidade("Opção: ")) {
                case 1: int soldados = lerQuantidade("Quantidade: ");
                    System.out.println(construcoes.recrutarSoldados(cidade, soldados) ? "Soldados recrutados." : "Recrutamento recusado."); break;
                case 2: int obras = lerQuantidade("Quantidade: ");
                    System.out.println(construcoes.construir(cidade, obras) ? "Construções concluídas." : "Construção recusada."); break;
                case 0: ativo = false; break; default: System.out.println("Opção inválida.");
            }
        }
    }
    private void encerrarTurno() {
        if (!exigirJogoEmAndamento()) return;
        String jogadorAtual = cidade.getDono().getNome();
        boolean fimDoAno = partida.passarTurno();
        if (fimDoAno) {
            List<Cidade> promovidas = partida.encerrarAno(nobreza);
            System.out.println("\nAno encerrado. Novo ano: " + partida.getAnoAtual());
            for (Cidade promovida : promovidas)
                System.out.println("PROMOÇÃO: " + promovida.getDono().getNome() + " de " + promovida.getNome() + " agora é " + promovida.getDono().getTitulo() + "!");
            exibirClassificacao();
            if (partida.terminou()) { encerrarPartida(); return; }
        }
        cidade = partida.getCidadeAtual();
        System.out.println("Turno de " + jogadorAtual + " encerrado. Agora é a vez de " + cidade.getDono().getNome() + " (" + cidade.getNome() + ").");
    }
    private void exibirClassificacao() {
        if (!exigirJogoEmAndamento()) return;
        System.out.println("\n========== CLASSIFICAÇÃO | ANO " + partida.getAnoAtual() + " ==========");
        for (Cidade c : partida.getCidades()) {
            Jogador j = c.getDono(); int prestigio = nobreza.calcularPrestigio(c);
            int proximo = nobreza.prestigioParaProximoTitulo(j);
            System.out.println(c.getNome() + " — " + j.getNome() + " | " + j.getTitulo()
                    + " | Prestígio: " + prestigio + (proximo < 0 ? " | título máximo" : " | faltam: " + Math.max(0, proximo - prestigio)));
        }
    }
    private void encerrarPartida() {
        Cidade vencedor = partida.determinarVencedor(nobreza);
        System.out.println("\n========== FIM DE JOGO ==========");
        System.out.println("Vencedor: " + vencedor.getDono().getNome() + " de " + vencedor.getNome()
                + " — " + vencedor.getDono().getTitulo() + " (prestígio " + nobreza.calcularPrestigio(vencedor) + ").");
        partida = null; cidade = null;
    }
    private void exibirSobre() {
        System.out.println("\nBolonha e Milano: estratégia econômica medieval para 1 a 4 jogadores.");
        System.out.println("Administre recursos, cultive, tribute, construa e dispute prestígio até 1411 ou até surgir um Rei/Rainha.");
    }
}
