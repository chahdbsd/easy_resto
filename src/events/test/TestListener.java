package events.test;

import events.*;

public class TestListener implements EvenementListener<Evenement> {

    private String dernierMessage = "";
    private int nbReceptions = 0;

    @Override
    public void handle(Evenement e) {
        nbReceptions++;
        if (e instanceof AjoutEvenement a) {
            dernierMessage = "Ajout détecté : " + a.cible();
        } else if (e instanceof SuppressionEvenement s) {
            dernierMessage = "Suppression détectée : " + s.cible();
        } else {
            dernierMessage = "Evenement " + e.type() + " sur " + e.timestamp();
        }
        // (pas d'output ici pour laisser le test décider d'afficher OK/ECHEC)
    }

    public String getDernierMessage() { return dernierMessage; }
    public int getNbReceptions() { return nbReceptions; }
    public void reset() { dernierMessage = ""; nbReceptions = 0; }
}
