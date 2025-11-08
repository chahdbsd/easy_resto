package Avis.view;

import Avis.model.Avis;
import Avis.service.IAvisService;
import Avis.service.AvisService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class AvisView {
    private IAvisService service = new AvisService();
    private Scanner sc = new Scanner(System.in);

    public void ajouterAvis(int userId, int platId) {
        System.out.print("Note (1 à 5) : ");
        int note = sc.nextInt();
        sc.nextLine(); // flush
        System.out.print("Commentaire : ");
        String commentaire = sc.nextLine();

        Avis avis = new Avis(0, userId, platId, note, commentaire, new Timestamp(System.currentTimeMillis()));
        int id = service.ajouterAvis(avis);
        if (id > 0) {
            System.out.println("✅ Avis ajouté !");
        } else {
            System.out.println("❌ Impossible d'ajouter l'avis.");
        }
    }

    public void afficherAvisParPlat(int platId) {
        List<Avis> avisList = service.listerAvisParPlat(platId);
        if (avisList.isEmpty()) {
            System.out.println("Aucun avis pour ce plat.");
            return;
        }
        avisList.forEach(System.out::println);
    }

    public void afficherTousLesAvis() {
        List<Avis> avisList = service.listerTousLesAvis();
        if (avisList.isEmpty()) {
            System.out.println("Aucun avis enregistré.");
            return;
        }
        avisList.forEach(System.out::println);
    }

    public void supprimerAvis(int avisId) {
        if (service.supprimerAvis(avisId)) {
            System.out.println("✅ Avis supprimé !");
        } else {
            System.out.println("❌ Impossible de supprimer l'avis.");
        }
    }
}
