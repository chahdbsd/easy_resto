package Plat.controller;

import Plat.model.Plat;
import Plat.service.IPlatService;
import Plat.service.PlatService;
import java.util.List;

public class PlatController {
    private IPlatService service = new PlatService();

    public void afficherMenu() {
        List<Plat> plats = service.afficherMenu();
        plats.forEach(System.out::println);
    }

    public void filtrerParCategorie(String categorie) {
        List<Plat> plats = service.filtrerParCategorie(categorie);
        if (plats.isEmpty()) {
            System.out.println("⚠️ Aucun plat trouvé dans la catégorie '" + categorie + "'.");
        } else {
            plats.forEach(System.out::println);
        }
    }

    public Plat obtenirPlatParId(int id) {
        return service.obtenirPlatParId(id);
    }
}
