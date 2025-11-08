package Avis.service;

import Avis.model.Avis;
import java.util.List;

public interface IAvisService {
    int ajouterAvis(Avis avis);
    List<Avis> listerAvisParPlat(int platId);
    List<Avis> listerTousLesAvis();
    boolean supprimerAvis(int avisId);
}
