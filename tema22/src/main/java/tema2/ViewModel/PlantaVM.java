package tema2.ViewModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import tema2.Model.Planta;
import tema2.Model.Repo.PlantaRepo;
import tema2.ViewModel.Commands.PlantaCommands;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;


public class PlantaVM {
    private final PlantaRepo plantaRepository;
    private final ObservableList<String[]> plantaList;

    // Proprietăți pentru binding
    private final IntegerProperty idPlanta = new SimpleIntegerProperty();
    private final IntegerProperty deleteOrUpdateId = new SimpleIntegerProperty(); // ID pentru ștergere/actualizare
    private final StringProperty denumire = new SimpleStringProperty();
    private final StringProperty tip = new SimpleStringProperty();
    private final StringProperty specie = new SimpleStringProperty();
    private final BooleanProperty plantaCarnivora = new SimpleBooleanProperty();
    private final StringProperty imagini = new SimpleStringProperty();

    // Proprietăți pentru filtrare
    private final StringProperty filtruTip = new SimpleStringProperty();
    private final StringProperty filtruSpecie = new SimpleStringProperty();
    private final BooleanProperty filtruCarnivora = new SimpleBooleanProperty();

    // Comenzi MVVM
    public PlantaCommands addCommand;
    public PlantaCommands deleteCommand;
    public PlantaCommands updateCommand;
    public PlantaCommands loadAllCommand;
    public PlantaCommands filterCommand;
    public PlantaCommands exportCsvCommand;
    // Constructor PlantaVM
    public PlantaVM() {
        plantaRepository = new PlantaRepo();
        plantaList = FXCollections.observableArrayList();

        addCommand = new PlantaCommands(this::adaugaPlanta);
        deleteCommand = new PlantaCommands(this::stergePlanta);
        updateCommand = new PlantaCommands(this::actualizeazaPlanta);
        loadAllCommand = new PlantaCommands(this::incarcaPlante);
        filterCommand = new PlantaCommands(this::filtreazaPlante);
        exportCsvCommand = new PlantaCommands(this::exportToCSV);
        incarcaPlante();
    }

    // Metode resetare și încărcare
    private void incarcaPlante() {
        plantaList.clear();

        for (Planta planta : plantaRepository.getToatePlanteleSortate()) {
            plantaList.add(new String[]{
                    String.valueOf(planta.getId()),
                    planta.getDenumire(),
                    planta.getTip(),
                    planta.getSpecie(),
                    String.valueOf(planta.isPlanta_carnivora()),
                    planta.getImagini()
            });
        }
    }

    private void reseteazaCampuri() {
        idPlanta.set(0);  // Resetăm ID-ul (noua valoare se încarcă automat după adăugare)
        denumire.set("");
        tip.set("");
        specie.set("");
        plantaCarnivora.set(false);
        imagini.set("");
    }

    // Implementări comenzi
    private void adaugaPlanta() {
        Planta planta = new Planta(0, denumire.get(), tip.get(), specie.get(), plantaCarnivora.get(), imagini.get());
        plantaRepository.adaugaPlanta(planta);

        // Obține ID-ul nou generat și îl afișează
        Optional<Planta> addedPlanta = plantaRepository.getUltimaPlantaAdaugata();
        addedPlanta.ifPresent(p -> idPlanta.set(p.getId()));

        incarcaPlante();
        reseteazaCampuri();
    }

    private void stergePlanta() {
        if (deleteOrUpdateId.get() > 0) {
            plantaRepository.stergePlanta(deleteOrUpdateId.get());
            incarcaPlante();
            deleteOrUpdateId.set(0); // Resetăm ID-ul de ștergere
        }
    }

