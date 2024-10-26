/*
***************************
Sistema Operacional: Windows 10 - 64 Bits
Versão Da Linguagem: Java 1.8.0_411
Autor: Leonardo Oliveira Almeida da Cruz
Componente Curricular: EXA863 - MI - PROGRAMAÇÃO
Concluido em: 14/09/2024
Declaro que este código foi elaborado por mim de forma
individual e não contém nenhum trecho de código de outro
colega ou de outro autor, tais como provindos de livros e
apostilas, e páginas ou documentos eletrônicos da Internet.
Qualquer trecho de código de outra autoria que não a minha
está destacado com uma citação para o autor e a fonte do código,
e estou ciente que estes trechos não serão considerados para fins de avaliação.
******************************
*/

package Classes;
import java.util.*;

public class Evento {
    private String nome;
    private String descricao;
    private Date data;
    private HashMap<String,Boolean> assentos;
    private List<Avaliacao> avaliacoes;
    private boolean status;
    

    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descr) {
        this.descricao = descricao;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;}
    public HashMap<String, Boolean> getAssentos() {
        return assentos;
    }
    public void setAssentos(HashMap<String, Boolean> assentos) {
        this.assentos = assentos;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }
    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    // Construtor
    public Evento(String nome, String descricao, Date data) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.assentos = new HashMap<>();
        this.status = true;
        this.avaliacoes = new ArrayList<>();
    }

    public boolean isAtivo() {
        Date dataAtual = new Date();

        if (dataAtual.after(data) || dataAtual.equals(data)) {
            this.status = false; // Se a data atual for maior ou igual à data do evento, retorna falso;
            return false;
        } else {
            this.status = true; // Se a data atual for menor retorna true;
            return true;
        }
    }

    // Metodo para adicionar um assento
    public void adicionarAssento(String nomeAssento) {
        assentos.put(nomeAssento, true); // O assento é adicionado sempre como disponível.
    }
    // Metodo para remover assento
    public boolean removerAssento(String nomeAssento) {
        if (assentos.containsKey(nomeAssento)) {
            assentos.remove(nomeAssento);
            return true;
        } else {
            return false;
        }
    }
    public void alterarAssento(String key,boolean status){
        assentos.put(key,status);
    }
    public void adicionarAvaliacao(Avaliacao avaliacao){
        avaliacoes.add(avaliacao);
    }

    // Metodo para retornar assentos disponíveis
    public List<String> getAssentosDisponiveis() {
        List<String> assentosDisponiveis = new ArrayList<>();
        for (String assento : assentos.keySet()) {
            if (assentos.get(assento)) { // Se o valor da key assento for true;
                assentosDisponiveis.add(assento); // Adiciona ele na lista;
            }
        }
        return assentosDisponiveis;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Se for o mesmo objeto na memória
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Se o objeto comparado for null ou não for da mesma classe
        }

        Evento other = (Evento) obj;

        // Compara os atributos relevantes para a identidade do evento
        return this.nome.equals(other.nome) &&
                this.descricao.equals(other.descricao) &&
                this.data.equals(other.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, descricao, data);
    }

}
