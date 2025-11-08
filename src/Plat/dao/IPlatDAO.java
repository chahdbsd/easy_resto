package Plat.dao;

import Plat.model.Plat;
import java.util.List;

public interface IPlatDAO {
    List<Plat> findAll();
    Plat findById(int id);
    List<Plat> findByCategorie(String categorie); // <-- ajout de cette méthode
}
