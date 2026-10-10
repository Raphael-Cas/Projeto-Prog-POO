package projeto.modelo;

import java.util.ArrayList;

// tipos: 1 soldado, 2 tanque, 3 aviao, 4 barco, 5 artilharia
public class Logistica {
    public static final double PRECO_MUNICAO = 10;

    private double dinheiro;
    private double municao;
    private double numTropas;
    private double numVeiculos;

    private ArrayList<Soldado> soldados = new ArrayList<>();
    private ArrayList<Tanque> tanques = new ArrayList<>();
    private ArrayList<Aviao> avioes = new ArrayList<>();
    private ArrayList<Barco> barcos = new ArrayList<>();
    private ArrayList<Artilharia> artilharias = new ArrayList<>();
    private ArrayList<Missao> missoes = new ArrayList<>();

    public Logistica(double dinheiro, double municao) {
        this.dinheiro = dinheiro;
        this.municao = municao;
        this.numTropas = 0;
        this.numVeiculos = 0;
    }

    public double getDinheiro() { return dinheiro; }
    public double getMunicao() { return municao; }
    public double getNumTropas() { return numTropas; }
    public double getNumVeiculos() { return numVeiculos; }
    public ArrayList<Soldado> getSoldados() { return soldados; }
    public ArrayList<Tanque> getTanques() { return tanques; }
    public ArrayList<Aviao> getAvioes() { return avioes; }
    public ArrayList<Barco> getBarcos() { return barcos; }
    public ArrayList<Artilharia> getArtilharias() { return artilharias; }

    public int contagemSoldados() {
        int total = 0;
        for (Soldado s : soldados) {
            if (s.statusSoldado()) total++;
        }
        return total;
    }

    public int contagemTanques() {
        int total = 0;
        for (Tanque t : tanques) {
            if (t.statusTanque()) total++;
        }
        return total;
    }

    public int contagemAvioes() {
        int total = 0;
        for (Aviao a : avioes) {
            if (a.statusAviao()) total++;
        }
        return total;
    }

    public int contagemBarcos() {
        int total = 0;
        for (Barco b : barcos) {
            if (b.statusBarco()) total++;
        }
        return total;
    }

    public int contagemArtilharias() {
        int total = 0;
        for (Artilharia a : artilharias) {
            if (a.statusArtilharia()) total++;
        }
        return total;
    }

    private void atualizarContadores() {
        numTropas = contagemSoldados();
        numVeiculos = contagemTanques() + contagemAvioes() + contagemBarcos() + contagemArtilharias();
    }

    // quando o soldado e desativado ele sai da tripulacao
    private void tirarSoldadoDasTripulacoes(Soldado soldado) {
        for (Tanque t : tanques) t.removerSoldado(soldado);
        for (Aviao a : avioes) a.removerSoldado(soldado);
        for (Barco b : barcos) b.removerSoldado(soldado);
        for (Artilharia a : artilharias) a.removerSoldado(soldado);
    }

    public String nomeTipo(int tipo) {
        if (tipo == 1) return "Soldado";
        if (tipo == 2) return "Tanque";
        if (tipo == 3) return "Aviao";
        if (tipo == 4) return "Barco";
        if (tipo == 5) return "Artilharia";
        return "Desconhecido";
    }

    public Soldado buscarSoldado(int codigo) {
        for (Soldado s : soldados) {
            if (s.getCodigo() == codigo) return s;
        }
        return null;
    }

    public Tanque buscarTanque(int codigo) {
        for (Tanque t : tanques) {
            if (t.getCodigo() == codigo) return t;
        }
        return null;
    }

    public Aviao buscarAviao(int codigo) {
        for (Aviao a : avioes) {
            if (a.getCodigo() == codigo) return a;
        }
        return null;
    }

    public Barco buscarBarco(int codigo) {
        for (Barco b : barcos) {
            if (b.getCodigo() == codigo) return b;
        }
        return null;
    }

    public Artilharia buscarArtilharia(int codigo) {
        for (Artilharia a : artilharias) {
            if (a.getCodigo() == codigo) return a;
        }
        return null;
    }

