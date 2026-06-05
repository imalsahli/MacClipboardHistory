package org.example;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.geometry.Insets;


public class ClipboardApp extends Application {

    @Override
    public void start(Stage stage) {


        // This list will show all copied texts
        ListView<String> listView = new ListView<>();

      /*
      Label label = new Label("Mac Clipboard History");!?
      Scene scene = new Scene(label, 400, 300);
       */

        // To save/load data
        StorageService storageService = new StorageService();

        // Load saved history from fil
        ClipboardHistory history = storageService.load();

        // Service to read/write from macOS clipboard
        ClipboardService clipboardService = new ClipboardService();

        // Keeps the last copied text to avoid duplicates
        final String[] lastText = {""};

        for (ClipboardItem item : history.getAllItems()) {
            listView.getItems().add(item.getDisplayText());
        }

        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selectedText = cleanDisplayText(listView.getSelectionModel().getSelectedItem());
                if (selectedText != null) {
                    clipboardService.setCurrentText(selectedText);
                }
            }

        });

        // Check clipboard every 1 second
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    String currentText = clipboardService.getCurrentText();

                    // Save only new non-empty text
                    if (!currentText.isEmpty() && !currentText.equals(lastText[0])) {

                        boolean added = history.addText(currentText);

                        if (added) {
                            storageService.save(history);
                            listView.getItems().add(currentText);
                        }
                        lastText[0] = currentText;
                    }
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        Button deleteButton = new Button("Delete Selected");
        deleteButton.setOnAction(event -> {
            String selectedText = cleanDisplayText(listView.getSelectionModel().getSelectedItem());

            if (selectedText != null) {
                history.removeText(selectedText);
                storageService.save(history);
                refreshListView(listView, history);
            }
        });

        //Clear all Button
        Button clearButton = new Button("Clear All");

        clearButton.setOnAction(event -> {
            history.clearAll();
            storageService.save(history);

            refreshListView(listView, history);

        });

        //pinButton
        Button pinButton = new Button("Pin / Unpin");
        pinButton.setOnAction(event -> {
            String selectedText = cleanDisplayText(listView.getSelectionModel().getSelectedItem());

            if (selectedText != null) {
                history.togglePinned(selectedText);
                storageService.save(history);
                refreshListView(listView, history);
            }
        });

        // VBox root = new VBox(deleteButton, clearButton, pinButton, listView);
        //HBox root = new HBox(deleteButton, clearButton, pinButton, listView);
        HBox buttonBar = new HBox(10, deleteButton, clearButton, pinButton);
        buttonBar.setAlignment(Pos.CENTER);
        VBox root = new VBox(10, buttonBar, listView);
        root.setPadding(new Insets(10));
        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("Clipboard History");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void refreshListView(ListView<String> listView, ClipboardHistory history) {
        listView.getItems().clear();

        for (ClipboardItem item : history.getAllItems()) {
            listView.getItems().add(item.getDisplayText());
        }
    }

    private String cleanDisplayText(String text) {
        if (text == null) {
            return null;
        }

        if (text.startsWith("📌 ")) {
            return text.substring(3);
        }

        return text;
    }

    public static void main(String[] args) {
        launch(args);
    }
}