package projeto.modelo;

import java.util.ArrayList;

public class Barco {
    public static final int CAPACIDADE = 10;
    public static final double FATOR_SEM_TRIPULACAO = 0.5;

    private int codigo;
    private double dano;
    private double vitalidade;
    private double custo;
    private boolean ativo;
    private boolean emMissao;
    private ArrayList<Soldado> tripulacao = new ArrayList<>();

    public Barco(double dano, double vitalidade, int codigo, double custo) {
        this.dano = dano;
        this.vitalidade = vitalidade;
        this.codigo = codigo;
        this.custo = custo;
        this.ativo = true;
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
    public boolean isEmMissao() { return emMissao; }
    public void setEmMissao(boolean emMissao) { this.emMissao = emMissao; }

    public boolean statusBarco() {
        return ativo && vitalidade > 0;
    }

    public int contagemTripulacao() {
        return tripulacao.size();
    }

    public void removerSoldado(Soldado soldado) {
        tripulacao.remove(soldado);
    }

    // sem tripulacao o dano vale so a metade
    public double getPoderEmCombate() {
        if (tripulacao.size() == 0) {
            return dano * FATOR_SEM_TRIPULACAO;
        }
        return dano;
    }

    public String adicionarSoldado(Soldado soldado) {
        if (!statusBarco()) {
            return "ERRO: barco esta desativado.";
        }
        if (!soldado.statusSoldado()) {
            return "ERRO: soldado desativado.";
        }
        if (soldado.isAlocado()) {
            return "ERRO: soldado ja esta alocado em outra unidade.";
        }
        if (soldado.isEmMissao()) {
            return "ERRO: soldado ja esta designado a uma missao.";
        }
        if (tripulacao.size() >= CAPACIDADE) {
            return "ERRO: tripulacao cheia (maximo " + CAPACIDADE + ").";
        }
        tripulacao.add(soldado);
        soldado.setAlocado(true);
        return "OK: soldado " + soldado.getCodigo() + " alocado em Barco " + codigo + ".";
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
        emMissao = false;
        for (Soldado s : tripulacao) {
            s.setAlocado(false);
        }
        tripulacao.clear();
    }

    public String toString() {
        String situacao = statusBarco() ? "ATIVO" : "DESATIVADO";
        return "Barco " + codigo + " | dano " + dano + " | vitalidade " + vitalidade
                + " | custo " + custo + " | " + situacao
                + " | tripulacao " + tripulacao.size() + "/" + CAPACIDADE;
    }
}
