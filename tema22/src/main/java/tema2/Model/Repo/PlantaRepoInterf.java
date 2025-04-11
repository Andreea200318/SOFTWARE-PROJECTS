package tema2.Model.Repo;

import tema2.Model.Planta;

import java.util.List;
import java.util.Optional;

public interface PlantaRepoInterf {
    void adaugaPlanta(Planta planta);

    void stergePlanta(int idPlanta);

    void actualizeazaPlanta(Planta planta);

    List<Planta> getToatePlanteleSortate(); // sortare după tip și specie

    List<Planta> filtreazaPlante(String tip, String specie, Boolean carnivora) ;

    Optional<Planta> cautaPlantaDupaId(int idPlanta);

    Optional<Planta> cautaPlantaDupaDenumire(String denumire);
}
