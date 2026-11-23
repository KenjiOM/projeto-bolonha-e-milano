package bolonhaemilano;

/**
 * Mercado que negocia recursos sem manter uma referência permanente à cidade.
 */
public class Mercado {
    private static final int PRECO_GRAO = 2;
    private static final int PRECO_TERRA = 5;

    public int getPrecoGrao() {
        return PRECO_GRAO;
    }

    public int getPrecoTerra() {
        return PRECO_TERRA;
    }

    public boolean comprarGraos(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        long custoLongo = (long) quantidade * PRECO_GRAO;
        if (custoLongo > Integer.MAX_VALUE) {
            return false;
        }

        int custo = (int) custoLongo;
        if (!cidade.removerFlorins(custo)) {
            return false;
        }

        cidade.adicionarGraos(quantidade);
        return true;
    }

    public boolean venderGraos(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        long receitaLonga = (long) quantidade * PRECO_GRAO;
        if (receitaLonga > Integer.MAX_VALUE || !cidade.removerGraos(quantidade)) {
            return false;
        }

        cidade.adicionarFlorins((int) receitaLonga);
        return true;
    }

    public boolean comprarTerras(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        long custoLongo = (long) quantidade * PRECO_TERRA;
        if (custoLongo > Integer.MAX_VALUE) {
            return false;
        }

        int custo = (int) custoLongo;
        if (!cidade.removerFlorins(custo)) {
            return false;
        }

        cidade.adicionarTerras(quantidade);
        return true;
    }

    public boolean venderTerras(Cidade cidade, int quantidade) {
        if (cidade == null || quantidade <= 0) {
            return false;
        }

        long receitaLonga = (long) quantidade * PRECO_TERRA;
        if (receitaLonga > Integer.MAX_VALUE || !cidade.removerTerras(quantidade)) {
            return false;
        }

        cidade.adicionarFlorins((int) receitaLonga);
        return true;
    }
}
