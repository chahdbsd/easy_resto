import Plat.view.PlatView;
import Commande.view.CommandeView;

public class Main {
    public static void main(String[] args) {
        int userId = 1; // Simuler un utilisateur connecté

        PlatView platView = new PlatView();
        CommandeView commandeView = new CommandeView();

        platView.afficherMenuPrincipal(userId); // voir plats, filtrer, ajouter panier
        commandeView.menuCommande(userId);     // passer commande
    }
}
