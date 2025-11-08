package Avis.dao;

import Avis.model.Avis;
import java.util.List;

public interface IAvisDAO {
    int ajouterAvis(Avis avis);
    List<Avis> listerAvisParPlat(int platId);
    List<Avis> listerTousLesAvis();
    boolean supprimerAvis(int avisId);
}