    public Missao buscarMissao(int codigo) {
        for (Missao m : missoes) {
            if (m.getCodigo() == codigo) return m;
        }
        return null;
    }

    // desconta o custo do dinheiro e nao deixa repetir o codigo
    public String cadastrar(int tipo, int codigo, double dano, double vitalidade, double custo) {
        if (tipo < 1 || tipo > 5) return "ERRO: tipo invalido.";
        if (codigo <= 0 || dano < 0 || vitalidade <= 0 || custo < 0) {
            return "ERRO: valores invalidos.";
        }
        if (consultar(tipo, codigo) != null) {
            return "ERRO: ja existe " + nomeTipo(tipo) + " com o codigo " + codigo + ".";
        }
        if (custo > dinheiro) {
            return "ERRO: dinheiro insuficiente.";
        }

        if (tipo == 1) soldados.add(new Soldado(dano, vitalidade, codigo, custo));
        if (tipo == 2) tanques.add(new Tanque(dano, vitalidade, codigo, custo));
        if (tipo == 3) avioes.add(new Aviao(dano, vitalidade, codigo, custo));
        if (tipo == 4) barcos.add(new Barco(dano, vitalidade, codigo, custo));
        if (tipo == 5) artilharias.add(new Artilharia(dano, vitalidade, codigo, custo));

        dinheiro = dinheiro - custo;
        atualizarContadores();
        return "OK: " + nomeTipo(tipo) + " " + codigo + " cadastrado. Dinheiro restante: " + dinheiro;
    }

    // devolve o texto da unidade ou null se nao achar
    public String consultar(int tipo, int codigo) {
        if (tipo == 1) {
            Soldado s = buscarSoldado(codigo);
            if (s != null) return s.toString();
        }
        if (tipo == 2) {
            Tanque t = buscarTanque(codigo);
            if (t != null) return t.toString();
        }
        if (tipo == 3) {
            Aviao a = buscarAviao(codigo);
            if (a != null) return a.toString();
        }
        if (tipo == 4) {
            Barco b = buscarBarco(codigo);
            if (b != null) return b.toString();
        }
        if (tipo == 5) {
            Artilharia a = buscarArtilharia(codigo);
            if (a != null) return a.toString();
        }
        return null;
    }

    // editar so funciona para soldado e tanque
    public String editar(int tipo, int codigo, double dano, double vitalidade, double custo) {
        if (tipo != 1 && tipo != 2) return "ERRO: so da para editar soldado e tanque.";
        if (dano < 0 || vitalidade <= 0 || custo < 0) {
            return "ERRO: valores invalidos.";
        }
        if (consultar(tipo, codigo) == null) {
            return "ERRO: " + nomeTipo(tipo) + " " + codigo + " nao encontrado.";
        }

        if (tipo == 1) {
            Soldado s = buscarSoldado(codigo);
            s.setDano(dano);
            s.setVitalidade(vitalidade);
            s.setCusto(custo);
        }
        if (tipo == 2) {
            Tanque t = buscarTanque(codigo);
            t.setDano(dano);
            t.setVitalidade(vitalidade);
            t.setCusto(custo);
        }
        return "OK: " + nomeTipo(tipo) + " " + codigo + " editado.";
    }

    public void listar(int tipo) {
        System.out.println("--- Lista de " + nomeTipo(tipo) + " ---");
        int quantidade = 0;
        if (tipo == 1) {
            for (Soldado s : soldados) { System.out.println(s); quantidade++; }
        }
        if (tipo == 2) {
            for (Tanque t : tanques) { System.out.println(t); quantidade++; }
        }
        if (tipo == 3) {
            for (Aviao a : avioes) { System.out.println(a); quantidade++; }
        }
        if (tipo == 4) {
            for (Barco b : barcos) { System.out.println(b); quantidade++; }
        }
        if (tipo == 5) {
            for (Artilharia a : artilharias) { System.out.println(a); quantidade++; }
        }
        if (quantidade == 0) {
            System.out.println("Nenhum registro.");
        }
    }

