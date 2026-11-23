package bolonhaemilano;

public class Agricultura {
    private static final int HECTARES_POR_SERVO = 5;
    private static final int RENDIMENTO_POR_HECTARE = 2;
    private static final int SERVOS_POR_GRAO = 5;
    private static final int CRESCIMENTO_PERCENTUAL = 5;
    private static final int PERCENTUAL_MINIMO = 20;
    private static final int PERCENTUAL_MAXIMO = 80;

    public int processarColheita(Cidade cidade) {
        if (cidade == null) {
            return 0;
        }

        int hectares = Math.min(cidade.getTerras(),
                cidade.getServos() * HECTARES_POR_SERVO);
        int sementes = hectares / 2;

        // Se faltarem sementes, reduz a área cultivada ao estoque disponível.
        if (sementes > cidade.getGraos()) {
            sementes = cidade.getGraos();
            hectares = sementes * 2;
        }

        if (hectares <= 0) {
            return 0;
        }

        if (sementes > 0 && !cidade.removerGraos(sementes)) {
            return 0;
        }

        cidade.adicionarGraos(hectares * RENDIMENTO_POR_HECTARE);
        return hectares;
    }

    public boolean distribuirAlimentos(Cidade cidade, int percentual) {
        if (cidade == null || percentual < PERCENTUAL_MINIMO
                || percentual > PERCENTUAL_MAXIMO) {
            return false;
        }

        int alimentoDistribuido =
                (int) ((long) cidade.getGraos() * percentual / 100);
        cidade.removerGraos(alimentoDistribuido);

        int necessidade = cidade.getServos() / SERVOS_POR_GRAO;
        if (alimentoDistribuido >= necessidade) {
            int crescimento = (int) ((long) cidade.getServos()
                    * CRESCIMENTO_PERCENTUAL / 100);
            cidade.adicionarServos(crescimento);
        } else {
            int deficit = necessidade - alimentoDistribuido;
            cidade.removerServos(Math.min(deficit, cidade.getServos()));
        }
        return true;
    }
}
