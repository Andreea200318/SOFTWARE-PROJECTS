package tema2.ViewModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import tema2.Model.Exemplar;
import tema2.Model.Repo.ExemplarRepo;
import tema2.ViewModel.Commands.ExemplarCommands;
import tema2.ViewModel.Commands.PlantaCommands;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;

public class ExemplarVM {
    private final ExemplarRepo exemplarRepository;
    private final ObservableList<String[]> exemplarList;

    // Proprietăți pentru binding
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty deleteOrUpdateId = new SimpleIntegerProperty(); // ID pentru ștergere/actualizare
    private final StringProperty imagine = new SimpleStringProperty();
    private final StringProperty zona = new SimpleStringProperty();
    private final IntegerProperty planta_id = new SimpleIntegerProperty();
    private final StringProperty denumire = new SimpleStringProperty();

    // Proprietăți pentru filtrare
    private final StringProperty filtruZona = new SimpleStringProperty();
    private final StringProperty filtruSpecie = new SimpleStringProperty();

    // Comenzi MVVM
    public ExemplarCommands addCommand;
    public ExemplarCommands deleteCommand;
    public ExemplarCommands updateCommand;
    public ExemplarCommands loadAllCommand;
    public ExemplarCommands filterCommand;
    public ExemplarCommands exportCsvCommand;

    // Constructor ExemplarVM
    public ExemplarVM() {
        exemplarRepository = new ExemplarRepo();
        exemplarList = FXCollections.observableArrayList();

        addCommand = new ExemplarCommands(this::adaugaExemplar);
        deleteCommand = new ExemplarCommands(this::stergeExemplar);
        updateCommand = new ExemplarCommands(this::actualizeazaExemplar);
        loadAllCommand = new ExemplarCommands(this::incarcaExemplare);
        filterCommand = new ExemplarCommands(this::filtreazaExemplare);
        //exportCsvCommand = new PlantaCommands(this::exportToCSV);
        incarcaExemplare();
    }

    // Metode resetare și încărcare
    private void incarcaExemplare() {
        exemplarList.clear();

        // Obținem informații extinse despre exemplare (inclusiv detalii plante)
        List<Object[]> rezultate = exemplarRepository.getExemplareWithPlantaInfo();
        for (Object[] row : rezultate) {
            exemplarList.add(new String[]{
                    String.valueOf(row[0]), // id
                    (String) row[1],        // imagine
                    (String) row[2],        // zona
                    (String) row[3],        // denumire plantă
                    (String) row[4]         // specie plantă
            });
        }
    }

    private void reseteazaCampuri() {
        id.set(0);  // Resetăm ID-ul (noua valoare se încarcă automat după adăugare)
        imagine.set("");
        zona.set("");
        planta_id.set(0);
        denumire.set("");
    }

    // Implementări comenzi
    private void adaugaExemplar() {
        Exemplar exemplar = new Exemplar(0, imagine.get(), zona.get(), planta_id.get(), denumire.get());
        exemplarRepository.adaugaExemplar(exemplar);

        // Actualizăm ID-ul generat
        id.set(exemplar.getId());

        incarcaExemplare();
        reseteazaCampuri();
    }

    private void stergeExemplar() {
        if (deleteOrUpdateId.get() > 0) {
            exemplarRepository.stergeExemplar(deleteOrUpdateId.get());
            incarcaExemplare();
            deleteOrUpdateId.set(0); // Resetăm ID-ul de ștergere
        }
    }

