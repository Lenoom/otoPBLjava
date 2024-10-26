package Classes;

public class Avaliacao {
    private double nota;
    private String comentario;
    private Usuario usuario;

    public Avaliacao(double nota, String comentario, Usuario usuario){
        this.nota = nota;
        this.comentario = comentario;
        this.usuario = usuario;
    }


    // Getters e Setters
    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
