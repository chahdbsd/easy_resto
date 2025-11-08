package Utilisateur.service;

import Utilisateur.dao.UtilisateurDAO;
import Utilisateur.dao.IUtilisateurDAO;
import Utilisateur.model.Utilisateur;

public class UtilisateurService {
    private IUtilisateurDAO dao = new UtilisateurDAO();

    /**
     * Connexion d'un utilisateur existant.
     */
    public Utilisateur connexion(String email, String motDePasse) {
        return dao.trouverParEmail(email, motDePasse);
    }

    /**
     * Création d'un compte utilisateur.
     * Retourne l'objet Utilisateur créé ou null en cas d'erreur.
     */
    public Utilisateur creerCompte(String nom, String prenom, String email, String motDePasse) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(motDePasse);
        utilisateur.setAdmin(false);

        boolean succes = dao.creerUtilisateur(utilisateur);
        return succes ? dao.trouverParEmail(email, motDePasse) : null;
    }
}
