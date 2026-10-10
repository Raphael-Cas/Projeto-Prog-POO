package projeto.modelo;

public class Soldado {
    private int codigo;
    private double dano;
    private double vitalidade;
    private double custo;
    private boolean ativo;
    private boolean alocado;
    private boolean emMissao;

    public Soldado(double dano, double vitalidade, int codigo, double custo) {
        this.dano = dano;
        this.vitalidade = vitalidade;
        this.codigo = codigo;
        this.custo = custo;
        this.ativo = true;
        this.alocado = false;
        this.emMissao = false;
    }

    public double getDano() { return dano; }
    public void setDano(double dano) { this.dano = dano; }
    public double getVitalidade() { return vitalidade; }
    public void setVitalidade(double vitalidade) { this.vitalidade = vitalidade; }
    public int getCodigo() { return codigo; }
    public double getCusto() { return custo; }
    public void setCusto(double custo) { this.custo = custo; }
    public boolean isAtivo() { return ativo; }
    public boolean isAlocado() { return alocado; }
    public void setAlocado(boolean alocado) { this.alocado = alocado; }
    public boolean isEmMissao() { return emMissao; }
    public void setEmMissao(boolean emMissao) { this.emMissao = emMissao; }

    public boolean statusSoldado() {
        return ativo && vitalidade > 0;
    }

    public double getPoderEmCombate() {
        return dano;
    }

    public void receberDano(double valor) {
        vitalidade = vitalidade - valor;
        if (vitalidade <= 0) {
            vitalidade = 0;
            desativar();
        }
    }

    public void desativar() {
        ativo = false;
        alocado = false;
        emMissao = false;
    }

    public String toString() {
        String situacao = statusSoldado() ? "ATIVO" : "DESATIVADO";
        String lotacao = alocado ? "alocado" : "livre";
        return "Soldado " + codigo + " | dano " + dano + " | vitalidade " + vitalidade
                + " | custo " + custo + " | " + situacao + " | " + lotacao;
    }
}
