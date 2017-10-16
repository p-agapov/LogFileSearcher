package com.agapovp.logfilesearcher.view;


import com.agapovp.logfilesearcher.MainApp;
import com.agapovp.logfilesearcher.model.FileItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchOverviewController implements Initializable {

    @FXML
    private AnchorPane leftPane;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private Text folderPath;

    @FXML
    private TextField extension;

    @FXML
    private TextField content;

    @FXML
    private TextArea fileContent;

    @FXML
    private Text statusBar;

    private MainApp mainApp;

    public SearchOverviewController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Text text = new Text();
        text.setText("Please select folder\nand file extension.");

        AnchorPane.setLeftAnchor(text, 20.0);
        AnchorPane.setTopAnchor(text, 20.0);
        leftPane.getChildren().add(text);
    }

    public void updateTreeView(TreeView<FileItem> treeView, boolean isEmpty, int totalFilesFounded) {
        if (!isEmpty) {
            AnchorPane.setLeftAnchor(treeView, 0.0);
            AnchorPane.setTopAnchor(treeView, 0.0);
            AnchorPane.setRightAnchor(treeView, 0.0);
            AnchorPane.setBottomAnchor(treeView, 0.0);
            leftPane.getChildren().clear();
            leftPane.getChildren().add(treeView);
            statusBar.setText(totalFilesFounded + " files found");

            treeView.getSelectionModel().selectedItemProperty().
                    addListener((observable, oldValue, newValue) -> {
                        if (newValue != null && !newValue.getValue().getFile().isDirectory()) {
                            mainApp.browseFile(newValue.getValue().getFile());
                        }
                    });

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result");
            alert.setHeaderText("Search complete");
            alert.setContentText("Files found: " + totalFilesFounded);

            alert.showAndWait();
        } else {
            Text text = new Text();
            text.setText("Please select folder\nand file extension.");

            AnchorPane.setLeftAnchor(text, 20.0);
            AnchorPane.setTopAnchor(text, 20.0);
            leftPane.getChildren().clear();
            leftPane.getChildren().add(text);
            statusBar.setText("no matches found");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Result");
            alert.setHeaderText("No matches found");
            alert.setContentText("Nothing to show.\nMake another request");

            alert.showAndWait();
        }
    }


    public Text getFolderPath() {
        return folderPath;
    }

    public TextField getExtension() {
        return extension;
    }

    public TextField getContent() {
        return content;
    }

    public AnchorPane getLeftPane() {
        return leftPane;
    }

    public AnchorPane getRightPane() {
        return rightPane;
    }

    public TextArea getFileContent() {
        return fileContent;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
}
