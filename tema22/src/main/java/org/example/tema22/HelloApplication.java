package org.example.tema22;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import tema2.View.ExemplarView;
import tema2.View.PlantaView;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        // Create main container
        BorderPane mainLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10));

        // Create header
        Label headerLabel = new Label("Grădina Botanică - Sistem de Management");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        headerLabel.setPadding(new Insets(0, 0, 10, 0));

        // Create plants section
        VBox plantsSection = new VBox(5);
        Label plantsHeader = new Label("Gestionare Plante");
        plantsHeader.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        plantsHeader.setPadding(new Insets(0, 0, 5, 0));

        PlantaView plantaView = new PlantaView();
        plantsSection.getChildren().addAll(plantsHeader, plantaView.getRoot());

        // Create specimens section
        VBox specimensSection = new VBox(5);
        Label specimensHeader = new Label("Gestionare Exemplare");
        specimensHeader.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        specimensHeader.setPadding(new Insets(15, 0, 5, 0));

        ExemplarView exemplarView = new ExemplarView();
        specimensSection.getChildren().addAll(specimensHeader, exemplarView.getRoot());

        // Combine all sections in a vertical layout
        VBox contentLayout = new VBox(10);
        contentLayout.getChildren().addAll(headerLabel, plantsSection, specimensSection);

        // Add scrolling capability for better UX
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(contentLayout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPannable(true);

        mainLayout.setCenter(scrollPane);

        // Create and show the scene
        Scene scene = new Scene(mainLayout, 950, 800);
        stage.setTitle("Grădina Botanică - Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
