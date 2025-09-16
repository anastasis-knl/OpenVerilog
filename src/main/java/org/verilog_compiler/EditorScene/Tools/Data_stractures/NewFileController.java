package org.verilog_compiler.EditorScene.Tools.Data_stractures;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;


public class NewFileController {

    @FXML
    TextField nameArea;

    @FXML
    Button buttonOk ;

    fileExplorerTreeNode FilePressed ;

    void initButton(fileExplorerTreeNode btn ) {
        this.FilePressed = btn;
    }


    @FXML
    public void initialize() {
        buttonOk.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = nameArea.getText() ;
                String absPath = FilePressed.getDirNode().getGetFileInstance().getAbsolutePath() ;

                // create the file
                directoryTreeNode nd = new directoryTreeNode(absPath  , name) ;
                FilePressed.getDirNode().addFileNode(nd);


                // create the File Explorer Node;
                int level ;
                if (FilePressed.isDir) {
                    level = FilePressed.getLevel() + 3  ;
                    try {
                        nd.setRelativePath(FilePressed.getDirNode().getRelativeTempPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    level = FilePressed.getLevel();
                    try {
                        nd.setRelativePath(FilePressed.getDirNode().getRelativeTempPath()  );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                VBox fe = FilePressed.getFE()  ;
                TabPane et  = FilePressed.getEditorTabs() ;

                fileExplorerTreeNode tnd = new fileExplorerTreeNode(name , level ,fe , false , nd , et) ;

            }
        });
    }
}
