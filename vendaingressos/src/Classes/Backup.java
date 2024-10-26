package Classes;

import java.util.List;

public class Backup {
    private List<Usuario> usuarios;
    private List<Evento> eventos;

    public Backup(List<Usuario> users,List<Evento> eventos){
        this.usuarios = users;
        this.eventos = eventos;
    }

    public void remontarDados(Controller controller){
        controller.setUsuarios(usuarios);
        controller.setEventos(eventos);
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
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}
