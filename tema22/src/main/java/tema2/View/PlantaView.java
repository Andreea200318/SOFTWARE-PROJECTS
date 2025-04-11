package tema2.View;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.converter.NumberStringConverter;
import tema2.ViewModel.PlantaVM;

public class PlantaView {
    private final PlantaVM viewModel;
    private final VBox root;
    private final TableView<String[]> plantaTable;

    private final TextField idPlantaField, denumireField, tipField, specieField, imaginiField;
    private final CheckBox plantaCarnivoraCheck;

    private final Button savePlantaButton, deletePlantaButton, updatePlantaButton;

    private final TextField deleteOrUpdateIdField;

    private final TextField filterTipField, filterSpecieField;
    private final CheckBox filterCarnivoraCheck;
    private final Button filterButton, loadAllButton,exportCsvButton;

    public PlantaView() {
        viewModel = new PlantaVM();
        root = new VBox(10);
        root.setPadding(new Insets(10));

        plantaTable = new TableView<>();
        plantaTable.setPrefWidth(800);
        plantaTable.setItems(viewModel.getPlantaList());

        plantaTable.getColumns().addAll(viewModel.getTableColumns());

        idPlantaField = createTextField("ID Plantă");
        idPlantaField.textProperty().bindBidirectional(viewModel.idPlantaProperty(), new NumberStringConverter());
        idPlantaField.setEditable(false);

        denumireField = createTextField("Denumire");
        denumireField.textProperty().bindBidirectional(viewModel.denumireProperty());

        tipField = createTextField("Tip");
        tipField.textProperty().bindBidirectional(viewModel.tipProperty());

        specieField = createTextField("Specie");
        specieField.textProperty().bindBidirectional(viewModel.specieProperty());

        plantaCarnivoraCheck = new CheckBox("Carnivoră");
        plantaCarnivoraCheck.selectedProperty().bindBidirectional(viewModel.plantaCarnivoraProperty());

        imaginiField = createTextField("Imagini (URL)");
        imaginiField.textProperty().bindBidirectional(viewModel.imaginiProperty());

        // Folosim command pattern pentru operațiuni
        savePlantaButton = new Button("Salvează");
        savePlantaButton.setOnAction(e -> viewModel.getAddCommand().execute());

        deletePlantaButton = new Button("Șterge");
        deletePlantaButton.setOnAction(e -> viewModel.getDeleteCommand().execute());

        updatePlantaButton = new Button("Actualizează");
        updatePlantaButton.setOnAction(e -> viewModel.getUpdateCommand().execute());

        deleteOrUpdateIdField = createTextField("ID Plantă pentru ștergere/actualizare");
        deleteOrUpdateIdField.textProperty().bindBidirectional(viewModel.deleteOrUpdateIdProperty(), new NumberStringConverter());

        // Filtrare
        filterTipField = createTextField("Filtru tip");
        filterTipField.textProperty().bindBidirectional(viewModel.filtruTipProperty());

        filterSpecieField = createTextField("Filtru specie");
        filterSpecieField.textProperty().bindBidirectional(viewModel.filtruSpecieProperty());

        filterCarnivoraCheck = new CheckBox("Doar carnivore");
        filterCarnivoraCheck.selectedProperty().bindBidirectional(viewModel.filtruCarnivoraProperty());

        filterButton = new Button("Filtrează");
        filterButton.setOnAction(e -> viewModel.getFilterCommand().execute());

        loadAllButton = new Button("Încarcă toate plantele");
        loadAllButton.setOnAction(e -> viewModel.getLoadAllCommand().execute());
        exportCsvButton = new Button("Export CSV");
        viewModel.getExportCsvCommand().trigger.bind(exportCsvButton.pressedProperty());

        // Layout formular
        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(10));

        formGrid.add(createLabel("Denumire:"), 0, 0);
        formGrid.add(denumireField, 1, 0);

        formGrid.add(createLabel("Tip:"), 0, 1);
        formGrid.add(tipField, 1, 1);
        formGrid.add(createLabel("Specie:"), 2, 1);
        formGrid.add(specieField, 3, 1);

        formGrid.add(plantaCarnivoraCheck, 0, 2);
        formGrid.add(createLabel("Imagini (URL):"), 2, 2);
        formGrid.add(imaginiField, 3, 2);

        // Buton "Salvează"
        HBox saveBox = new HBox(10);
        saveBox.getChildren().addAll(savePlantaButton);
        saveBox.setPadding(new Insets(10));

        // Butoane ștergere/actualizare
        HBox actionBox = new HBox(10);
        actionBox.getChildren().addAll(deleteOrUpdateIdField, deletePlantaButton, updatePlantaButton);
        actionBox.setPadding(new Insets(10));

        // Căutare/filtrare
        HBox filterBox = new HBox(10);
        filterBox.getChildren().addAll(filterTipField, filterSpecieField, filterCarnivoraCheck, filterButton, loadAllButton,exportCsvButton);
        filterBox.setPadding(new Insets(10));

        root.getChildren().addAll(formGrid, saveBox, actionBox, filterBox, plantaTable);
    }

    public VBox getRoot() {
        return root;
    }

    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        return textField;
    }

    private Label createLabel(String text) {
        return new Label(text);
    }

    // Am eliminat metoda createButton care conținea apelul direct la comenzi
}