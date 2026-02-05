package events.listeners;

import events.*;

public class NotificationListener implements EvenementListener<Evenement> {
    @Override
    public void handle(Evenement e) {
        if (e instanceof AjoutEvenement a) {
            System.out.println("[EVENT] Ajout : " + a.cible());
        } else if (e instanceof SuppressionEvenement s) {
            System.out.println("[EVENT] Suppression : " + s.cible());
        } else {
            System.out.println("[EVENT] " + e.type() + " @ " + e.timestamp());
        }
    }
}