    // so marca como inativo, o registro continua na lista
    public String desativar(int tipo, int codigo) {
        if (consultar(tipo, codigo) == null) {
            return "ERRO: " + nomeTipo(tipo) + " " + codigo + " nao encontrado.";
        }
        if (tipo == 1) {
            Soldado s = buscarSoldado(codigo);
            s.desativar();
            tirarSoldadoDasTripulacoes(s);
        }
        if (tipo == 2) buscarTanque(codigo).desativar();
        if (tipo == 3) buscarAviao(codigo).desativar();
        if (tipo == 4) buscarBarco(codigo).desativar();
        if (tipo == 5) buscarArtilharia(codigo).desativar();
        atualizarContadores();
        return "OK: " + nomeTipo(tipo) + " " + codigo + " desativado.";
    }

    // coloca um soldado na tripulacao de um veiculo (tipos 2 a 5)
    public String associarSoldado(int tipoVeiculo, int codigoVeiculo, int codigoSoldado) {
        Soldado soldado = buscarSoldado(codigoSoldado);
        if (soldado == null) {
            return "ERRO: soldado " + codigoSoldado + " nao encontrado.";
        }
        if (tipoVeiculo == 2) {
            Tanque t = buscarTanque(codigoVeiculo);
            if (t == null) return "ERRO: tanque nao encontrado.";
            return t.adicionarSoldado(soldado);
        }
        if (tipoVeiculo == 3) {
            Aviao a = buscarAviao(codigoVeiculo);
            if (a == null) return "ERRO: aviao nao encontrado.";
            return a.adicionarSoldado(soldado);
        }
        if (tipoVeiculo == 4) {
            Barco b = buscarBarco(codigoVeiculo);
            if (b == null) return "ERRO: barco nao encontrado.";
            return b.adicionarSoldado(soldado);
        }
        if (tipoVeiculo == 5) {
            Artilharia a = buscarArtilharia(codigoVeiculo);
            if (a == null) return "ERRO: artilharia nao encontrada.";
            return a.adicionarSoldado(soldado);
        }
        return "ERRO: tipo de veiculo invalido.";
    }

    // pesquisar so funciona para soldado e tanque
    public void pesquisarPorDano(int tipo, double danoMinimo) {
        System.out.println("--- " + nomeTipo(tipo) + " com dano >= " + danoMinimo + " ---");
        int encontrados = 0;
        if (tipo == 1) {
            for (Soldado s : soldados) {
                if (s.getDano() >= danoMinimo) { System.out.println(s); encontrados++; }
            }
        }
        if (tipo == 2) {
            for (Tanque t : tanques) {
                if (t.getDano() >= danoMinimo) { System.out.println(t); encontrados++; }
            }
        }
        if (encontrados == 0) {
            System.out.println("Nenhum registro encontrado.");
        }
    }

    // bubble sort pelo custo, so para soldado e tanque
    public void ordenarPorCusto(int tipo) {
        if (tipo == 1) {
            for (int i = 0; i < soldados.size() - 1; i++) {
                for (int j = 0; j < soldados.size() - 1 - i; j++) {
                    if (soldados.get(j).getCusto() > soldados.get(j + 1).getCusto()) {
                        Soldado aux = soldados.get(j);
                        soldados.set(j, soldados.get(j + 1));
                        soldados.set(j + 1, aux);
                    }
                }
            }
        }
        if (tipo == 2) {
            for (int i = 0; i < tanques.size() - 1; i++) {
                for (int j = 0; j < tanques.size() - 1 - i; j++) {
                    if (tanques.get(j).getCusto() > tanques.get(j + 1).getCusto()) {
                        Tanque aux = tanques.get(j);
                        tanques.set(j, tanques.get(j + 1));
                        tanques.set(j + 1, aux);
                    }
                }
            }
        }
        System.out.println("(ordenado por custo, do menor para o maior)");
        listar(tipo);
    }

    public String cadastrarMissao(int codigo, String nome, double dificuldade, double recompensa, double municaoNecessaria) {
        if (codigo <= 0) return "ERRO: o codigo deve ser maior que zero.";
        if (nome == null || nome.trim().isEmpty()) return "ERRO: a missao precisa de um nome.";
        if (dificuldade <= 0 || recompensa < 0 || municaoNecessaria < 0) {
            return "ERRO: valores invalidos.";
        }
        if (buscarMissao(codigo) != null) return "ERRO: ja existe missao com o codigo " + codigo + ".";
        missoes.add(new Missao(codigo, nome.trim(), dificuldade, recompensa, municaoNecessaria));
        return "OK: missao " + codigo + " cadastrada.";
    }

