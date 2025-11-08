import Plat.view.PlatView;
import Commande.view.CommandeView;
import Utilisateur.view.UtilisateurView;
import Utilisateur.model.Utilisateur;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PlatView platView = new PlatView();
        CommandeView commandeView = new CommandeView();

        // 1️⃣ Consultation du menu (accessible sans compte)
        platView.afficherMenuPrincipal(0);

        System.out.println("\nVoulez-vous passer une commande ? (oui/non)");
        String reponse = sc.nextLine().trim().toLowerCase();

        if (reponse.equals("oui")) {
            UtilisateurView utilisateurView = new UtilisateurView();
            Utilisateur utilisateur = utilisateurView.loginOuCreerCompte();
            if (utilisateur != null) {
                int userId = utilisateur.getId();
                commandeView.menuCommande(userId);
            } else {
                System.out.println("⚠️ Vous devez être connecté pour passer une commande.");
            }
        } else {
            System.out.println("Merci de votre visite !");
        }
    }
}
