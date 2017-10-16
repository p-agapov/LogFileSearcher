package com.agapovp.logfilesearcher.util;


import com.agapovp.logfilesearcher.model.FileItem;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class SearchUtil {

    private boolean empty;
    private int totalFilesFounded;

    private String fileExtension;
    private String stringToSearch;
    private TreeView<FileItem> filesTree;


    public SearchUtil(File startDir, String fileExtension, String stringToSearch) {
        this.fileExtension = fileExtension;
        this.stringToSearch = stringToSearch;
        this.filesTree = new TreeView<>();
        totalFilesFounded = 0;

        empty = !findFiles(startDir, null);
    }

    public boolean isEmpty() {
        return empty;
    }

    public TreeView<FileItem> getFilesTree() {
        return filesTree;
    }

    public int getTotalFilesFounded() {
        return totalFilesFounded;
    }

    private boolean findFiles(File dir, TreeItem<FileItem> parent) {

        boolean isFound = false;
        TreeItem<FileItem> root = new TreeItem<>(new FileItem(dir));
        root.setExpanded(true);

        try {
            File[] files;
            if (dir.listFiles() == null) {
                return false;
            }
            files = dir.listFiles();
            File file;
            Pattern pattern = Pattern.compile(stringToSearch);
            for (int i = 0; i < files.length; i++) {
                if (files[i] == null) {
                    continue;
                }
                file = files[i];
                if (file.isDirectory()) {
                    //System.out.println("directory:" + file.getCanonicalPath());
                    if (findFiles(file,root)) {
                        isFound = true;
                    }
                } else if (file.getName().endsWith(fileExtension)){
                    Scanner scanner = new Scanner(file);
                    String result = scanner.findWithinHorizon(pattern, 0);
                    if (result != null) {
                        totalFilesFounded++;
                        isFound = true;
                        //System.out.println("     file:" + file.getCanonicalPath());
                        root.getChildren().add(new TreeItem<>(new FileItem(file)));
                    }
                } else {
                    continue;
                }
            }
            if (isFound && parent == null) {
                this.filesTree.setRoot(root);
            } else if (isFound) {
                parent.getChildren().add(root);
            }
        } catch (PatternSyntaxException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong pattern string");
            alert.setContentText("Please use correct pattern" +
                    "\nRead: https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html");

            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isFound;
    }
}
