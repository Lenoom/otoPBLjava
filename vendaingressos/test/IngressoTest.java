import Classes.*;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class IngressoTest {

    @Test
    public void testVerificarSeEstaAtivoRetornandoTrue() {
        // Criar um evento no futuro
        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1);
        Date dataFutura = calendar.getTime();

        Evento evento = new Evento("Show Teste", "Descrição", dataFutura);
        Ingresso ingresso = new Ingresso(evento, 100.0, "A1");

        assertTrue(ingresso.isAtivo(), "O ingresso deve estar ativo.");
    }

    @Test
    public void testVerificarSeEstaAtivoRetornandoFalse() {
        Controller controller = new Controller();
        // Criar um evento no passado
        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1);
        Date dataPassada = calendar.getTime();


        Usuario admin = controller.cadastrarUsuario("admin", "blabla", "John Does", "30128763749", "john.does@example.com", true);

        Evento evento = controller.cadastrarEvento(admin,"Show Teste","Excelente Show",dataPassada);
        controller.adicionarAssentoEvento(admin,"Show Teste","A1");

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        usuario.addCartao("1234432114234132");
        Cartao cartao = usuario.getCartoes().getFirst();
        controller.comprarIngresso(usuario,"Show Teste", "A1",cartao);

        assertFalse(usuario.getIngressos().getFirst().isAtivo(), "O ingresso deve estar inativo.");
    }

    @Test
    public void testCancelarIngressoDepoisDaData() {
        // Criar um evento no passado
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1);
        Date dataPassada = calendar.getTime();

        Evento evento = new Evento("Show Teste", "Descrição", dataPassada);
        Ingresso ingresso = new Ingresso(evento, 100.0, "A1");

        assertFalse(ingresso.cancelar(), "Não deve ser possível cancelar o ingresso após a data do evento.");
    }

    @Test
    public void testCancelarIngressoAntesDaData() {
        // Criar um evento no futuro
        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1);
        Date dataFutura = calendar.getTime();

        Evento evento = new Evento("Show Teste", "Descrição", dataFutura);
        Ingresso ingresso = new Ingresso(evento, 100.0, "A1");

        assertTrue(ingresso.cancelar(), "Deve ser possível cancelar o ingresso antes da data do evento.");
        assertFalse(ingresso.isAtivo(), "O ingresso deve estar inativo após o cancelamento.");
    }

    @Test
    public void testReativarIngressoAntesDaData() {
        // Criar um evento no futuro
        Calendar calendar = Calendar.getInstance();
        calendar.set(2040, Calendar.JANUARY, 1);
        Date dataFutura = calendar.getTime();

        Evento evento = new Evento("Show Teste", "Descrição", dataFutura);
        Ingresso ingresso = new Ingresso(evento, 100.0, "A1");

        ingresso.cancelar(); // Cancelar o ingresso
        assertTrue(ingresso.reativar(), "Deve ser possível reativar o ingresso antes da data do evento.");
        assertTrue(ingresso.isAtivo(), "O ingresso deve estar ativo após a reativação.");
    }

    @Test
    public void testReativarIngressoDepoisDaData() {
        // Criar um evento no passado
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1);
        Date dataPassada = calendar.getTime();

        Evento evento = new Evento("Show Teste", "Descrição", dataPassada);
        Ingresso ingresso = new Ingresso(evento, 100.0, "A1");

        ingresso.setAtivo(false); // Cancelar o ingresso
        assertFalse(ingresso.reativar(), "Não deve ser possível reativar o ingresso após a data do evento.");
        assertFalse(ingresso.isAtivo(), "O ingresso deve continuar inativo após tentar reativar.");
    }

@Test
    public void testIngressoDuplicado() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 29);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso1 = new Ingresso(evento, 100.0, "A1");
        Ingresso ingresso2 = new Ingresso(evento, 100.0, "A1");

        assertEquals(ingresso1, ingresso2);
        assertEquals(ingresso1.hashCode(), ingresso2.hashCode());
    }
}