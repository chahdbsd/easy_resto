package Utilisateur.view;

import Utilisateur.model.Utilisateur;
import Utilisateur.service.UtilisateurService;

import java.util.Scanner;

public class UtilisateurView {
    private UtilisateurService service = new UtilisateurService();
    private Scanner sc = new Scanner(System.in);

    /**
     * Permet à l'utilisateur de se connecter ou de créer un compte.
     * Retourne l'objet Utilisateur connecté ou créé, ou null si échec.
     */
    public Utilisateur loginOuCreerCompte() {
        System.out.println("\n=== Connexion / Création de compte ===");
        System.out.print("Voulez-vous (1) vous connecter ou (2) créer un compte ? ");
        int choix = sc.nextInt();
        sc.nextLine(); // flush

        if (choix == 1) {
            System.out.print("Email : ");
            String email = sc.nextLine();
            System.out.print("Mot de passe : ");
            String mdp = sc.nextLine();
            Utilisateur user = service.connexion(email, mdp);
            if (user != null) {
                System.out.println("✅ Connexion réussie !");
            } else {
                System.out.println("❌ Email ou mot de passe incorrect.");
            }
            return user;

        } else if (choix == 2) {
            System.out.print("Nom : ");
            String nom = sc.nextLine();
            System.out.print("Prénom : ");
            String prenom = sc.nextLine();   // <--- ajouté
            System.out.print("Email : ");
            String email = sc.nextLine();
            System.out.print("Mot de passe : ");
            String mdp = sc.nextLine();

            Utilisateur user = service.creerCompte(nom, prenom, email, mdp); // adapter la méthode service
            if (user != null) {
                System.out.println("✅ Compte créé avec succès !");
            } else {
                System.out.println("❌ Échec de la création du compte.");
            }
            return user;

        } else {
            System.out.println("Choix invalide !");
            return null;
        }
    }
}
