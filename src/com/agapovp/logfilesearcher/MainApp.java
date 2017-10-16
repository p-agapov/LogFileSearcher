package com.agapovp.logfilesearcher;

import com.agapovp.logfilesearcher.model.FileItem;
import com.agapovp.logfilesearcher.util.SearchUtil;
import com.agapovp.logfilesearcher.view.RootLayoutController;
import com.agapovp.logfilesearcher.view.SearchOverviewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private SearchOverviewController searchOverviewController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("LogFileSearcher");
        this.primaryStage.getIcons().add(new Image("file:resources/images/logfilesearcher_128.png"));


        initRootLayout();
        showSearchOverview();
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSearchOverview() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SearchOverview.fxml"));
            AnchorPane searchOverview = loader.load();

            rootLayout.setCenter(searchOverview);

            searchOverviewController = loader.getController();
            searchOverviewController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void startSearch(File startFolder) {
        if (searchOverviewController.getContent().getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Pattern field is empty");
            alert.setContentText("Please fill in reg exp field\nand try again");

            alert.showAndWait();
            return;
        }
        SearchUtil searchUtil = new SearchUtil(
                startFolder,
                searchOverviewController.getExtension().getText(),
                searchOverviewController.getContent().getText());
        TreeView<FileItem> treeView = searchUtil.getFilesTree();
        searchOverviewController.getFolderPath().setText(startFolder.getPath());
        searchOverviewController.updateTreeView(
                treeView, searchUtil.isEmpty(), searchUtil.getTotalFilesFounded());
    }

    public void browseFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder builder = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                builder.append(line);
                builder.append('\n');
            }
            reader.close();
            searchOverviewController.getFileContent().setText(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
