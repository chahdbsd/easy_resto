package Plat.service;

import Plat.model.Plat;
import java.util.List;

public interface IPlatService {
    List<Plat> afficherMenu();
    List<Plat> filtrerParCategorie(String categorie);
    Plat obtenirPlatParId(int id);
}
