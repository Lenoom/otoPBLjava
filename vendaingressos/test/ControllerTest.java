import Classes.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    @Test
    public void testCadastrarUsuario() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertNotNull(usuario);
        assertEquals("johndoe", usuario.getLogin());
        assertEquals("John Doe", usuario.getNome());
    }

    @Test
    public void testAvaliarEventoJaOcorrido() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1); // Data já ocorrida
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        boolean resultado = controller.avaliarEvento(usuario, "Show de Rock", 5.0, "Ótimo evento!");

        assertFalse(resultado); // Não deve conseguir avaliar
    }

    @Test
    public void testTentarAvaliarEventoFuturo() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        boolean resultado = controller.avaliarEvento(usuario, "Show de Rock", 5.0, "Ótimo evento!");

        assertFalse(resultado); // Não deve conseguir avaliar
    }

    @Test
    public void testCadastrarEventoComoAdmin() {
        Controller controller = new Controller();
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        assertNotNull(evento);
        assertEquals("Show de Rock", evento.getNome());
    }

    @Test
    public void testTentarCadastrarEventoComoUsuarioComum() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        Exception exception = assertThrows(SecurityException.class, () -> {
            controller.cadastrarEvento(usuario, "Show de Rock", "Banda XYZ", data);
        });

        assertEquals("Somente administradores podem cadastrar eventos.", exception.getMessage());
    }

    @Test
    public void testAdicionarAssentoComoAdmin() {
        Controller controller = new Controller();
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        boolean resultado = controller.adicionarAssentoEvento(admin, "Show de Rock", "A1");

        assertTrue(resultado);
    }

    @Test
    public void testTentarAdicionarAssentoComoUsuarioComum() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        boolean resultado = controller.adicionarAssentoEvento(usuario, "Show de Rock", "A1");

        assertFalse(resultado); // Não deve conseguir adicionar
    }

    @Test
    public void testComprarIngresso() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        usuario.addCartao("1234432114234132");
        Cartao cartao = usuario.getCartoes().getFirst();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento(admin, "Show de Rock", "A1");

        boolean resultado = controller.comprarIngresso(usuario, "Show de Rock", "A1", cartao);

        assertTrue(resultado); // Deve conseguir comprar
    }

    @Test
    public void testCancelarCompraAntesDoEvento() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        usuario.addCartao("1234432114234132");
        Cartao cartao = usuario.getCartoes().getFirst();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento(admin, "Show de Rock", "A1");
        controller.comprarIngresso(usuario, "Show de Rock", "A1", cartao);

        Ingresso ingresso = usuario.getIngressos().get(0); // Assume que o ingresso foi adicionado à lista do usuário

        boolean cancelado = controller.cancelarCompra(usuario, ingresso);

        assertTrue(cancelado); // Deve conseguir cancelar
    }

    @Test
    public void testTentarCancelarCompraDepoisQueEventoJaOcorreu() {
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        usuario.addCartao("1234432114234132");
        Cartao cartao = usuario.getCartoes().getFirst();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1); // Data já ocorrida
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        controller.adicionarAssentoEvento(admin, "Show de Rock", "A1");
        usuario.getIngressos().add(new Ingresso(evento,100.0f,"A1"));

        Ingresso ingresso = usuario.getIngressos().get(0); // Assume que o ingresso foi adicionado à lista do usuário

        boolean cancelado = controller.cancelarCompra(usuario, ingresso);

        assertFalse(cancelado); // Não deve conseguir cancelar
    }

    @Test
    public void testListarEventosDisponiveis() {
        Controller controller = new Controller();
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        List<Evento> eventos = controller.listarEventosDisponiveis();

        assertEquals(1, eventos.size());
    }

    @Test
    public void testListarIngressosComprados() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        usuario.addCartao("1234432114234132");
        Cartao cartao = usuario.getCartoes().getFirst();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();

        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento(admin, "Show de Rock", "A1");
        controller.comprarIngresso(usuario, "Show de Rock", "A1", cartao);
        List<Ingresso> ingressos = controller.listarIngressosComprados(usuario);

        assertEquals(1, ingressos.size());
    }

    @Test
    public void armazenarBackup(){
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        usuario.addCartao("12345678987654");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1); // Data futura
        Date data = calendar.getTime();
        controller.cadastrarEvento(admin,"Showzão","Show Legal",data);

        controller.adicionarAssentoEvento(admin,"Showzão","A1");
        controller.adicionarAssentoEvento(admin,"Showzão","A2");
        controller.adicionarAssentoEvento(admin,"Showzão","A3");

        Cartao cartao = usuario.getCartoes().getFirst();
        controller.comprarIngresso(usuario,"Showzão","A2",cartao);

        assertTrue(controller.armazenaDados());
    }

    @Test
    public void recuperarBackup(){
        Controller controller = new Controller();
        Backup arquivo = controller.recuperaDados("backup.json");
        arquivo.remontarDados(controller);

        assertNotNull(arquivo);
        Evento evento = controller.getUsuarios().getFirst().getIngressos().getFirst().getEvento();
        System.out.println(controller.getEventos().contains(evento));

    }
}
