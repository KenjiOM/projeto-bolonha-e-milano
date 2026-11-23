package bolonhaemilano;

import java.util.Scanner;

/**
 * Menu principal do jogo Bolonha e Milano.
 * Responsável por exibir opções, ler entradas e processar escolhas.
 */
public class Menu {

    private final Scanner scanner;
    private boolean emExecucao;

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
        System.out.println("2 - Sobre o jogo");
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
                System.out.println("\nNovo jogo: funcionalidade disponível na Etapa 2.\n");
                break;
            case 2:
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
