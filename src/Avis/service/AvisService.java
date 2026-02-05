package Avis.service;

import Avis.dao.AvisDAO;
import Avis.dao.IAvisDAO;
import Avis.model.Avis;

import java.util.List;


import events.EvenementDispatcher;
import events.AjoutEvenement;
import events.SuppressionEvenement;

public class AvisService implements IAvisService {
    private final IAvisDAO dao = new AvisDAO();


    private final EvenementDispatcher dispatcher;
    public AvisService(EvenementDispatcher dispatcher) { this.dispatcher = dispatcher; }

    public AvisService() { this.dispatcher = null; }

    @Override
    public void afficherTous() {
        List<Avis> list = dao.findAll();
        if (list.isEmpty()) {
            System.out.println("Aucun avis.");
            return;
        }
        for (Avis a : list) print(a);
    }

    @Override
    public void afficherParPlat(int platId) {
        List<Avis> list = dao.findByPlat(platId);
        if (list.isEmpty()) {
            System.out.println("Aucun avis pour ce plat.");
            return;
        }
        for (Avis a : list) print(a);
    }

    @Override
    public void ajouter(int userId, int platId, int note, String commentaire) {
        if (note < 1 || note > 5) {
            System.out.println("Note invalide (1..5).");
            return;
        }
        dao.insert(userId, platId, note, commentaire);
        System.out.println("✅ Avis ajouté.");

        // 🔔 évènement : ajout d'avis (si dispatcher injecté)
        if (dispatcher != null) {
            dispatcher.publish(new AjoutEvenement("avis#plat:" + platId));
        }
    }


    @Override
    public void ajouterAvis(Avis avis) {
        if (avis == null) {
            System.out.println("Avis null.");
            return;
        }
        ajouter(avis.getUserId(), avis.getPlatId(), avis.getNote(), avis.getCommentaire());
    }

    @Override
    public void supprimer(int avisId) {
        dao.delete(avisId);
        System.out.println("🗑️ Avis supprimé.");


        if (dispatcher != null) {
            dispatcher.publish(new SuppressionEvenement("avis#" + avisId));
        }
    }

    private void print(Avis a) {
        String dateTxt = (a.getDateAvis() == null ? "-" : a.getDateAvis().toString());
        System.out.printf("#%d | user=%d | plat=%d | %d★ | %s | %s%n",
                a.getId(), a.getUserId(), a.getPlatId(), a.getNote(),
                a.getCommentaire(), dateTxt);
    }
}
