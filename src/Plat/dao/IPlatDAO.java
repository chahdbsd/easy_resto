package Plat.dao;

import Plat.model.Plat;
import java.util.List;

public interface IPlatDAO {
    List<Plat> findAll();
    List<Plat> findByCategorie(String categorie);
    Plat findById(int id);
}
