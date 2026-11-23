package bolonhaemilano;

/**
 * Representa a cidade governada pelo jogador e seus recursos econômicos.
 */
public class Cidade {
    private final String nome;
    private int anoAtual;
    private final Jogador dono;
    private int florins;
    private int terras;
    private int graos;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome;
        this.dono = dono;
        this.anoAtual = 1400;
        this.florins = 1000;
        this.terras = 10000;
        this.graos = 500;
    }

    public String getNome() {
        return nome;
    }

    public int getAnoAtual() {
        return anoAtual;
    }

    public Jogador getDono() {
        return dono;
    }

    public int getFlorins() {
        return florins;
    }

    public int getTerras() {
        return terras;
    }

    public int getGraos() {
        return graos;
    }

    public void avancarAno() {
        anoAtual++;
    }

    public void adicionarFlorins(int quantidade) {
        if (quantidade > 0 && florins <= Integer.MAX_VALUE - quantidade) {
            florins += quantidade;
        }
    }

    public boolean removerFlorins(int quantidade) {
        if (quantidade <= 0 || quantidade > florins) {
            return false;
        }
        florins -= quantidade;
        return true;
    }

    public void adicionarTerras(int quantidade) {
        if (quantidade > 0 && terras <= Integer.MAX_VALUE - quantidade) {
            terras += quantidade;
        }
    }

    public boolean removerTerras(int quantidade) {
        if (quantidade <= 0 || quantidade > terras) {
            return false;
        }
        terras -= quantidade;
        return true;
    }

    public void adicionarGraos(int quantidade) {
        if (quantidade > 0 && graos <= Integer.MAX_VALUE - quantidade) {
            graos += quantidade;
        }
    }

    public boolean removerGraos(int quantidade) {
        if (quantidade <= 0 || quantidade > graos) {
            return false;
        }
        graos -= quantidade;
        return true;
    }

    public void exibirStatus() {
        System.out.println("\n========== STATUS DA CIDADE ==========");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + dono.getTitulo() + " " + dono.getNome());
        System.out.println("Ano atual: " + anoAtual);
        System.out.println("Florins: " + florins);
        System.out.println("Terras: " + terras + " hectares");
        System.out.println("Grãos: " + graos + " unidades");
        System.out.println("======================================\n");
    }
}
