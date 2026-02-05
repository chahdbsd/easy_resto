package Avis.controller;

import Avis.model.Avis;
import Avis.service.AvisService;
import Avis.service.IAvisService;

public class AvisController {
    private final IAvisService service = new AvisService();

    public void afficherTous() { service.afficherTous(); }
    public void afficherParPlat(int platId) { service.afficherParPlat(platId); }
    public void ajouter(int userId, int platId, int note, String commentaire) { service.ajouter(userId, platId, note, commentaire); }
    public void ajouterAvis(Avis avis) { service.ajouterAvis(avis); }
    public void supprimer(int avisId) { service.supprimer(avisId); }
}