    private void actualizeazaPlanta() {
        if (deleteOrUpdateId.get() > 0) {
            Optional<Planta> existingPlanta = plantaRepository.cautaPlantaDupaID(deleteOrUpdateId.get());

            if (existingPlanta.isPresent()) {
                Planta currentPlanta = existingPlanta.get();

                String denumireNoua = (denumire.get() == null || denumire.get().isBlank()) ?
                        currentPlanta.getDenumire() : denumire.get();
                String tipNou = (tip.get() == null || tip.get().isBlank()) ?
                        currentPlanta.getTip() : tip.get();
                String specieNoua = (specie.get() == null || specie.get().isBlank()) ?
                        currentPlanta.getSpecie() : specie.get();
                boolean carnivoraNoua = plantaCarnivora.get();
                String imaginiNoi = (imagini.get() == null || imagini.get().isBlank()) ?
                        currentPlanta.getImagini() : imagini.get();

                Planta plantaActualizata = new Planta(deleteOrUpdateId.get(), denumireNoua,
                        tipNou, specieNoua, carnivoraNoua, imaginiNoi);
                plantaRepository.actualizeazaPlanta(plantaActualizata);

                incarcaPlante();
                deleteOrUpdateId.set(0);
            } else {
                System.out.println("Eroare: Planta cu ID " + deleteOrUpdateId.get() + " nu există.");
            }
        }
    }

    private void filtreazaPlante() {
        String tipFiltru = filtruTip.get();
        String specieFiltru = filtruSpecie.get();
        Boolean carnivoraFiltru = filtruCarnivora.get();

        List<Planta> rezultate = plantaRepository.filtreazaPlante(tipFiltru, specieFiltru, carnivoraFiltru);

        plantaList.clear();
        for (Planta p : rezultate) {
            plantaList.add(new String[]{
                    String.valueOf(p.getId()),
                    p.getDenumire(),
                    p.getTip(),
                    p.getSpecie(),
                    String.valueOf(p.isPlanta_carnivora()),
                    p.getImagini()
            });
        }
    }

    public ObservableList<TableColumn<String[], String>> getTableColumns() {
        ObservableList<TableColumn<String[], String>> columns = FXCollections.observableArrayList();
        String[] headers = {"ID", "Denumire", "Tip", "Specie", "Carnivoră", "Imagini"};

        for (int i = 0; i < headers.length; i++) {
            final int colIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(headers[i]);
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[colIndex]));
            columns.add(column);
        }
        return columns;
    }
    private void exportToCSV() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CSV File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                try (FileWriter writer = new FileWriter(file)) {
                    // Write header
                    writer.write("ID,Denumire,Tip,Specie,Planta_Carnivora,Imagini\n");

                    // Write data
                    for (String[] row : plantaList) {
                        writer.write(String.join(",", row) + "\n");
                    }

                    System.out.println("CSV file saved successfully at: " + file.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving CSV file: " + e.getMessage());
        }
    }

    // Getteri pentru View
    public ObservableList<String[]> getPlantaList() {
        return plantaList;
    }

    public IntegerProperty idPlantaProperty() {
        return idPlanta;
    }

    public IntegerProperty deleteOrUpdateIdProperty() {
        return deleteOrUpdateId;
    }

    public StringProperty denumireProperty() {
        return denumire;
    }

    public StringProperty tipProperty() {
        return tip;
    }

    public StringProperty specieProperty() {
        return specie;
    }

    public BooleanProperty plantaCarnivoraProperty() {
        return plantaCarnivora;
    }

    public StringProperty imaginiProperty() {
        return imagini;
    }

    public StringProperty filtruTipProperty() {
        return filtruTip;
    }

    public StringProperty filtruSpecieProperty() {
        return filtruSpecie;
    }

    public BooleanProperty filtruCarnivoraProperty() {
        return filtruCarnivora;
    }

    public PlantaCommands getAddCommand() {
        return addCommand;
    }

    public PlantaCommands getDeleteCommand() {
        return deleteCommand;
    }

    public PlantaCommands getUpdateCommand() {
        return updateCommand;
    }

    public PlantaCommands getLoadAllCommand() {
        return loadAllCommand;
    }
    public PlantaCommands getExportCsvCommand() {
        return exportCsvCommand;
    }
    public PlantaCommands getFilterCommand() {
        return filterCommand;
    }
}