package Avis.view;

import Avis.controller.AvisController;
import Avis.model.Avis;

import java.util.Scanner;

public class AvisView {
    private final AvisController controller = new AvisController();
    private final Scanner sc = new Scanner(System.in);


    public void afficherTousLesAvis() {
        controller.afficherTous();
    }

    public void afficherAvisParPlat(int platId) {
        controller.afficherParPlat(platId);
    }

    public void ajouterAvis(int userId, int platId) {
        System.out.print("Note (1..5): ");
        int note = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Commentaire (optionnel): ");
        String commentaire = sc.nextLine();

        controller.ajouter(userId, platId, note, commentaire);
    }

    public void supprimerAvis(int avisId) {
        controller.supprimer(avisId);
    }


    public void menuAvis(int userId) {
        while (true) {
            System.out.println("\n=== AVIS ===");
            System.out.println("1) Afficher tous les avis");
            System.out.println("2) Afficher les avis d’un plat");
            System.out.println("3) Ajouter un avis");
            System.out.println("4) Supprimer un avis");
            System.out.println("0) Retour");
            System.out.print("Choix : ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> afficherTousLesAvis();
                case "2" -> {
                    System.out.print("ID du plat : ");
                    int platId = Integer.parseInt(sc.nextLine().trim());
                    afficherAvisParPlat(platId);
                }
                case "3" -> {
                    System.out.print("ID du plat : ");
                    int platId = Integer.parseInt(sc.nextLine().trim());
                    ajouterAvis(userId, platId);
                }
                case "4" -> {
                    System.out.print("ID de l’avis à supprimer : ");
                    int avisId = Integer.parseInt(sc.nextLine().trim());
                    supprimerAvis(avisId);
                }
                case "0" -> { return; }
                default -> System.out.println("Choix invalide.");
            }
        }
    }
}
