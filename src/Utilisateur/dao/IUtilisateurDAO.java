package Utilisateur.dao;

import Utilisateur.model.Utilisateur;

public interface IUtilisateurDAO {
    Utilisateur trouverParEmail(String email, String motDePasse);
    boolean creerUtilisateur(Utilisateur utilisateur); // retourne true si succès
}
