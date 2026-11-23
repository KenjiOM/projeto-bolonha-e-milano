package bolonhaemilano;

/** Governante de uma cidade; o título só evolui por promoção. */
public class Jogador {
    private static final String[] TITULOS = {
        "Cavaleiro", "Barão", "Conde", "Marquês", "Duque", "Rei/Rainha"
    };
    private final String nome;
    private int nivelTitulo;

    public Jogador(String nome) {
        this.nome = nome == null || nome.trim().isEmpty() ? "Sem nome" : nome.trim();
        this.nivelTitulo = 0;
    }
    public String getNome() { return nome; }
    public String getTitulo() { return TITULOS[nivelTitulo]; }
    public int getNivelTitulo() { return nivelTitulo; }
    public boolean promover() {
        if (ehMonarca()) return false;
        nivelTitulo++;
        return true;
    }
    public boolean ehMonarca() { return nivelTitulo == TITULOS.length - 1; }
}
