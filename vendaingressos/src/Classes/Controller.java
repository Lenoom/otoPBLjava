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
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class Controller {

    private List<Usuario> usuarios = new ArrayList<>();
    private List<Evento> eventos = new ArrayList<>();

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }
    public List<Evento> getEventos() {
        return eventos;
    }
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    // Cadastrar um usuário
    public Usuario cadastrarUsuario(String login, String senha, String nome, String cpf, String email, boolean isAdmin) {
        Usuario usuario = new Usuario(login, senha, nome, cpf, email, isAdmin);
        usuarios.add(usuario);
        return usuario;
    }

    // Metodo para avaliar um evento.
    public boolean avaliarEvento(Usuario usuario, String eventoNome, double nota, String comentario){

        Evento evento = buscarEvento(eventoNome);
        Date data = new Date();

        if(evento != null && data.before(evento.getData())){
            return usuario.avaliarEvento(evento,nota,comentario);
        }
        return false; // Caso o evento não exista.
    }
    // Metodo que cadastra eventos
    public Evento cadastrarEvento(Usuario usuario, String nome, String descricao, Date data) {
        if (!usuario.isAdmin()) {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        Evento evento = new Evento(nome, descricao, data);
        eventos.add(evento);
        return evento;
    }

    // Metodo para adicionar novo assento
    public boolean adicionarAssentoEvento(Usuario user,String nomeEvento, String assento) {
        Evento evento = buscarEvento(nomeEvento);
        if (evento != null && user.isAdmin()) {
            evento.adicionarAssento(assento);
            return true;
        }
        return false;
    }

    // Metodo para comprar um ingresso
    public boolean comprarIngresso(Usuario usuario, String nomeEvento, String assento, Cartao cartao) {
        Evento evento = buscarEvento(nomeEvento);
        Date atualData = new Date();
        if (evento != null && evento.getAssentosDisponiveis().contains(assento) && atualData.before(evento.getData())) {
            Compra buy = new Compra();
            buy.realizarCompra(usuario,evento,assento,cartao);
            usuario.getCompras().add(buy);
            return true;
        }
        return false;
    }

    // Cancelar a compra de um ingresso
    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso) {
        Date atualData = new Date();
        if (usuario.getIngressos().contains(ingresso) && (atualData.before(ingresso.getEvento().getData()))) {
            Compra buy = new Compra();
            buy.cancelarCompra(usuario, ingresso);
            return true;
        }
        return false;
    }

    // Listar Eventos Disponíveis
    public List<Evento> listarEventosDisponiveis() {
        List<Evento> eventosDisponiveis = new ArrayList<>();
        for (Evento evento : eventos) {
            if (evento.isAtivo()) {
                eventosDisponiveis.add(evento);
            }
        }
        return eventosDisponiveis;
    }

    // Listar todos os Eventos;
    public List<Evento> listarEventos(){
        return eventos;
    }

    // Metodo para listar ingressos comprados de um usuário
    public List<Ingresso> listarIngressosComprados(Usuario usuario) {
        return usuario.getIngressos();
    }

    // Buscar evento com base no seu nome
    private Evento buscarEvento(String nomeEvento) {
        for (Evento evento : eventos) {
            if (evento.getNome().equals(nomeEvento)) {
                return evento;
            }
        }
        return null;
    }

    // Guardar itens em arquivos json
    public boolean armazenaDados() {
        Gson gson = new Gson();
        Backup arquivo = new Backup(this.usuarios,this.eventos);
        try (FileWriter writer = new FileWriter("backup.json")) {
            gson.toJson(arquivo, writer);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Recuperar os dados de JSon e retornar um objeto do tipo Backup
    public Backup recuperaDados(String caminhoArquivo) {
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(caminhoArquivo)) {
            // Lê o conteúdo do arquivo JSON e converte para um objeto Backup
            Backup backup = gson.fromJson(reader, Backup.class);
            return backup;

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            System.out.println("Erro na estrutura JSON: " + e.getMessage());
            e.printStackTrace();
        }

        return null; // Retorna null caso ocorra uma exceção
    }

}