    // coloca uma unidade (de qualquer tipo) em uma missao planejada
    public String designarUnidade(int codigoMissao, int tipo, int codigoUnidade) {
        Missao m = buscarMissao(codigoMissao);
        if (m == null) return "ERRO: missao " + codigoMissao + " nao encontrada.";
        if (consultar(tipo, codigoUnidade) == null) {
            return "ERRO: " + nomeTipo(tipo) + " " + codigoUnidade + " nao encontrado.";
        }
        if (tipo == 1) return m.adicionarSoldado(buscarSoldado(codigoUnidade));
        if (tipo == 2) return m.adicionarTanque(buscarTanque(codigoUnidade));
        if (tipo == 3) return m.adicionarAviao(buscarAviao(codigoUnidade));
        if (tipo == 4) return m.adicionarBarco(buscarBarco(codigoUnidade));
        return m.adicionarArtilharia(buscarArtilharia(codigoUnidade));
    }

    // comprar municao gasta dinheiro
    public String comprarMunicao(double quantidade) {
        if (quantidade <= 0) return "ERRO: a quantidade deve ser maior que zero.";
        double preco = quantidade * PRECO_MUNICAO;
        if (preco > dinheiro) {
            return "ERRO: dinheiro insuficiente.";
        }
        dinheiro = dinheiro - preco;
        municao = municao + quantidade;
        return "OK: comprou " + quantidade + " de municao por " + preco
                + ". Municao: " + municao + " | Dinheiro: " + dinheiro;
    }

    // confere status, unidades e municao antes de executar
    public String executarMissao(int codigo) {
        Missao m = buscarMissao(codigo);
        if (m == null) return "ERRO: missao " + codigo + " nao encontrada.";
        if (!m.getStatus().equals(Missao.PLANEJADA)) return "ERRO: a missao nao esta planejada (" + m.getStatus() + ").";
        if (m.totalUnidadesAtivas() == 0) return "ERRO: a missao nao tem nenhuma unidade ativa.";
        if (m.getMunicaoNecessaria() > municao) {
            return "ERRO: municao insuficiente (precisa " + m.getMunicaoNecessaria() + ", tem " + municao + ").";
        }

        municao = municao - m.getMunicaoNecessaria();
        boolean venceu = m.executar();
        if (venceu) {
            dinheiro = dinheiro + m.getRecompensa();
        }
        atualizarContadores();

        String texto;
        if (venceu) {
            texto = "SUCESSO! Recompensa de " + m.getRecompensa() + " recebida.";
        } else {
            texto = "FRACASSO. Nenhuma recompensa.";
        }
        return texto + "\n" + m;
    }

    // lista de nomes e danos so das unidades ativas
    private void montarListaAtivas(ArrayList<String> nomes, ArrayList<Double> danos) {
        for (Soldado s : soldados) {
            if (s.statusSoldado()) { nomes.add("Soldado " + s.getCodigo()); danos.add(s.getDano()); }
        }
        for (Tanque t : tanques) {
            if (t.statusTanque()) { nomes.add("Tanque " + t.getCodigo()); danos.add(t.getDano()); }
        }
        for (Aviao a : avioes) {
            if (a.statusAviao()) { nomes.add("Aviao " + a.getCodigo()); danos.add(a.getDano()); }
        }
        for (Barco b : barcos) {
            if (b.statusBarco()) { nomes.add("Barco " + b.getCodigo()); danos.add(b.getDano()); }
        }
        for (Artilharia a : artilharias) {
            if (a.statusArtilharia()) { nomes.add("Artilharia " + a.getCodigo()); danos.add(a.getDano()); }
        }
    }

