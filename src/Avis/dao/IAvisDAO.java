package Avis.dao;

import Avis.model.Avis;
import java.util.List;

public interface IAvisDAO {
    List<Avis> findAll();
    List<Avis> findByPlat(int platId);
    void insert(int userId, int platId, int note, String commentaire);
    void delete(int avisId);
}
