package com.agapovp.logfilesearcher.view;

import com.agapovp.logfilesearcher.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RootLayoutController implements Initializable {

    private MainApp mainApp;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void handleNew() {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        File file = directoryChooser.showDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.startSearch(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("LogFileSearcher");
        alert.setContentText("Author: Paul Agapov\nWebsite: https://github.com/p-agapov");

        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
