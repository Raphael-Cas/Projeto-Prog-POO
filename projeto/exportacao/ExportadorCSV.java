package projeto.exportacao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import projeto.modelo.Artilharia;
import projeto.modelo.Aviao;
import projeto.modelo.Barco;
import projeto.modelo.Logistica;
import projeto.modelo.Soldado;
import projeto.modelo.Tanque;

// formato: tipo;codigo;dano;vitalidade;custo;ativo
public class ExportadorCSV {
    public static String exportar(Logistica logistica, String caminho) {
        int total = 0;
        try (PrintWriter escritor = new PrintWriter(new FileWriter(caminho))) {
            escritor.println("tipo;codigo;dano;vitalidade;custo;ativo");
            for (Soldado s : logistica.getSoldados()) {
                escritor.println("1;" + s.getCodigo() + ";" + s.getDano() + ";" + s.getVitalidade()
                        + ";" + s.getCusto() + ";" + s.isAtivo());
                total++;
            }
            for (Tanque t : logistica.getTanques()) {
                escritor.println("2;" + t.getCodigo() + ";" + t.getDano() + ";" + t.getVitalidade()
                        + ";" + t.getCusto() + ";" + t.isAtivo());
                total++;
            }
            for (Aviao a : logistica.getAvioes()) {
                escritor.println("3;" + a.getCodigo() + ";" + a.getDano() + ";" + a.getVitalidade()
                        + ";" + a.getCusto() + ";" + a.isAtivo());
                total++;
            }
            for (Barco b : logistica.getBarcos()) {
                escritor.println("4;" + b.getCodigo() + ";" + b.getDano() + ";" + b.getVitalidade()
                        + ";" + b.getCusto() + ";" + b.isAtivo());
                total++;
            }
            for (Artilharia a : logistica.getArtilharias()) {
                escritor.println("5;" + a.getCodigo() + ";" + a.getDano() + ";" + a.getVitalidade()
                        + ";" + a.getCusto() + ";" + a.isAtivo());
                total++;
            }
        } catch (IOException e) {
            return "ERRO ao gravar o arquivo: " + e.getMessage();
        }
        return "OK: " + total + " unidades exportadas para " + caminho;
    }
}
