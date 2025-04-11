package tema2.Model;

public class Exemplar {
    private int id;               // Database column: id
    private String imagine;       // Database column: imagine
    private String zona;          // Database column: zona
    private int planta_id;        // Database column: planta_id
    private String denumire;      // Database column: denumire

    public Exemplar(int id, String imagine, String zona, int planta_id, String denumire) {
        this.id = id;
        this.imagine = imagine;
        this.zona = zona;
        this.planta_id = planta_id;
        this.denumire = denumire;
    }

    public Exemplar() {
        this.id = 0;
        this.imagine = "";
        this.zona = "";
        this.planta_id = 0;
        this.denumire = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagine() {
        return imagine;
    }

    public void setImagine(String imagine) {
        this.imagine = imagine;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public int getPlantaId() {
        return planta_id;
    }

    public void setPlantaId(int planta_id) {
        this.planta_id = planta_id;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
}