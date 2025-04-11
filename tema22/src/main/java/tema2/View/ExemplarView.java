package tema2.View;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.converter.NumberStringConverter;
import tema2.ViewModel.ExemplarVM;

public class ExemplarView {
    private final ExemplarVM viewModel;
    private final VBox root;
    private final TableView<String[]> exemplarTable;

    private final TextField idExemplarField, imagineField, zonaGradinaField, idPlantaField;

    private final Button saveExemplarButton, deleteExemplarButton, updateExemplarButton;

    private final TextField deleteOrUpdateIdField;

    private final TextField filterZonaGradinaField, filterSpecieField;
    private final Button filterButton, loadAllButton;

    public ExemplarView() {
        viewModel = new ExemplarVM();
        root = new VBox(10);
        root.setPadding(new Insets(10));

        exemplarTable = new TableView<>();
        exemplarTable.setPrefWidth(800);
        exemplarTable.setItems(viewModel.getExemplarList());

        exemplarTable.getColumns().addAll(viewModel.getTableColumns());

        idExemplarField = createTextField("ID Exemplar");
        idExemplarField.textProperty().bindBidirectional(viewModel.idProperty(), new NumberStringConverter());
        idExemplarField.setEditable(false);

        imagineField = createTextField("Imagine (URL)");
        imagineField.textProperty().bindBidirectional(viewModel.imagineProperty());

        zonaGradinaField = createTextField("Zona Grădină");
        zonaGradinaField.textProperty().bindBidirectional(viewModel.zonaProperty());

        idPlantaField = createTextField("ID Plantă");
        idPlantaField.textProperty().bindBidirectional(viewModel.plantaIdProperty(), new NumberStringConverter());

        // Folosim command pattern pentru operațiuni
        saveExemplarButton = new Button("Salvează");
        saveExemplarButton.setOnAction(e -> viewModel.getAddCommand().execute());

        deleteExemplarButton = new Button("Șterge");
        deleteExemplarButton.setOnAction(e -> viewModel.getDeleteCommand().execute());

        updateExemplarButton = new Button("Actualizează");
        updateExemplarButton.setOnAction(e -> viewModel.getUpdateCommand().execute());

        deleteOrUpdateIdField = createTextField("ID Exemplar pentru ștergere/actualizare");
        deleteOrUpdateIdField.textProperty().bindBidirectional(viewModel.deleteOrUpdateIdProperty(), new NumberStringConverter());

        // Filtrare
        filterZonaGradinaField = createTextField("Filtru zona grădină");
        filterZonaGradinaField.textProperty().bindBidirectional(viewModel.filtruZonaProperty());

        filterSpecieField = createTextField("Filtru specie");
        filterSpecieField.textProperty().bindBidirectional(viewModel.filtruSpecieProperty());

        filterButton = new Button("Filtrează");
        filterButton.setOnAction(e -> viewModel.getFilterCommand().execute());

        loadAllButton = new Button("Încarcă toate exemplarele");
        loadAllButton.setOnAction(e -> viewModel.getLoadAllCommand().execute());

        // Layout formular
        GridPane formGrid = new GridPane();
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setPadding(new Insets(10));

        formGrid.add(createLabel("Imagine (URL):"), 0, 0);
        formGrid.add(imagineField, 1, 0);

        formGrid.add(createLabel("Zona Grădină:"), 0, 1);
        formGrid.add(zonaGradinaField, 1, 1);

        formGrid.add(createLabel("ID Plantă:"), 0, 2);
        formGrid.add(idPlantaField, 1, 2);

        // Buton "Salvează"
        HBox saveBox = new HBox(10);
        saveBox.getChildren().addAll(saveExemplarButton);
        saveBox.setPadding(new Insets(10));

        // Butoane ștergere/actualizare
        HBox actionBox = new HBox(10);
        actionBox.getChildren().addAll(deleteOrUpdateIdField, deleteExemplarButton, updateExemplarButton);
        actionBox.setPadding(new Insets(10));

        // Căutare/filtrare
        HBox filterBox = new HBox(10);
        filterBox.getChildren().addAll(filterZonaGradinaField, filterSpecieField, filterButton, loadAllButton);
        filterBox.setPadding(new Insets(10));

        root.getChildren().addAll(formGrid, saveBox, actionBox, filterBox, exemplarTable);
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
}