    public double calcularCustoTotal() {
        double total = 0;
        for (Soldado s : soldados) total = total + s.getCusto();
        for (Tanque t : tanques) total = total + t.getCusto();
        for (Aviao a : avioes) total = total + a.getCusto();
        for (Barco b : barcos) total = total + b.getCusto();
        for (Artilharia a : artilharias) total = total + a.getCusto();
        return total;
    }

    public double calcularMediaDano() {
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<Double> danos = new ArrayList<>();
        montarListaAtivas(nomes, danos);
        if (danos.size() == 0) return 0;
        double soma = 0;
        for (double d : danos) soma = soma + d;
        return soma / danos.size();
    }

    // porcentagem de missoes vencidas entre as que ja foram executadas
    public double calcularTaxaSucesso() {
        int sucessos = 0;
        int executadas = 0;
        for (Missao m : missoes) {
            if (m.getStatus().equals(Missao.SUCESSO)) { sucessos++; executadas++; }
            if (m.getStatus().equals(Missao.FRACASSO)) { executadas++; }
        }
        if (executadas == 0) return 0;
        return sucessos * 100.0 / executadas;
    }

    public void indicadores() {
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<Double> danos = new ArrayList<>();
        montarListaAtivas(nomes, danos);

        System.out.println("--- Indicadores ---");
        System.out.println("Custo total das unidades cadastradas: " + calcularCustoTotal());
        if (danos.size() == 0) {
            System.out.println("Media de dano: sem unidades ativas");
            System.out.println("Maior dano: sem unidades ativas");
        } else {
            int posMaior = 0;
            for (int i = 1; i < danos.size(); i++) {
                if (danos.get(i) > danos.get(posMaior)) posMaior = i;
            }
            System.out.println("Media de dano das unidades ativas: " + calcularMediaDano());
            System.out.println("Maior dano: " + danos.get(posMaior) + " (" + nomes.get(posMaior) + ")");
        }
        System.out.println("Taxa de sucesso das missoes: " + calcularTaxaSucesso() + "%");
    }

    // selection sort do maior dano para o menor, mostra os 5 primeiros
    public void ranking() {
        ArrayList<String> nomes = new ArrayList<>();
        ArrayList<Double> danos = new ArrayList<>();
        montarListaAtivas(nomes, danos);

        for (int i = 0; i < danos.size() - 1; i++) {
            int posMaior = i;
            for (int j = i + 1; j < danos.size(); j++) {
                if (danos.get(j) > danos.get(posMaior)) posMaior = j;
            }
            double auxDano = danos.get(i);
            danos.set(i, danos.get(posMaior));
            danos.set(posMaior, auxDano);
            String auxNome = nomes.get(i);
            nomes.set(i, nomes.get(posMaior));
            nomes.set(posMaior, auxNome);
        }

        System.out.println("--- Ranking: unidades com maior dano ---");
        if (danos.size() == 0) {
            System.out.println("Nenhuma unidade ativa.");
        }
        for (int i = 0; i < danos.size() && i < 5; i++) {
            System.out.println((i + 1) + "o lugar: " + nomes.get(i) + " (dano " + danos.get(i) + ")");
        }
    }

    public void relatorio() {
        atualizarContadores();
        System.out.println("");
        System.out.println("===== RELATORIO DE LOGISTICA =====");
        System.out.println("Dinheiro disponivel: " + dinheiro);
        System.out.println("Municao disponivel: " + municao);
        System.out.println("Total de tropas ativas: " + (int) numTropas);
        System.out.println("Total de veiculos ativos: " + (int) numVeiculos);
        System.out.println("  Soldados: " + contagemSoldados());
        System.out.println("  Tanques: " + contagemTanques());
        System.out.println("  Avioes: " + contagemAvioes());
        System.out.println("  Barcos: " + contagemBarcos());
        System.out.println("  Artilharias: " + contagemArtilharias());
        indicadores();
        System.out.println("==================================");
    }

    public void relatorioMissoes() {
        System.out.println("");
        System.out.println("===== RELATORIO DE MISSOES =====");
        for (Missao m : missoes) {
            System.out.println(m);
        }
        if (missoes.size() == 0) {
            System.out.println("Nenhuma missao cadastrada.");
        }
        System.out.println("================================");
    }
}
