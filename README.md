# Projeto-Prog-POO
Este repositorio tem como objetivo mostrar um de nossos projetos em POO.

# Sistema de Logística Militar

Sistema em Java (terminal) para gerenciar as forças e os recursos de um exército fictício: soldados, tanques, aviões, barcos e artilharias, além de dinheiro, munição e missões.

## Integrantes

| Integrante | Função / parte do sistema |
|---|---|
| [Nome 1] | [ex.: classes de domínio (Soldado, Tanque, Aviao, Barco, Artilharia)] |
| [Nome 2] | [ex.: Logistica, regras de negócio e missões] |
| [Nome 3] | [ex.: menu (Main), exportação CSV e testes] |

> Preencher com os nomes e responsabilidades reais da equipe.

## Descrição do problema

Em um jogo de estratégia ou simulação militar (cenário fictício), o comandante precisa saber **quanto tem de cada unidade, quanto dinheiro e munição restam, quem está em cada veículo e se uma missão tem chance de dar certo** antes de enviá-la. Fazer isso de cabeça ou em papel gera erros: gastar mais do que se tem, colocar o mesmo soldado em dois lugares ou enviar unidades fracas para uma missão difícil.

O sistema resolve isso controlando os recursos e aplicando as regras automaticamente.

**Possíveis usuários:** comandante ou gestor de logística de um jogo de estratégia, jogador de simulação militar e instrutor que queira demonstrar planejamento de operações.

## Funcionalidades

- **Cadastrar, consultar, listar e desativar** soldados, tanques, aviões, barcos e artilharias.
- **Editar, pesquisar (por dano mínimo) e ordenar (por custo)** soldados e tanques.
- **Associar** soldados à tripulação de veículos e **designar** unidades a missões.
- **Comprar munição** com o dinheiro disponível.
- **Missões (processo completo):** cadastrar missão → designar unidades → executar → resultado (recompensa e dano às unidades).
- **Relatórios e indicadores:** custo total, média de dano, maior dano, ranking de dano, taxa de sucesso das missões, relatório geral e relatório de missões.
- **Exportar** as unidades para um arquivo CSV.

## Principais classes

| Classe | Pacote | Responsabilidade |
|---|---|---|
| `Soldado` | `projeto.modelo` | Soldado: dano, vitalidade, custo, situação (ativo/alocado/em missão) |
| `Tanque`, `Aviao`, `Barco`, `Artilharia` | `projeto.modelo` | Veículos e armas: mesmos atributos do soldado + **tripulação** (`ArrayList<Soldado>`) com capacidade própria |
| `Missao` | `projeto.modelo` | Guarda as unidades designadas (cinco `ArrayList`), calcula o poder, executa e registra o resultado |
| `Logistica` | `projeto.modelo` | Classe central: dinheiro, munição, listas de todas as unidades e missões, operações e relatórios |
| `ExportadorCSV` | `projeto.exportacao` | Exporta as unidades em CSV |
| `Main` | `projeto` | Menu interativo no terminal (não contém regras) |

**Coleções:** `Logistica` possui listas de `Soldado`, `Tanque`, `Aviao`, `Barco`, `Artilharia` e `Missao`; cada veículo possui `ArrayList<Soldado>`; `Missao` possui listas das unidades designadas.

## Regras de negócio

1. **Custo debitado:** cadastrar uma unidade desconta seu custo do dinheiro. Sem dinheiro suficiente, o cadastro é recusado.
2. **Tripulação limitada:** cada veículo tem capacidade máxima (tanque 4, avião 2, barco 10, artilharia 3). Um soldado só pode estar em uma unidade.
3. **Veículo sem tripulação opera com 50% do dano.**
4. **Desativação automática:** vitalidade zero desativa a unidade e libera a tripulação. Soldado desativado sai da tripulação.
5. **Missão exige munição:** a munição necessária é consumida ao executar; sem munição suficiente, a missão não executa.
6. **Resultado da missão:** vence se o poder total (soma do poder das unidades ativas) for maior ou igual à dificuldade. Vencendo, recebe a recompensa e as unidades sofrem 20% da dificuldade de dano (dividido entre elas); perdendo, sofrem 50% e não há recompensa.
7. **Sem dupla missão:** uma unidade não pode estar em duas missões planejadas ao mesmo tempo; fica livre depois de executada.
8. **Contadores automáticos:** total de tropas e veículos ativos é recalculado a cada mudança.
9. **Munição custa dinheiro:** cada unidade de munição custa 10. Sem dinheiro suficiente, a compra é recusada.

## Tecnologias utilizadas

- Java 17
- Maven (gerenciamento do projeto e dependências)
- **Tecnologia externa 1:** exportação de dados em **CSV**
- **Tecnologia externa 2:** testes automatizados com **JUnit 5** (testam as regras de negócio)

## Configuração

Requisitos: **JDK 17 ou superior** e **Maven 3.8+**.

```bash
git clone <url-do-repositorio>
cd Battlefield
mvn compile
```

No VS Code, instale a extensão *Extension Pack for Java* e abra a pasta `Battlefield`.

## Execução

Programa:

```bash
mvn compile exec:java
```

Testes:

```bash
mvn test
```

## Exemplos de utilização

Fluxo de uma missão no menu:

```
1  -> cadastrar tipo 2 (Tanque), codigo 1, dano 50, vitalidade 500, custo 1000
1  -> cadastrar tipo 1 (Soldado), codigo 1, dano 10, vitalidade 100, custo 200
6  -> associar o soldado 1 ao tanque 1
9  -> cadastrar missao 1 "Resgate", dificuldade 40, recompensa 500, municao 10
10 -> designar o tanque 1 a missao 1
11 -> executar a missao 1   (poder 50 >= 40: SUCESSO, +500 de dinheiro, -10 de municao)
12 -> comprar 20 de municao (gasta 200 de dinheiro)
14 -> ranking de dano
16 -> relatorio de missoes
17 -> exportar para unidades.csv
```

Formato do CSV (`tipo;codigo;dano;vitalidade;custo;ativo`; tipo 1=Soldado, 2=Tanque, 3=Aviao, 4=Barco, 5=Artilharia):

```
tipo;codigo;dano;vitalidade;custo;ativo
1;1;10.0;100.0;200.0;true
2;1;50.0;492.0;1000.0;true
```

## Uso de IA

| Ferramenta | Objetivo | Resumo do uso | Revisão pelo Dev |
|---|---|---|---|
| Claude Sonnet 5.5 | Código Base e Regras de Negócio | Apoiou a organização das classes em pacotes, a modelagem das regras de negócio (tripulação, missões, munição), a implementação das operações obrigatórias, da exportação CSV e dos testes JUnit. | [Confirmar/ajustar: ex.: testes manuais pelo menu, validação dos casos de borda, correção de bug na tripulação de soldados desativados e revisão do código para o nível da disciplina.] |

> A coluna "Revisão pelo Dev" deve descrever o que a equipe realmente revisou e testou. Acrescente linhas se outras ferramentas de IA tiverem sido usadas (ex.: para o README).

## Estrutura do projeto

```
Battlefield/
├── pom.xml
├── README.md
└── src/
    ├── main/java/projeto/
    │   ├── Main.java
    │   ├── modelo/        (Soldado, Tanque, Aviao, Barco, Artilharia, Missao, Logistica)
    │   └── exportacao/    (ExportadorCSV)
    └── test/java/projeto/
        ├── modelo/        (LogisticaTest)
        └── exportacao/    (ExportadorCSVTest)
```
