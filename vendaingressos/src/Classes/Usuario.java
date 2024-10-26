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
import java.util.ArrayList;
import java.util.List;


public class Usuario{
    private String login;
    private String senha;
    private String nome;
    private String cpf;
    private String email;
    private boolean isAdmin;
    private List<Ingresso> ingressos;
    private List<Cartao> cartoes;
    private List<Compra> compras;

    public String getLogin() {return login;}
    public void setLogin(String login) {this.login = login;}
    public String getSenha() {return senha;}
    public void setSenha(String senha){this.senha = senha;}
    public String getNome(){return nome;}
    public void setNome(String nome){this.nome = nome;}
    public String getCpf() {return cpf;}
    public void setCpf(String cpf){this.cpf = cpf;}
    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public List<Ingresso> getIngressos() {
        return ingressos;
    }
    public void setIngressos(List<Ingresso> ingressos) {this.ingressos = ingressos;}
    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }
    public List<Cartao> getCartoes(){
        return cartoes;
    }
    public void setCompras(List<Compra> compras) {
        this.compras = compras;
    }
    public List<Compra> getCompras() {
        return compras;
    }

    // Contrutor do Usuário.
    public Usuario(String login, String senha, String nome, String cpf, String email, boolean isAdmin){
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.isAdmin = isAdmin;
        this.ingressos = new ArrayList<>();
        this.cartoes = new ArrayList<>();
    }
    public boolean isAdmin(){
        return isAdmin;
    }

    public boolean login(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    public void addIngresso(Ingresso ingresso){
        this.ingressos.add(ingresso);
    }
    // Metodo pra add pagamento
    public boolean addCartao(String numeracao){
        Cartao cartao = new Cartao(numeracao);
        if(cartao != null) {
            this.cartoes.add(cartao);
            return true;
        }
        return false;
    }
    // Metodo para remover cartao
    public boolean removeCartao(Cartao cartao){
        if(this.cartoes.contains(cartao)) {
            this.cartoes.remove(cartao);
            return true;
        }
        return false;
    }

    // Metodo para avaliar um evento;
    public boolean avaliarEvento(Evento evento,double nota,String comentario){
        Date atualData = new Date();
        if ( (evento != null) && (atualData.after(evento.getData())) && possuiEvento(evento)){
                Avaliacao avaliacao = new Avaliacao(nota, comentario, this);
                evento.adicionarAvaliacao(avaliacao);
                return true;
        }
        return false;
    }

    // Verifica se o usuário possui um ingresso para um evento específico
    public boolean possuiEvento(Evento evento) {
        for(Ingresso ingresso: this.getIngressos()){
            if(ingresso.getEvento().equals(evento)) {
                return true; // Encerra o loop ao encontrar o evento
            }
        }
        return false; // Retorna false se não encontrar o evento
    }


    // Metodo para editar perfil.
    public boolean editarPerfil(String atributo, String novo) {
        switch (atributo.toLowerCase()) {
            case "nome":
                setNome(novo);
                return true;
            case "email":
                setEmail(novo);
                return true;
            case "senha":
                setSenha(novo);
                return true;
            default:
                System.out.println("Atributo inválido.");
                return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;
        return cpf.equals(usuario.cpf);
    }

    @Override
    public int hashCode() {
        return cpf.hashCode();
    }


}