    private void actualizeazaExemplar() {
        if (deleteOrUpdateId.get() > 0) {
            Optional<Exemplar> existingExemplar = exemplarRepository.cautaExemplarDupaID(deleteOrUpdateId.get());

            if (existingExemplar.isPresent()) {
                Exemplar currentExemplar = existingExemplar.get();

                String imagineNoua = (imagine.get() == null || imagine.get().isBlank()) ?
                        currentExemplar.getImagine() : imagine.get();
                String zonaNoua = (zona.get() == null || zona.get().isBlank()) ?
                        currentExemplar.getZona() : zona.get();
                int plantaIdNou = (planta_id.get() == 0) ?
                        currentExemplar.getPlantaId() : planta_id.get();
                String denumireNoua = (denumire.get() == null || denumire.get().isBlank()) ?
                        currentExemplar.getDenumire() : denumire.get();

                Exemplar exemplarActualizat = new Exemplar(deleteOrUpdateId.get(), imagineNoua,
                        zonaNoua, plantaIdNou, denumireNoua);
                exemplarRepository.actualizeazaExemplar(exemplarActualizat);

                incarcaExemplare();
                deleteOrUpdateId.set(0);
            } else {
                System.out.println("Eroare: Exemplarul cu ID " + deleteOrUpdateId.get() + " nu există.");
            }
        }
    }

    private void filtreazaExemplare() {
        exemplarList.clear();

        // Dacă este setat filtrul pentru specie, folosim căutarea după specie
        if (filtruSpecie.get() != null && !filtruSpecie.get().isBlank()) {
            List<Exemplar> rezultate = exemplarRepository.getExemplareBySpecie(filtruSpecie.get());

            for (Exemplar e : rezultate) {
                // Dacă avem și filtru pentru zona grădinii, verificăm și asta
                if (filtruZona.get() == null || filtruZona.get().isBlank() ||
                        e.getZona().toLowerCase().contains(filtruZona.get().toLowerCase())) {

                    // Obținem informații despre planta asociată pentru afișare
                    // Pentru simplificare, aici punem doar ID-ul plantei și zonele din exemplar
                    exemplarList.add(new String[]{
                            String.valueOf(e.getId()),
                            e.getImagine(),
                            e.getZona(),
                            e.getDenumire(),
                            filtruSpecie.get() // Folosim filtrul ca valoare pentru specie
                    });
                }
            }
        } else if (filtruZona.get() != null && !filtruZona.get().isBlank()) {
            // Filtrare doar după zona grădinii
            List<Object[]> rezultate = exemplarRepository.getExemplareWithPlantaInfo();

            for (Object[] row : rezultate) {
                String zona = (String) row[2];

                if (zona.toLowerCase().contains(filtruZona.get().toLowerCase())) {
                    exemplarList.add(new String[]{
                            String.valueOf(row[0]), // id
                            (String) row[1],        // imagine
                            (String) row[2],        // zona
                            (String) row[3],        // denumire plantă
                            (String) row[4]         // specie plantă
                    });
                }
            }
        } else {
            // Dacă nu avem filtre, încărcăm toate exemplarele
            incarcaExemplare();
        }
    }

    public ObservableList<TableColumn<String[], String>> getTableColumns() {
        ObservableList<TableColumn<String[], String>> columns = FXCollections.observableArrayList();
        String[] headers = {"ID", "Imagine", "Zona Grădină", "Denumire Plantă", "Specie Plantă"};

        for (int i = 0; i < headers.length; i++) {
            final int colIndex = i;
            TableColumn<String[], String> column = new TableColumn<>(headers[i]);
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[colIndex]));
            columns.add(column);
        }
        return columns;
    }

    // Getteri pentru View
    public ObservableList<String[]> getExemplarList() {
        return exemplarList;
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty deleteOrUpdateIdProperty() {
        return deleteOrUpdateId;
    }

    public StringProperty imagineProperty() {
        return imagine;
    }

    public StringProperty zonaProperty() {
        return zona;
    }

    public IntegerProperty plantaIdProperty() {
        return planta_id;
    }

    public StringProperty denumireProperty() {
        return denumire;
    }

    public StringProperty filtruZonaProperty() {
        return filtruZona;
    }

    public StringProperty filtruSpecieProperty() {
        return filtruSpecie;
    }

    public ExemplarCommands getAddCommand() {
        return addCommand;
    }

    public ExemplarCommands getDeleteCommand() {
        return deleteCommand;
    }

    public ExemplarCommands getUpdateCommand() {
        return updateCommand;
    }

    public ExemplarCommands getLoadAllCommand() {
        return loadAllCommand;
    }

    public ExemplarCommands getFilterCommand() {
        return filterCommand;
    }
}