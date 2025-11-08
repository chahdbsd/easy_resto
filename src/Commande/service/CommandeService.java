package Commande.service;

import Commande.dao.CommandeDAO;
import Commande.dao.ICommandeDAO;
import Commande.dao.IPanierDAO;
import Commande.dao.PanierDAO;
import Commande.model.Panier;
import Commande.model.CommandePlat;
import java.util.ArrayList;
import java.util.List;

public class CommandeService implements ICommandeService {
    private ICommandeDAO commandeDAO = new CommandeDAO();
    private IPanierDAO panierDAO = new PanierDAO();

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

        for (Panier p : panier) {
            commandeDAO.ajouterDetailCommande(commandeId, p.getPlat().getId(), p.getQuantite());
        }

        panierDAO.viderPanier(userId);
        System.out.println("✅ Commande #" + commandeId + " passée avec succès !");
        return commandeId;
    }

    @Override
    public void voirHistorique(int userId) {
        commandeDAO.historiqueCommandes(userId).forEach(System.out::println);
    }

    @Override
    public double calculerMontantTotal(int commandeId) {
        double total = commandeDAO.calculerMontantTotal(commandeId);
        System.out.println("💰 Montant total de la commande #" + commandeId + " : " + total + "€");
        return total;
    }

    @Override
    public List<CommandePlat> passerCommandeAvecDetails(int userId, boolean paiementEnLigne) {
        List<Panier> panier = panierDAO.voirPanier(userId);
        List<CommandePlat> platsCommandes = new ArrayList<>();

        if (panier.isEmpty()) {
            System.out.println("⚠️ Votre panier est vide !");
            return platsCommandes;
        }

        int commandeId = commandeDAO.creerCommande(userId, paiementEnLigne);
        if (commandeId == -1) {
            System.out.println("❌ Erreur lors de la création de la commande.");
            return platsCommandes;
        }

        for (Panier p : panier) {
            commandeDAO.ajouterDetailCommande(commandeId, p.getPlat().getId(), p.getQuantite());
            platsCommandes.add(new CommandePlat(p.getPlat().getId(), p.getPlat().getNom(), p.getQuantite()));
        }

        panierDAO.viderPanier(userId);
        System.out.println("✅ Commande #" + commandeId + " passée avec succès !");
        return platsCommandes;
    }

    @Override
    public double calculerMontantTotalCommandeUtilisateur(int userId) {
        List<Panier> panier = panierDAO.voirPanier(userId);
        double total = 0;
        for (Panier p : panier) {
            total += p.getQuantite() * commandeDAO.getPrixPlat(p.getPlat().getId());
        }
        return total;
    }
}
