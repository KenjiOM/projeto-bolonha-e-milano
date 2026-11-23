package bolonhaemilano;

import java.util.Scanner;

public class Menu {
    private final Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;
    private Mercado mercado;
    private Agricultura agricultura;
    private Governo governo;

    public Menu() {
        scanner = new Scanner(System.in);
        emExecucao = true;
        mercado = new Mercado();
        agricultura = new Agricultura();
        governo = new Governo();
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
        System.out.println("6 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1: iniciarNovoJogo(); break;
            case 2: exibirStatus(); break;
            case 3: abrirMercado(); break;
            case 4: abrirAgricultura(); break;
            case 5: abrirGoverno(); break;
            case 6: exibirSobre(); break;
            case 0:
                emExecucao = false;
                System.out.println("\nSaindo de Bolonha e Milano. Ate a proxima!");
                break;
            default:
                System.out.println("\nOpcao invalida. Digite uma opcao do menu.\n");
        }
    }

    private void iniciarNovoJogo() {
        System.out.print("Digite o nome do jogador: ");
        String nomeJogador = scanner.nextLine().trim();
        while (nomeJogador.isEmpty()) {
            System.out.print("O nome nao pode ficar vazio. Digite o nome do jogador: ");
            nomeJogador = scanner.nextLine().trim();
        }
        System.out.print("Digite o nome da cidade: ");
        String nomeCidade = scanner.nextLine().trim();
        while (nomeCidade.isEmpty()) {
            System.out.print("O nome nao pode ficar vazio. Digite o nome da cidade: ");
            nomeCidade = scanner.nextLine().trim();
        }
        jogador = new Jogador(nomeJogador);
        cidade = new Cidade(nomeCidade, jogador);
        System.out.println("\nNovo jogo iniciado com sucesso!");
        cidade.exibirStatus();
    }

    private boolean verificarJogoIniciado() {
        if (cidade == null) {
            System.out.println("\nNenhum jogo foi iniciado. Escolha 'Novo jogo' primeiro.\n");
            return false;
        }
        return true;
    }

    private void exibirStatus() {
        if (verificarJogoIniciado()) cidade.exibirStatus();
    }

    private void abrirMercado() {
        if (!verificarJogoIniciado()) return;
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
        if (!verificarJogoIniciado()) return;
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
        if (!verificarJogoIniciado()) return;
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

    private void exibirSobre() {
        System.out.println("\n--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica medieval.");
        System.out.println("Governe sua cidade, administre recursos, negocie no mercado");
        System.out.println("e cuide da agricultura e da populacao.\n");
    }
}
