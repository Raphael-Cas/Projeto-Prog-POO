package projeto;

import java.util.Scanner;

import projeto.exportacao.ExportadorCSV;
import projeto.modelo.Logistica;

public class Main {
    static Scanner teclado = new Scanner(System.in);

    static String lerLinha(String texto) {
        System.out.print(texto);
        return teclado.nextLine().trim();
    }

    static int lerInt(String texto) {
        while (true) {
            try {
                return Integer.parseInt(lerLinha(texto));
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero inteiro.");
            }
        }
    }

    static double lerDouble(String texto) {
        while (true) {
            try {
                double valor = Double.parseDouble(lerLinha(texto).replace(",", "."));
                if (Double.isNaN(valor) || Double.isInfinite(valor)) {
                    System.out.println("Entrada invalida. Digite um numero comum.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Digite um numero.");
            }
        }
    }

    // pergunta o tipo ate a pessoa digitar um valor entre minimo e maximo
    static int lerTipo(int minimo, int maximo) {
        System.out.println("Tipo: 1-Soldado 2-Tanque 3-Aviao 4-Barco 5-Artilharia (aceita de "
                + minimo + " a " + maximo + ")");
        int tipo = lerInt("Escolha o tipo: ");
        while (tipo < minimo || tipo > maximo) {
            System.out.println("Tipo invalido.");
            tipo = lerInt("Escolha o tipo: ");
        }
        return tipo;
    }

    static void mostrarMenu() {
        System.out.println("");
        System.out.println("===== MENU =====");
        System.out.println("1 - Cadastrar unidade");
        System.out.println("2 - Consultar por codigo");
        System.out.println("3 - Editar (soldado ou tanque)");
        System.out.println("4 - Listar");
        System.out.println("5 - Desativar");
        System.out.println("6 - Associar soldado a um veiculo");
        System.out.println("7 - Pesquisar por dano minimo (soldado ou tanque)");
        System.out.println("8 - Ordenar por custo (soldado ou tanque)");
        System.out.println("9 - Cadastrar missao");
        System.out.println("10 - Designar unidade a uma missao");
        System.out.println("11 - Executar missao");
        System.out.println("12 - Comprar municao");
        System.out.println("13 - Indicadores");
        System.out.println("14 - Ranking de dano");
        System.out.println("15 - Relatorio geral");
        System.out.println("16 - Relatorio de missoes");
        System.out.println("17 - Exportar unidades para CSV");
        System.out.println("0 - Sair");
    }

    public static void main(String[] args) {
        Logistica logistica = new Logistica(100000, 500);
        int opcao = -1;

        while (opcao != 0) {
            mostrarMenu();
            opcao = lerInt("Opcao: ");

            if (opcao == 1) {
                int tipo = lerTipo(1, 5);
                int codigo = lerInt("Codigo: ");
                double dano = lerDouble("Dano: ");
                double vitalidade = lerDouble("Vitalidade: ");
                double custo = lerDouble("Custo: ");
                System.out.println(logistica.cadastrar(tipo, codigo, dano, vitalidade, custo));
            } else if (opcao == 2) {
                int tipo = lerTipo(1, 5);
                int codigo = lerInt("Codigo: ");
                String texto = logistica.consultar(tipo, codigo);
                if (texto == null) {
                    System.out.println("Nao encontrado.");
                } else {
                    System.out.println(texto);
                }
            } else if (opcao == 3) {
                int tipo = lerTipo(1, 2);
                int codigo = lerInt("Codigo: ");
                double dano = lerDouble("Novo dano: ");
                double vitalidade = lerDouble("Nova vitalidade: ");
                double custo = lerDouble("Novo custo: ");
                System.out.println(logistica.editar(tipo, codigo, dano, vitalidade, custo));
            } else if (opcao == 4) {
                logistica.listar(lerTipo(1, 5));
            } else if (opcao == 5) {
                int tipo = lerTipo(1, 5);
                int codigo = lerInt("Codigo: ");
                System.out.println(logistica.desativar(tipo, codigo));
            } else if (opcao == 6) {
                int tipo = lerTipo(2, 5);
                int codigoVeiculo = lerInt("Codigo do veiculo: ");
                int codigoSoldado = lerInt("Codigo do soldado: ");
                System.out.println(logistica.associarSoldado(tipo, codigoVeiculo, codigoSoldado));
            } else if (opcao == 7) {
                int tipo = lerTipo(1, 2);
                double minimo = lerDouble("Dano minimo: ");
                logistica.pesquisarPorDano(tipo, minimo);
            } else if (opcao == 8) {
                logistica.ordenarPorCusto(lerTipo(1, 2));
            } else if (opcao == 9) {
                int codigo = lerInt("Codigo da missao: ");
                String nome = lerLinha("Nome da missao: ");
                double dificuldade = lerDouble("Dificuldade (poder minimo): ");
                double recompensa = lerDouble("Recompensa em dinheiro: ");
                double municao = lerDouble("Municao necessaria: ");
                System.out.println(logistica.cadastrarMissao(codigo, nome, dificuldade, recompensa, municao));
            } else if (opcao == 10) {
                int codigoMissao = lerInt("Codigo da missao: ");
                int tipo = lerTipo(1, 5);
                int codigoUnidade = lerInt("Codigo da unidade: ");
                System.out.println(logistica.designarUnidade(codigoMissao, tipo, codigoUnidade));
            } else if (opcao == 11) {
                int codigo = lerInt("Codigo da missao: ");
                System.out.println(logistica.executarMissao(codigo));
            } else if (opcao == 12) {
                double quantidade = lerDouble("Quantidade de municao (preco " + Logistica.PRECO_MUNICAO + " cada): ");
                System.out.println(logistica.comprarMunicao(quantidade));
            } else if (opcao == 13) {
                logistica.indicadores();
            } else if (opcao == 14) {
                logistica.ranking();
            } else if (opcao == 15) {
                logistica.relatorio();
            } else if (opcao == 16) {
                logistica.relatorioMissoes();
            } else if (opcao == 17) {
                String caminho = lerLinha("Nome do arquivo (ex: unidades.csv): ");
                System.out.println(ExportadorCSV.exportar(logistica, caminho));
            } else if (opcao == 0) {
                System.out.println("Encerrando.");
            } else {
                System.out.println("Opcao invalida.");
            }
        }
    }
}
