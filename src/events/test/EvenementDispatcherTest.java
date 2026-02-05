package events.test;

import events.*;

public class EvenementDispatcherTest {

    public static void main(String[] args) {
        System.out.println("=== Tests système d’événements ===");

        testAjoutEvenement();
        testSuppressionEvenement();
        testSansListener();
        testListenersMultiples();
        testTypeNonEnregistre();

        System.out.println("=== Fin des tests ===");
    }

    // --- Helpers ---
    private static void expect(boolean cond, String ok, String ko) {
        if (cond) System.out.println("OK  - " + ok);
        else      System.out.println("ÉCHEC - " + ko);
    }

    // 1) AjoutEvenement
    public static void testAjoutEvenement() {
        EvenementDispatcher dispatcher = new EvenementDispatcher();
        TestListener listener = new TestListener();
        dispatcher.subscribe(AjoutEvenement.class, listener);

        AjoutEvenement evt = new AjoutEvenement("ObjetAjout");
        dispatcher.publish(evt);

        expect(
                "Ajout détecté : ObjetAjout".equals(listener.getDernierMessage()),
                "testAjoutEvenement",
                "testAjoutEvenement -> reçu: " + listener.getDernierMessage()
        );
    }

    // 2) SuppressionEvenement
    public static void testSuppressionEvenement() {
        EvenementDispatcher dispatcher = new EvenementDispatcher();
        TestListener listener = new TestListener();
        dispatcher.subscribe(SuppressionEvenement.class, listener);

        SuppressionEvenement evt = new SuppressionEvenement("ObjetSuppression");
        dispatcher.publish(evt);

        expect(
                "Suppression détectée : ObjetSuppression".equals(listener.getDernierMessage()),
                "testSuppressionEvenement",
                "testSuppressionEvenement -> reçu: " + listener.getDernierMessage()
        );
    }

    // 3) Aucune exception si aucun listener
    public static void testSansListener() {
        EvenementDispatcher dispatcher = new EvenementDispatcher();
        try {
            dispatcher.publish(new AjoutEvenement("SansListener"));
            expect(true, "testSansListener", ""); // toujours OK ici
        } catch (Exception e) {
            expect(false, "", "testSansListener -> exception inattendue: " + e.getMessage());
        }
    }

    // 4) Deux listeners sur le même type → chacun reçoit l’événement
    public static void testListenersMultiples() {
        EvenementDispatcher dispatcher = new EvenementDispatcher();
        TestListener l1 = new TestListener();
        TestListener l2 = new TestListener();

        dispatcher.subscribe(AjoutEvenement.class, l1);
        dispatcher.subscribe(AjoutEvenement.class, l2);

        dispatcher.publish(new AjoutEvenement("Multi"));

        boolean ok = l1.getNbReceptions() == 1 && l2.getNbReceptions() == 1;
        expect(ok, "testListenersMultiples", "testListenersMultiples -> l1=" + l1.getNbReceptions() + ", l2=" + l2.getNbReceptions());
    }

    // 5) Publier un type sans abonnement ne doit pas appeler de listener
    public static void testTypeNonEnregistre() {
        EvenementDispatcher dispatcher = new EvenementDispatcher();
        TestListener l = new TestListener(); // non abonné

        dispatcher.publish(new AjoutEvenement("Personne"));

        boolean ok = l.getNbReceptions() == 0;
        expect(ok, "testTypeNonEnregistre", "testTypeNonEnregistre -> receptions=" + l.getNbReceptions());
    }
}
