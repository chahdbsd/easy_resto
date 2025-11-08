package Avis.service;

import Avis.dao.IAvisDAO;
import Avis.dao.AvisDAO;
import Avis.model.Avis;

import java.util.List;

public class AvisService implements IAvisService {
    private IAvisDAO avisDAO = new AvisDAO();

    @Override
    public int ajouterAvis(Avis avis) {
        return avisDAO.ajouterAvis(avis);
    }

    @Override
    public List<Avis> listerAvisParPlat(int platId) {
        return avisDAO.listerAvisParPlat(platId);
    }

    @Override
    public List<Avis> listerTousLesAvis() {
        return avisDAO.listerTousLesAvis();
    }

    @Override
    public boolean supprimerAvis(int avisId) {
        return avisDAO.supprimerAvis(avisId);
    }
}
