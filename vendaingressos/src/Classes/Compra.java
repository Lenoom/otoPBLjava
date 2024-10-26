package Classes;

public  class Compra {
    public boolean realizarCompra(Usuario user, Evento evento,String assento,Cartao cartao) {
        if(user.getCartoes().contains(cartao)) {
            Ingresso ingresso = new Ingresso(evento, 100.0f, assento);
            user.addIngresso(ingresso);
            ingresso.setAtivo(false);
            evento.alterarAssento(assento, false);
            System.out.println("Um email com uma cópia do ingresso foi enviado para: " + user.getEmail());
            return true;
        }
        return false;
    }
    public void cancelarCompra(Usuario user, Ingresso ingresso){
        if(user.getIngressos().contains(ingresso)) {
            user.getIngressos().remove(ingresso);
            String assento = ingresso.getAssento();
            ingresso.getEvento().alterarAssento(assento, true);
        }
    }
}