/**
 * Atividade de POO - Loja de Consoles
 * Disciplina: GCVA
 * Aluno: Gustavo Zimmermann
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// PARTE 1 - Encapsulamento / PARTE 2 - Construtores
// Deixei nome, tipo e preco privados e só com get (não fiz set de propósito, porque se desse pra mudar depois de criado dava pra colocar um preço negativo por exemplo). O construtor obriga passar os três, então não tem mais como nascer um Console "pela metade".
class Console {

    private final String nome;
    private final String tipo;
    private final double preco;

    public Console(String nome, String tipo, double preco) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do console é obrigatório.");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("O tipo do console é obrigatório.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPreco() {
        return preco;
    }
}

// PARTE 3 - Interface e Composição
// Contrato que toda classe de console precisa seguir.
interface IConsole {
    void ligar();
    double calcularPreco();
    String getNome();
}

// Dados que todo console tem em comum (nome e preço base).
class DadosConsole {

    private final String nome;
    private final double precoBase;

    public DadosConsole(String nome, double precoBase) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do console é obrigatório.");
        }
        if (precoBase < 0) {
            throw new IllegalArgumentException("O preço base não pode ser negativo.");
        }
        this.nome = nome;
        this.precoBase = precoBase;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoBase() {
        return precoBase;
    }
}

// Nintendo tem um DadosConsole (composição) em vez de repetir nome/precoBase aqui dentro. Acréscimo de 10% no preço, mensagem própria no ligar().
class Nintendo implements IConsole {

    private static final double PERCENTUAL = 0.10;

    private final DadosConsole dados;

    public Nintendo(String nome, double precoBase) {
        this.dados = new DadosConsole(nome, precoBase);
    }

    @Override
    public String getNome() {
        return dados.getNome();
    }

    public double getPrecoBase() {
        return dados.getPrecoBase();
    }

    @Override
    public void ligar() {
        System.out.println("Nintendo ligado.");
    }

    @Override
    public double calcularPreco() {
        return getPrecoBase() * (1 + PERCENTUAL);
    }
}

// Mesma ideia da Nintendo, só que com 20% e o atributo dados protected, porque a PlaystationPortatil (parte 4) precisa acessar ele direto.
class Playstation implements IConsole {

    private static final double PERCENTUAL = 0.20;

    protected final DadosConsole dados;

    public Playstation(String nome, double precoBase) {
        this.dados = new DadosConsole(nome, precoBase);
    }

    @Override
    public String getNome() {
        return dados.getNome();
    }

    public double getPrecoBase() {
        return dados.getPrecoBase();
    }

    @Override
    public void ligar() {
        System.out.println("Playstation ligado.");
    }

    @Override
    public double calcularPreco() {
        return getPrecoBase() * (1 + PERCENTUAL);
    }
}

// PARTE 4 - Herança
// PlaystationPortatil extends Playstation e reaproveita tudo que já existe lá, só troca a mensagem do ligar() e o percentual do cálculo (15% em vez de 20%).
class PlaystationPortatil extends Playstation {

    private static final double PERCENTUAL_PORTATIL = 0.15;

    public PlaystationPortatil(String nome, double precoBase) {
        super(nome, precoBase);
    }

    @Override
    public void ligar() {
        System.out.println("Playstation Portátil ligado.");
    }

    @Override
    public double calcularPreco() {
        return dados.getPrecoBase() * (1 + PERCENTUAL_PORTATIL);
    }
}

// PARTE 5 - Polimorfismo e OCP
// Console novo (Xbox), criado sem mexer em nada da Loja - só implementa IConsole igual os outros.
class Xbox implements IConsole {

    private static final double PERCENTUAL = 0.18;

    private final DadosConsole dados;

    public Xbox(String nome, double precoBase) {
        this.dados = new DadosConsole(nome, precoBase);
    }

    @Override
    public String getNome() {
        return dados.getNome();
    }

    public double getPrecoBase() {
        return dados.getPrecoBase();
    }

    @Override
    public void ligar() {
        System.out.println("Xbox ligado.");
    }

    @Override
    public double calcularPreco() {
        return getPrecoBase() * (1 + PERCENTUAL);
    }
}

class Loja {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    // Recebe um IConsole e só chama os métodos da interface, sem if/else nem instanceof - cada console sabe fazer a própria versão de ligar() e calcularPreco().
    public void venderConsole(IConsole console) {
        console.ligar();
        double precoFinal = console.calcularPreco();
        System.out.println(String.format(PT_BR, "%s -> Preço final: R$ %.2f", console.getNome(), precoFinal));
    }

    public void venderVarios(List<IConsole> consoles) {
        for (IConsole console : consoles) {
            venderConsole(console);
        }
    }

    public double calcularFaturamentoTotal(List<IConsole> consoles) {
        double total = 0;
        for (IConsole console : consoles) {
            total += console.calcularPreco();
        }
        return total;
    }
}

public class Atividade_POO_Problema {

    private static final Locale PT_BR = Locale.forLanguageTag("pt-BR");

    public static void main(String[] args) {

        // Partes 1 e 2: criando os consoles com o construtor novo, sem atributo público nenhum.
        System.out.println("=== Partes 1 e 2: Console com atributos privados e construtor ===");
        Console nintendoBasico = new Console("Nintendo Switch", "nintendo", 2000);
        Console playstationBasico = new Console("Playstation 5", "playstation", 3000);
        Console portatilBasico = new Console("Playstation Portátil", "portatil", 2500);

        Console[] basicos = { nintendoBasico, playstationBasico, portatilBasico };
        for (Console c : basicos) {
            System.out.println(String.format(PT_BR, "%s (%s) -> R$ %.2f", c.getNome(), c.getTipo(), c.getPreco()));
        }

        // Partes 3 a 5: agora usando IConsole e polimorfismo de verdade.
        System.out.println();
        System.out.println("=== Partes 3 a 5: vendas com polimorfismo ===");

        List<IConsole> consoles = new ArrayList<>();
        consoles.add(new Nintendo("Nintendo Switch", 2000));
        consoles.add(new Playstation("Playstation 5", 3000));
        consoles.add(new PlaystationPortatil("Playstation Portátil", 2500));

        Loja loja = new Loja();

        loja.venderVarios(consoles);
        System.out.println(String.format(PT_BR, "Faturamento total: R$ %.2f", loja.calcularFaturamentoTotal(consoles)));

        // Adiciona o Xbox na lista e chama tudo de novo - repara que não mudei uma linha sequer da Loja pra isso funcionar.
        System.out.println();
        System.out.println("=== Adicionando o Xbox (Loja não foi alterada) ===");
        consoles.add(new Xbox("Xbox Series X", 3500));

        loja.venderVarios(consoles);
        System.out.println(String.format(PT_BR, "Faturamento total: R$ %.2f", loja.calcularFaturamentoTotal(consoles)));
    }
}

/*
 * Respostas das perguntas do roteiro:
 *
 * Parte 1 - o Main antigo (nintendo.nome = "...") não compila mais, porque os atributos viraram private e só a própria classe Console consegue mexer neles. Não fiz setters de propósito: se desse pra alterar o objeto depois de criado, dava pra colocar um preço negativo por exemplo, e o construtor já garante que ele nasce válido.
 *
 * Parte 2 - não dá mais pra criar um Console sem passar o preço. Tentando new Console("Nintendo Switch", "nintendo") o código nem compila, porque o único construtor que existe agora pede os três parâmetros. Antes isso viraria um bug silencioso (preço ficando 0.0), agora vira erro de compilação mesmo.
 *
 * Parte 3 - usei DadosConsole em vez de colocar nome/precoBase direto na Nintendo e na Playstation pra não repetir os mesmos atributos e getters nas duas classes. Se um dia precisar mudar alguma validação desses dados, mudo só num lugar. E como é composição (a classe "tem um" DadosConsole) e não herança, Nintendo e Playstation continuam livres pra ter cada uma sua própria lógica sem ficar presa numa hierarquia.
 *
 * Parte 4 - a PlaystationPortatil sobrescreve ligar() e calcularPreco(), mas isso não é problema porque ela continua cumprindo a promessa dos dois métodos, só muda como (mensagem diferente, percentual diferente). Já o jogarDisco() do material de SOLID lançava exception, ou seja, recusava fazer o que o método promete - aí sim quebra o LSP, porque quem espera poder chamar aquele método vai quebrar se receber a subclasse.
 *
 * Parte 5 - pra o Xbox funcionar não precisei mudar nada na Loja, só criei a classe implementando IConsole e adicionei na lista. Isso é justamente o OCP: o sistema fica aberto pra adicionar console novo, mas fechado pra alterar código que já funcionava - antes, com o if/else, cada tipo novo ia exigir mexer na Loja de novo.
 */