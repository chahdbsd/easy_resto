package Plat.service;

import Plat.dao.IPlatDAO;
import Plat.dao.PlatDAO;
import Plat.model.Plat;
import java.util.List;

public class PlatService implements IPlatService {
    private IPlatDAO platDAO = new PlatDAO();

    @Override
    public List<Plat> afficherMenu() {
        return platDAO.findAll();
    }

    @Override
    public List<Plat> filtrerParCategorie(String categorie) {
        return platDAO.findByCategorie(categorie);
    }

    @Override
    public Plat obtenirPlatParId(int id) {
        return platDAO.findById(id);
    }
}
