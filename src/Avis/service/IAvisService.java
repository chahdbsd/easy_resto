package Avis.service;

import Avis.model.Avis;

public interface IAvisService {
    void afficherTous();
    void afficherParPlat(int platId);
    void ajouter(int userId, int platId, int note, String commentaire);
    void ajouterAvis(Avis avis);     // utile si ta View passe un objet
    void supprimer(int avisId);
}
