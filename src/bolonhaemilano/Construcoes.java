package bolonhaemilano;

/** Regras para recrutamento de soldados e construção de edifícios. */
public class Construcoes {
    private static final int PRECO_SOLDADO = 5;
    private static final int SERVOS_POR_SOLDADO = 1;
    private static final int PRECO_CONSTRUCAO = 20;

    public boolean recrutarSoldados(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) return false;

        long custoLongo = (long) quantidade * PRECO_SOLDADO;
        long servosNecessariosLongo = (long) quantidade * SERVOS_POR_SOLDADO;
        if (custoLongo > Integer.MAX_VALUE || servosNecessariosLongo > Integer.MAX_VALUE
                || (long) cidade.getSoldados() + quantidade > Integer.MAX_VALUE) return false;

        int custo = (int) custoLongo;
        int servosNecessarios = (int) servosNecessariosLongo;

        // Valida os dois recursos antes de alterar qualquer um deles.
        if (cidade.getFlorins() < custo || cidade.getServos() < servosNecessarios) return false;

        cidade.removerFlorins(custo);
        cidade.removerServos(servosNecessarios);
        cidade.adicionarSoldados(quantidade);
        return true;
    }

    public boolean construir(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) return false;
        long custoLongo = (long) quantidade * PRECO_CONSTRUCAO;
        if (custoLongo > Integer.MAX_VALUE
                || (long) cidade.getConstrucoes() + quantidade > Integer.MAX_VALUE) return false;
        if (!cidade.removerFlorins((int) custoLongo)) return false;
        cidade.adicionarConstrucoes(quantidade);
        return true;
    }

    public int getPrecoSoldado() { return PRECO_SOLDADO; }
    public int getPrecoConstrucao() { return PRECO_CONSTRUCAO; }
}
