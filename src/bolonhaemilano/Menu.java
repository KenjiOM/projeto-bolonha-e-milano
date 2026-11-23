package bolonhaemilano;

import java.util.Scanner;

/**
 * Menu principal do jogo Bolonha e Milano.
 * Responsável por exibir opções, ler entradas e processar escolhas.
 */
public class Menu {

    private final Scanner scanner;
    private boolean emExecucao;
    private Jogador jogador;
    private Cidade cidade;

    public Menu() {
        this.scanner = new Scanner(System.in);
        this.emExecucao = true;
    }

    /**
     * Executa o laço principal do menu até o jogador escolher sair.
     */
    public void executar() {
        while (emExecucao) {
            exibirOpcoes();
            int opcao = lerOpcao();
            processarOpcao(opcao);
        }

        scanner.close();
    }

    /**
     * Exibe o título e as três opções do menu.
     */
    private void exibirOpcoes() {
        System.out.println("==========================================");
        System.out.println("             BOLONHA E MILANO");
        System.out.println("  Um jogo de estrategia economica medieval");
        System.out.println("==========================================");
        System.out.println("1 - Novo jogo");
        System.out.println("2 - Ver status da cidade");
        System.out.println("3 - Sobre o jogo");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    /**
     * Lê uma linha e tenta convertê-la para inteiro.
     * Retorna -1 se a entrada não for um número inteiro válido.
     */
    private int lerOpcao() {
        String entrada = scanner.nextLine();

        try {
            return Integer.parseInt(entrada.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Processa a opção escolhida pelo jogador.
     */
    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                iniciarNovoJogo();
                break;
            case 2:
                exibirStatus();
                break;
            case 3:
                exibirSobre();
                break;
            case 0:
                emExecucao = false;
                System.out.println("\nSaindo de Bolonha e Milano. Ate a proxima!");
                break;
            default:
                System.out.println("\nOpcao invalida. Digite 1, 2 ou 0.\n");
                break;
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
    private void exibirStatus() {
        if (cidade == null) {
            System.out.println("\nNenhum jogo foi iniciado. Escolha 'Novo jogo' primeiro.\n");
            return;
        }
        cidade.exibirStatus();
    }

    /**
     * Exibe uma breve descrição do jogo.
     */
    private void exibirSobre() {
        System.out.println("\n--- SOBRE O JOGO ---");
        System.out.println("Bolonha e Milano e um jogo de estrategia economica medieval.");
        System.out.println("O projeto sera desenvolvido em etapas, com jogadores, cidades,");
        System.out.println("economia, agricultura, impostos, construcoes e soldados.");
        System.out.println("Objetivo desta etapa: preparar a estrutura inicial do jogo.\n");
    }
}
