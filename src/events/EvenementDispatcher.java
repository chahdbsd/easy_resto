package events;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class EvenementDispatcher {


    private final Map<Class<? extends Evenement>, List<EvenementListener<? super Evenement>>> routes =
            new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <E extends Evenement> void subscribe(Class<E> type, EvenementListener<? super E> listener) {
        routes.computeIfAbsent(type, k -> new ArrayList<>())
                .add((EvenementListener<? super Evenement>) listener);
    }

    public <E extends Evenement> void publish(E event) {
        List<EvenementListener<? super Evenement>> ls = routes.get(event.getClass());
        if (ls == null) return;
        for (EvenementListener<? super Evenement> l : ls) {

            @SuppressWarnings("unchecked")
            EvenementListener<? super E> listener = (EvenementListener<? super E>) l;
            listener.handle(event);
        }
    }
}
