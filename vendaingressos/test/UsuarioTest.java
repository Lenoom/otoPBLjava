import Classes.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

public class UsuarioTest {

    @Test
    public void testCadastrarUsuario() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertNotNull(usuario);
        assertEquals("johndoe", usuario.getLogin());
        assertEquals("John Doe", usuario.getNome());
        assertEquals("12345678901", usuario.getCpf());
        assertEquals("john.doe@example.com", usuario.getEmail());
        assertFalse(usuario.isAdmin());
    }

    @Test
    public void testCadastrarUsuarioAdmin() {
        Usuario admin = new Usuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        assertNotNull(admin);
        assertEquals("admin", admin.getLogin());
        assertEquals("Admin User", admin.getNome());
        assertEquals("00000000000", admin.getCpf());
        assertEquals("admin@example.com", admin.getEmail());
        assertTrue(admin.isAdmin());
    }

    @Test
    public void testLogin() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertTrue(usuario.login("johndoe", "senha123"));
        assertFalse(usuario.login("johndoe", "senhaErrada"));
    }

    @Test
    public void testAtualizarSenha() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setSenha("novaSenha123");
        assertTrue(usuario.login("johndoe", "novaSenha123"));
        assertFalse(usuario.login("johndoe", "senha123"));
    }

    @Test
    public void testDadosUsuario() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setNome("Jonathan Doe");
        usuario.setCpf("10987654321");
        usuario.setEmail("jon.doe@example.com");

        assertEquals("Jonathan Doe", usuario.getNome());
        assertEquals("10987654321", usuario.getCpf());
        assertEquals("jon.doe@example.com", usuario.getEmail());
    }

    @Test
    public void testUsuarioDuplicado() {
        Usuario usuario1 = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Usuario usuario2 = new Usuario("johndoe", "senha456", "John Doe", "12345678901", "john.doe@example.com", false);

        assertEquals(usuario1, usuario2);
    }

    @Test
    public void testAddIngresso() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Ingresso ingresso = new Ingresso(new Evento("Show Teste", "Descrição", new Date()), 100.0, "A1");

        usuario.addIngresso(ingresso);

        assertEquals(1, usuario.getIngressos().size(), "O usuário deve ter 1 ingresso.");
        assertEquals(ingresso, usuario.getIngressos().get(0), "O ingresso adicionado deve ser o mesmo que o esperado.");
    }

    @Test
    public void testAddCartao() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertTrue(usuario.addCartao("1234-5678-9876-5432"), "O cartão deve ser adicionado com sucesso.");
        assertEquals(1, usuario.getCartoes().size(), "O usuário deve ter 1 cartão.");
    }

    @Test
    public void testRemoveCartao() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        usuario.addCartao("1234-5678-9876-5432");
        Cartao cartao = usuario.getCartoes().getFirst();

        assertTrue(usuario.removeCartao(cartao), "O cartão deve ser removido com sucesso.");
        assertEquals(0, usuario.getCartoes().size(), "O usuário não deve ter cartões após a remoção.");
    }

    @Test
    public void testAvaliarEvento() {
        Controller controller = new Controller();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1);
        Date data = calendar.getTime();

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        usuario.addCartao("1234432112344321");
        Cartao cartao = usuario.getCartoes().getFirst();

        Usuario admin = controller.cadastrarUsuario("admin", "blabla", "John Does", "30128763749", "john.does@example.com", true);
        Evento evento = controller.cadastrarEvento(admin,"Show Teste","Excelente Show", data);
        controller.adicionarAssentoEvento(admin,"Show Teste", "A1");

        usuario.getIngressos().add(new Ingresso(evento,100.0f,"A1"));

        assertTrue(usuario.avaliarEvento(evento, 5.0, "Excelente show!"), "Deve ser possível avaliar um evento no futuro.");
        assertEquals(1, evento.getAvaliacoes().size(), "O evento deve ter 1 avaliação.");
    }

    @Test
    public void testAvaliarEventoNoPassado() {
        Controller controller = new Controller();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Usuario admin = controller.cadastrarUsuario("admin", "blabla", "John Does", "30128763749", "john.does@example.com", true);
        controller.cadastrarEvento(admin,"Show Teste","Excelente Show", data);
        Evento evento = controller.listarEventos().getFirst();

        assertFalse(usuario.avaliarEvento(evento, 5.0, "Excelente show!"), "Não deve ser possível avaliar um evento no passado.");
    }

    @Test
    public void testEditarPerfil() {
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertTrue(usuario.editarPerfil("nome", "Jonathan Doe"), "Deve ser possível editar o nome.");
        assertEquals("Jonathan Doe", usuario.getNome(), "O nome deve ser atualizado.");

        assertTrue(usuario.editarPerfil("email", "jon.doe@example.com"), "Deve ser possível editar o email.");
        assertEquals("jon.doe@example.com", usuario.getEmail(), "O email deve ser atualizado.");

        assertFalse(usuario.editarPerfil("telefone", "1234567890"), "A edição de um atributo inválido deve retornar false.");
    }
}