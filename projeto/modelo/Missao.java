package projeto.modelo;

import java.util.ArrayList;

public class Missao {
    public static final String PLANEJADA = "PLANEJADA";
    public static final String SUCESSO = "SUCESSO";
    public static final String FRACASSO = "FRACASSO";

    private int codigo;
    private String nome;
    private double dificuldade;
    private double recompensa;
    private double municaoNecessaria;
    private String status;
    private String resultado;

    private ArrayList<Soldado> soldados = new ArrayList<>();
    private ArrayList<Tanque> tanques = new ArrayList<>();
    private ArrayList<Aviao> avioes = new ArrayList<>();
    private ArrayList<Barco> barcos = new ArrayList<>();
    private ArrayList<Artilharia> artilharias = new ArrayList<>();

    public Missao(int codigo, String nome, double dificuldade, double recompensa, double municaoNecessaria) {
        this.codigo = codigo;
        this.nome = nome;
        this.dificuldade = dificuldade;
        this.recompensa = recompensa;
        this.municaoNecessaria = municaoNecessaria;
        this.status = PLANEJADA;
        this.resultado = "ainda nao executada";
    }

    public int getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public double getDificuldade() { return dificuldade; }
    public double getRecompensa() { return recompensa; }
    public double getMunicaoNecessaria() { return municaoNecessaria; }
    public String getStatus() { return status; }
    public String getResultado() { return resultado; }

    public String adicionarSoldado(Soldado s) {
        if (!status.equals(PLANEJADA)) return "ERRO: a missao nao esta mais planejada.";
        if (!s.statusSoldado()) return "ERRO: soldado desativado.";
        if (s.isAlocado()) return "ERRO: soldado esta na tripulacao de um veiculo (designe o veiculo).";
        if (s.isEmMissao()) return "ERRO: soldado ja esta designado a uma missao.";
        soldados.add(s);
        s.setEmMissao(true);
        return "OK: soldado " + s.getCodigo() + " designado a missao " + codigo + ".";
    }

    public String adicionarTanque(Tanque t) {
        if (!status.equals(PLANEJADA)) return "ERRO: a missao nao esta mais planejada.";
        if (!t.statusTanque()) return "ERRO: tanque desativado.";
        if (t.isEmMissao()) return "ERRO: tanque ja esta designado a uma missao.";
        tanques.add(t);
        t.setEmMissao(true);
        return "OK: tanque " + t.getCodigo() + " designado a missao " + codigo + ".";
    }

    public String adicionarAviao(Aviao a) {
        if (!status.equals(PLANEJADA)) return "ERRO: a missao nao esta mais planejada.";
        if (!a.statusAviao()) return "ERRO: aviao desativado.";
        if (a.isEmMissao()) return "ERRO: aviao ja esta designado a uma missao.";
        avioes.add(a);
        a.setEmMissao(true);
        return "OK: aviao " + a.getCodigo() + " designado a missao " + codigo + ".";
    }

    public String adicionarBarco(Barco b) {
        if (!status.equals(PLANEJADA)) return "ERRO: a missao nao esta mais planejada.";
        if (!b.statusBarco()) return "ERRO: barco desativado.";
        if (b.isEmMissao()) return "ERRO: barco ja esta designado a uma missao.";
        barcos.add(b);
        b.setEmMissao(true);
        return "OK: barco " + b.getCodigo() + " designado a missao " + codigo + ".";
    }

    public String adicionarArtilharia(Artilharia a) {
        if (!status.equals(PLANEJADA)) return "ERRO: a missao nao esta mais planejada.";
        if (!a.statusArtilharia()) return "ERRO: artilharia desativada.";
        if (a.isEmMissao()) return "ERRO: artilharia ja esta designada a uma missao.";
        artilharias.add(a);
        a.setEmMissao(true);
        return "OK: artilharia " + a.getCodigo() + " designada a missao " + codigo + ".";
    }

    public int totalUnidades() {
        return soldados.size() + tanques.size() + avioes.size() + barcos.size() + artilharias.size();
    }

    public int totalUnidadesAtivas() {
        int total = 0;
        for (Soldado s : soldados) if (s.statusSoldado()) total++;
        for (Tanque t : tanques) if (t.statusTanque()) total++;
        for (Aviao a : avioes) if (a.statusAviao()) total++;
        for (Barco b : barcos) if (b.statusBarco()) total++;
        for (Artilharia a : artilharias) if (a.statusArtilharia()) total++;
        return total;
    }

    public double calcularPoder() {
        double poder = 0;
        for (Soldado s : soldados) if (s.statusSoldado()) poder = poder + s.getPoderEmCombate();
        for (Tanque t : tanques) if (t.statusTanque()) poder = poder + t.getPoderEmCombate();
        for (Aviao a : avioes) if (a.statusAviao()) poder = poder + a.getPoderEmCombate();
        for (Barco b : barcos) if (b.statusBarco()) poder = poder + b.getPoderEmCombate();
        for (Artilharia a : artilharias) if (a.statusArtilharia()) poder = poder + a.getPoderEmCombate();
        return poder;
    }

    // vence se o poder for maior ou igual a dificuldade
    public boolean executar() {
        double poder = calcularPoder();
        boolean venceu = poder >= dificuldade;

        double danoTotal;
        if (venceu) {
            danoTotal = dificuldade * 0.2;
        } else {
            danoTotal = dificuldade * 0.5;
        }
        double danoPorUnidade = danoTotal / totalUnidadesAtivas();

        for (Soldado s : soldados) if (s.statusSoldado()) s.receberDano(danoPorUnidade);
        for (Tanque t : tanques) if (t.statusTanque()) t.receberDano(danoPorUnidade);
        for (Aviao a : avioes) if (a.statusAviao()) a.receberDano(danoPorUnidade);
        for (Barco b : barcos) if (b.statusBarco()) b.receberDano(danoPorUnidade);
        for (Artilharia a : artilharias) if (a.statusArtilharia()) a.receberDano(danoPorUnidade);

        if (venceu) {
            status = SUCESSO;
        } else {
            status = FRACASSO;
        }
        resultado = "poder " + poder + " x dificuldade " + dificuldade
                + " | dano sofrido por unidade: " + danoPorUnidade;
        liberarUnidades();
        return venceu;
    }

    private void liberarUnidades() {
        for (Soldado s : soldados) s.setEmMissao(false);
        for (Tanque t : tanques) t.setEmMissao(false);
        for (Aviao a : avioes) a.setEmMissao(false);
        for (Barco b : barcos) b.setEmMissao(false);
        for (Artilharia a : artilharias) a.setEmMissao(false);
    }

    public String toString() {
        return "Missao " + codigo + " (" + nome + ") | dificuldade " + dificuldade
                + " | recompensa " + recompensa + " | municao " + municaoNecessaria
                + " | unidades " + totalUnidades() + " | " + status + " | " + resultado;
    }
}
