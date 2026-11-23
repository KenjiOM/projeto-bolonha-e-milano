package bolonhaemilano;

/** Regras de arrecadacao e configuracao do governo. */
public class Governo {
    private static final int TAXA_MAXIMA = 50;
    private static final double MULTIPLICADOR_JUSTA = 0.8;
    private static final double MULTIPLICADOR_MODERADA = 1.0;
    private static final double MULTIPLICADOR_SEVERA = 1.3;

    public int arrecadarImpostos(Cidade cidade) {
        if (cidade == null) return 0;
        int base = (int) ((long) cidade.getServos() * cidade.getTaxaImposto() / 100);
        double multiplicador = multiplicadorPara(cidade.getNivelJustica());
        int arrecadado = (int) (base * multiplicador);
        cidade.adicionarFlorins(arrecadado);
        return arrecadado;
    }

    public boolean definirTaxaImposto(Cidade cidade, int taxa) {
        if (cidade == null || taxa < 0 || taxa > TAXA_MAXIMA) return false;
        cidade.setTaxaImposto(taxa);
        return true;
    }

    public boolean definirJustica(Cidade cidade, String nivel) {
        if (cidade == null || nivel == null) return false;
        String nivelNormalizado = nivel.trim();
        if (!nivelNormalizado.equalsIgnoreCase("Justa")
                && !nivelNormalizado.equalsIgnoreCase("Moderada")
                && !nivelNormalizado.equalsIgnoreCase("Severa")) return false;
        if (nivelNormalizado.equalsIgnoreCase("Justa")) nivelNormalizado = "Justa";
        else if (nivelNormalizado.equalsIgnoreCase("Moderada")) nivelNormalizado = "Moderada";
        else nivelNormalizado = "Severa";
        cidade.setNivelJustica(nivelNormalizado);
        return true;
    }

    private double multiplicadorPara(String nivel) {
        if ("Justa".equalsIgnoreCase(nivel)) return MULTIPLICADOR_JUSTA;
        if ("Severa".equalsIgnoreCase(nivel)) return MULTIPLICADOR_SEVERA;
        return MULTIPLICADOR_MODERADA;
    }
}
