package tema2.Model;

import java.util.ArrayList;
import java.util.List;

public class Planta {
    private int id;                       // corespunde cu 'id' din SQL
    private String denumire;
    private String tip;
    private String specie;
    private boolean planta_carnivora;     // corespunde cu 'planta_carnivora' din SQL
    private String imagini;              // corespunde cu 'imagini' din SQL
    private List<Exemplar> exemplare;    // relația cu Exemplar, dacă vrei să o păstrezi

    public Planta(int id, String denumire, String tip, String specie, boolean planta_carnivora, String imagini) {
        this.id = id;
        this.denumire = denumire;
        this.tip = tip;
        this.specie = specie;
        this.planta_carnivora = planta_carnivora;
        this.imagini = imagini;
        this.exemplare = new ArrayList<>();
    }

    public Planta() {
        this.id = 0;
        this.denumire = "";
        this.tip = "";
        this.specie = "";
        this.planta_carnivora = false;
        this.imagini = "";
        this.exemplare = new ArrayList<>();
    }

    // Getteri și setteri
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public boolean isPlanta_carnivora() {
        return planta_carnivora;
    }

    public void setPlanta_carnivora(boolean planta_carnivora) {
        this.planta_carnivora = planta_carnivora;
    }

    public String getImagini() {
        return imagini;
    }

    public void setImagini(String imagini) {
        this.imagini = imagini;
    }

    public List<Exemplar> getExemplare() {
        return exemplare;
    }

    public void setExemplare(List<Exemplar> exemplare) {
        this.exemplare = exemplare;
    }
}
