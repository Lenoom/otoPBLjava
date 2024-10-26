import Classes.Avaliacao;
import Classes.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class AvaliacaoTest {

    @Test
    void testAvaliacaoCreation() {
        Usuario usuario = new Usuario("login", "senha", "Nome", "123456789", "email@example.com", false);
        Avaliacao avaliacao = new Avaliacao(4.5, "Ótimo evento!", usuario);

        assertEquals(4.5, avaliacao.getNota());
        assertEquals("Ótimo evento!", avaliacao.getComentario());
        assertEquals(usuario, avaliacao.getUsuario());
    }

    @Test
    void testSettersAndGetters() {
        Usuario usuario = new Usuario("login", "senha", "Nome", "123456789", "email@example.com", false);
        Avaliacao avaliacao = new Avaliacao(4.0, "Bom", usuario);

        avaliacao.setNota(5.0);
        avaliacao.setComentario("Excelente");
        avaliacao.setUsuario(usuario);

        assertEquals(5.0, avaliacao.getNota());
        assertEquals("Excelente", avaliacao.getComentario());
        assertEquals(usuario, avaliacao.getUsuario());
    }
}
