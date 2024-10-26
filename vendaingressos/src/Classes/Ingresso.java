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

import java.util.Date;
import java.util.Objects;
import Classes.Evento;

public class Ingresso {
    private Evento evento;
    private double preco;
    private String assento;
    private boolean isAtivo;

    public Ingresso(Evento evento, double preco, String assento) {
        this.evento = evento;
        this.preco = preco;
        this.assento = assento;
        this.isAtivo = true;
    }

    // Metodo para verificar se o ingresso ainda é válido (ativo)
    public boolean isAtivo() {
        return isAtivo;
    }

    // Metodo para reativar o ingresso
    public boolean reativar() {
        Date dataAtual = new Date();
        Date dataEvento = evento.getData();

        if (dataAtual.after(dataEvento) || dataAtual.equals(dataEvento)) {
            return false;
        } else {
            isAtivo = true;
            return true;
        }
    }

    // Metodo para cancelar o ingresso
    public boolean cancelar() {
        Date dataAtual = new Date();
        Date dataEvento = evento.getData();

        if (dataAtual.after(dataEvento) || dataAtual.equals(dataEvento)) {
            return false;
        } else {
            isAtivo = false;
            return true;
        }
    }

    // Teste de ingresso duplicado
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Ingresso ingresso = (Ingresso) obj;
        return Double.compare(ingresso.preco, preco) == 0 &&
                Objects.equals(evento, ingresso.evento) &&
                Objects.equals(assento, ingresso.assento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(evento, preco, assento);
    }

    // Getters
    public Evento getEvento() {
        return evento;
    }

    public double getPreco() {
        return preco;
    }

    public String getAssento() {
        return assento;
    }

    // Setters
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public void setAssento(String assento) {
        this.assento = assento;
    }

    public void setAtivo(boolean isAtivo) {
        this.isAtivo = isAtivo;
    }
}