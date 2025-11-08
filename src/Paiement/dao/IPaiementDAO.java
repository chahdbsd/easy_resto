package Paiement.dao;

import Paiement.model.Paiement;
import java.util.List;

public interface IPaiementDAO {
    void enregistrerPaiement(Paiement paiement);
    List<Paiement> historiquePaiements(int userId);
}
