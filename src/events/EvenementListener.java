package events;

public interface EvenementListener<E extends Evenement> {
    void handle(E event);
}
