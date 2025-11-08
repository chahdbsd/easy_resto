package Commande.service;

import Commande.dao.CommandeDAO;
import Commande.dao.ICommandeDAO;
import Commande.dao.IPanierDAO;
import Commande.dao.PanierDAO;
import Commande.model.Panier;

import java.util.List;

public class CommandeService implements ICommandeService {
    private ICommandeDAO commandeDAO = new CommandeDAO();
    private IPanierDAO panierDAO = new PanierDAO();

    /**
     * Passe une commande à partir du panier de l'utilisateur.
     * @return l'ID de la commande créée, ou -1 en cas d'erreur.
     */
    @Override
    public int passerCommande(int userId, boolean paiementEnLigne) {
        List<Panier> panier = panierDAO.voirPanier(userId);

        if (panier.isEmpty()) {
            System.out.println("⚠️ Votre panier est vide !");
            return -1;
        }

        int commandeId = commandeDAO.creerCommande(userId, paiementEnLigne);
        if (commandeId == -1) {
            System.out.println("❌ Erreur lors de la création de la commande.");
            return -1;
        }

        // Ajout des plats du panier dans la commande
        for (Panier p : panier) {
            commandeDAO.ajouterDetailCommande(commandeId, p.getPlat().getId(), p.getQuantite());
        }

        // On vide le panier après validation
        panierDAO.viderPanier(userId);

        System.out.println("✅ Commande #" + commandeId + " passée avec succès !");
        return commandeId;
    }

    /**
     * Affiche l'historique des commandes d'un utilisateur.
     */
    @Override
    public void voirHistorique(int userId) {
        commandeDAO.historiqueCommandes(userId).forEach(System.out::println);
    }

    /**
     * Calcule le montant total d'une commande.
     */
    @Override
    public double calculerMontantTotal(int commandeId) {
        double total = commandeDAO.calculerMontantTotal(commandeId);
        System.out.println("💰 Montant total de la commande #" + commandeId + " : " + total + "€");
        return total;
    }
}
