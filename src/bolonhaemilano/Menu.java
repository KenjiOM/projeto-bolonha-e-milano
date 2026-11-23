package bolonhaemilano;

import java.util.List;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private boolean emExecucao;
    private Partida partida;
    private Jogador jogador;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;
    private Governo governo;
    private Construcoes construcoes;
    private final Nobreza nobreza = new Nobreza();

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
        mercado = new Mercado();
        agricultura = new Agricultura();
        governo = new Governo();
        construcoes = new Construcoes();
    }

    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            processarOpcao(lerOpcao());
        }
        scanner.close();
    }

    private void exibirOpcoes() {
        System.out.println("==========================================");
        System.out.println("             BOLONHA E MILANO");
        System.out.println("  Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Mercado");
        System.out.println("4 - Agricultura");
        System.out.println("5 - Governo");
        System.out.println("6 - Construcoes");
        System.out.println("7 - Encerrar turno");
        System.out.println("8 - Ver classificação");
        System.out.println("9 - Sobre o jogo");
        System.out.println("0 - Sair");
        if (cidade != null) System.out.println("Vez de: " + cidade.getDono().getNome() + " — " + cidade.getNome());
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    private int lerInteiro() {
        String linha = scanner.nextLine();
        try { return Integer.parseInt(linha.trim()); }
        catch (NumberFormatException e) { return -1; }
    }
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
            case 0:
                emExecucao = false;
                System.out.println("\nSaindo de Bolonha e Milano. Ate a proxima!");
                break;
            default:
                System.out.println("\nOpcao invalida. Digite uma opcao do menu.\n");
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
        boolean noMercado = true;
        while (noMercado) {
            System.out.println("\n============== MERCADO ==============");
            System.out.println("Florins: " + cidade.getFlorins());
            System.out.println("Graos: " + cidade.getGraos()
                    + " | Preco/unidade: " + mercado.getPrecoGrao());
            System.out.println("Terras: " + cidade.getTerras()
                    + " hectares | Preco/hectare: " + mercado.getPrecoTerra());
            System.out.println("1 - Comprar graos");
            System.out.println("2 - Vender graos");
            System.out.println("3 - Comprar terras");
            System.out.println("4 - Vender terras");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opcao: ");
            switch (lerOpcao()) {
                case 1: operarMercado("comprar graos"); break;
                case 2: operarMercado("vender graos"); break;
                case 3: operarMercado("comprar terras"); break;
                case 4: operarMercado("vender terras"); break;
                case 0: noMercado = false; break;
                default: System.out.println("Opcao invalida.");
            }
        }
    }

    private void operarMercado(String operacao) {
        System.out.print("Digite a quantidade: ");
        int quantidade = lerOpcao();
        boolean sucesso;
        if (operacao.equals("comprar graos")) sucesso = mercado.comprarGraos(cidade, quantidade);
        else if (operacao.equals("vender graos")) sucesso = mercado.venderGraos(cidade, quantidade);
        else if (operacao.equals("comprar terras")) sucesso = mercado.comprarTerras(cidade, quantidade);
        else sucesso = mercado.venderTerras(cidade, quantidade);

        System.out.println(sucesso ? "Operacao realizada com sucesso."
                : "Operacao nao realizada. Verifique quantidade, saldo ou estoque.");
    }

    private void abrirAgricultura() {
        if (!exigirJogoEmAndamento()) return;
        boolean naAgricultura = true;
        while (naAgricultura) {
            System.out.println("\n=========== AGRICULTURA ===========");
            System.out.println("Terras: " + cidade.getTerras() + " hectares");
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Graos: " + cidade.getGraos());
            System.out.println("1 - Colher (processar safra)");
            System.out.println("2 - Distribuir alimentos");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opcao: ");
            switch (lerOpcao()) {
                case 1: colher(); break;
                case 2: distribuirAlimentos(); break;
                case 0: naAgricultura = false; break;
                default: System.out.println("Opcao invalida. Digite 0, 1 ou 2.");
            }
        }
    }

    private void colher() {
        int hectares = agricultura.processarColheita(cidade);
        if (hectares > 0) {
            System.out.println("Colheita processada! Hectares cultivados: " + hectares);
            System.out.println("Graos apos a colheita: " + cidade.getGraos());
        } else {
            System.out.println("Nao foi possivel colher. Verifique terras, servos e sementes.");
        }
    }

    private void distribuirAlimentos() {
        System.out.print("Digite o percentual de graos para distribuir (20 a 80): ");
        int percentual = lerOpcao();
        if (agricultura.distribuirAlimentos(cidade, percentual)) {
            System.out.println("Distribuicao realizada.");
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Graos restantes: " + cidade.getGraos());
        } else {
            System.out.println("Percentual invalido. Digite um valor entre 20 e 80.");
        }
    }


    private void abrirGoverno() {
        if (!exigirJogoEmAndamento()) return;
        boolean noGoverno = true;
        while (noGoverno) {
            System.out.println("\n=============== GOVERNO ===============");
            System.out.println("Florins: " + cidade.getFlorins());
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Taxa de imposto: " + cidade.getTaxaImposto() + "%");
            System.out.println("Nivel de justica: " + cidade.getNivelJustica());
            System.out.println("1 - Arrecadar impostos");
            System.out.println("2 - Definir taxa de imposto");
            System.out.println("3 - Definir nivel de justica");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opcao: ");
            switch (lerOpcao()) {
                case 1: arrecadarImpostos(); break;
                case 2: definirTaxaImposto(); break;
                case 3: definirJustica(); break;
                case 0: noGoverno = false; break;
                default: System.out.println("Opcao invalida. Digite de 0 a 3.");
            }
        }
    }

    private void arrecadarImpostos() {
        int valor = governo.arrecadarImpostos(cidade);
        System.out.println("Impostos arrecadados: " + valor + " florins.");
    }

    private void definirTaxaImposto() {
        System.out.print("Nova taxa de imposto (0 a 50): ");
        int taxa = lerOpcao();
        if (governo.definirTaxaImposto(cidade, taxa))
            System.out.println("Taxa de imposto atualizada para " + taxa + "%.");
        else System.out.println("Taxa invalida. Informe um valor entre 0 e 50.");
    }

    private void definirJustica() {
        System.out.print("Nivel de justica (Justa, Moderada ou Severa): ");
        String nivel = scanner.nextLine().trim();
        if (governo.definirJustica(cidade, nivel))
            System.out.println("Nivel de justica atualizado para " + cidade.getNivelJustica() + ".");
        else System.out.println("Nivel invalido. Escolha Justa, Moderada ou Severa.");
    }

    private void abrirConstrucoes() {
        if (!exigirJogoEmAndamento()) return;
        boolean nasConstrucoes = true;
        while (nasConstrucoes) {
            System.out.println("\n============= CONSTRUCOES =============");
            System.out.println("Florins: " + cidade.getFlorins());
            System.out.println("Servos: " + cidade.getServos());
            System.out.println("Soldados: " + cidade.getSoldados());
            System.out.println("Construcoes: " + cidade.getConstrucoes());
            System.out.println("1 - Recrutar soldados (" + construcoes.getPrecoSoldado() + " florins + 1 servo cada)");
            System.out.println("2 - Construir (" + construcoes.getPrecoConstrucao() + " florins cada)");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opcao: ");
            switch (lerOpcao()) {
                case 1: recrutarSoldados(); break;
                case 2: construir(); break;
                case 0: nasConstrucoes = false; break;
                default: System.out.println("Opcao invalida. Digite 0, 1 ou 2.");
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

    private void recrutarSoldados() {
        System.out.print("Quantidade de soldados para recrutar: ");
        int quantidade = lerOpcao();
        if (construcoes.recrutarSoldados(cidade, quantidade))
            System.out.println("Soldados recrutados com sucesso!");
        else System.out.println("Nao foi possivel recrutar. Verifique quantidade, florins e servos.");
    }

    private void construir() {
        System.out.print("Quantidade de construcoes: ");
        int quantidade = lerOpcao();
        if (construcoes.construir(cidade, quantidade))
            System.out.println("Construcoes realizadas com sucesso!");
        else System.out.println("Nao foi possivel construir. Verifique quantidade e florins.");
    }

    private void exibirSobre() {
        System.out.println("\n--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica medieval.");
        System.out.println("Governe sua cidade, administre recursos, negocie no mercado");
        System.out.println("cuide da agricultura, arrecade impostos e desenvolva sua defesa e infraestrutura.\n");
    }
}
