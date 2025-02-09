package org.example.verilog_compiler.EditorScene;

import java.io.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.* ;
import javafx.scene.layout.VBox;
import org.example.verilog_compiler.SceneSelector ;

public class ControllerClass_Editor_main {
    File dir ;

    @FXML
    Button Run ;
    @FXML
    Button Compile;
    @FXML
    Button Settings ;
    @FXML
    ToolBar tools ;
    @FXML
    TextArea console ;
    @FXML
    TabPane editorTabs ;
    @FXML
    VBox FileExplorer;

    // file , tabname
    private static HashMap<File, Tab> openTabs;

    File rootDir ;
    SceneSelector globalSceneController ;

    public void init_editor(File root_dir, SceneSelector controller){
        this.rootDir = root_dir ;
        this.globalSceneController = controller ;
        init_fileExplorer() ;
    }

    private void init_fileExplorer(){
        // add buttons on the vbox with correct indentation
        // based on the file tree create the file explorer tree and hang into the vbox of the file explorer


        return ;
    }
    public HashMap<File,Tab> getOpenTabs(){return openTabs; } ;
    private void open_new_tab(){} ;




}













































