package bolonhaemilano;

/** Estado econômico e administrativo de uma cidade. */
public class Cidade {
    private final String nome;
    private int anoAtual;
    private final Jogador dono;
    private int florins, terras, graos, servos, taxaImposto, soldados, construcoes;
    private String nivelJustica;
    private boolean colheitaFeita, impostosArrecadados;

    public Cidade(String nome, Jogador dono) {
        this.nome = nome == null || nome.trim().isEmpty() ? "Cidade" : nome.trim();
        this.dono = dono;
        anoAtual = 1400; florins = 1000; terras = 10000; graos = 500; servos = 200;
        taxaImposto = 10; nivelJustica = "Moderada"; soldados = 0; construcoes = 0;
        colheitaFeita = false; impostosArrecadados = false;
    }
    public String getNome() { return nome; }
    public int getAnoAtual() { return anoAtual; }
    public Jogador getDono() { return dono; }
    public int getFlorins() { return florins; }
    public int getTerras() { return terras; }
    public int getGraos() { return graos; }
    public int getServos() { return servos; }
    public int getTaxaImposto() { return taxaImposto; }
    public void setTaxaImposto(int v) { taxaImposto = v; }
    public String getNivelJustica() { return nivelJustica; }
    public void setNivelJustica(String v) { nivelJustica = v; }
    public int getSoldados() { return soldados; }
    public int getConstrucoes() { return construcoes; }
    public boolean isColheitaFeita() { return colheitaFeita; }
    public void marcarColheitaFeita() { colheitaFeita = true; }
    public boolean isImpostosArrecadados() { return impostosArrecadados; }
    public void marcarImpostosArrecadados() { impostosArrecadados = true; }
    public void avancarAno() { anoAtual++; colheitaFeita = false; impostosArrecadados = false; }

    public void adicionarFlorins(int q) { if (q > 0 && (long) florins + q <= Integer.MAX_VALUE) florins += q; }
    public boolean removerFlorins(int q) { if (q <= 0 || q > florins) return false; florins -= q; return true; }
    public void adicionarTerras(int q) { if (q > 0 && (long) terras + q <= Integer.MAX_VALUE) terras += q; }
    public boolean removerTerras(int q) { if (q <= 0 || q > terras) return false; terras -= q; return true; }
    public void adicionarGraos(int q) { if (q > 0 && (long) graos + q <= Integer.MAX_VALUE) graos += q; }
    public boolean removerGraos(int q) { if (q <= 0 || q > graos) return false; graos -= q; return true; }
    public void adicionarServos(int q) { if (q > 0 && (long) servos + q <= Integer.MAX_VALUE) servos += q; }
    public boolean removerServos(int q) { if (q <= 0 || q > servos) return false; servos -= q; return true; }
    public void adicionarSoldados(int q) { if (q > 0 && (long) soldados + q <= Integer.MAX_VALUE) soldados += q; }
    public boolean removerSoldados(int q) { if (q <= 0 || q > soldados) return false; soldados -= q; return true; }
    public void adicionarConstrucoes(int q) { if (q > 0 && (long) construcoes + q <= Integer.MAX_VALUE) construcoes += q; }
    public boolean removerConstrucoes(int q) { if (q <= 0 || q > construcoes) return false; construcoes -= q; return true; }

    public void exibirStatus() {
        System.out.println("\n========== STATUS DA CIDADE ==========");
        System.out.println("Cidade: " + nome);
        System.out.println("Governante: " + (dono == null ? "Sem governante" : dono.getTitulo() + " " + dono.getNome()));
        System.out.println("Ano atual: " + anoAtual + " | Colheita: " + (colheitaFeita ? "realizada" : "pendente")
                + " | Impostos: " + (impostosArrecadados ? "arrecadados" : "pendentes"));
        System.out.println("Florins: " + florins + " | Terras: " + terras + " hectares | Grãos: " + graos);
        System.out.println("Servos: " + servos + " | Imposto: " + taxaImposto + "% | Justiça: " + nivelJustica);
        System.out.println("Soldados: " + soldados + " | Construções: " + construcoes);
        System.out.println("======================================\n");
    }
}
