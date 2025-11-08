package Utilisateur.service;

import Utilisateur.model.Utilisateur;

public interface IUtilisateurService {
    Utilisateur connexion(String email, String motDePasse);
    int creerCompte(String nom, String email, String motDePasse);
}
