package org.example.verilog_compiler.EditorScene;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.* ;
import javafx.scene.layout.VBox;
import org.example.verilog_compiler.EditorScene.Tools.Data_stractures.directoryTreeNode;
import org.example.verilog_compiler.EditorScene.Tools.Data_stractures.fileExplorerTreeNode;
import org.example.verilog_compiler.SceneSelector ;
import java.nio.file.StandardCopyOption;

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

    @FXML
    Button Save ;

    // file , tabname
    private static HashMap<File, Tab> openTabs = new HashMap<>();
    private static HashMap<Tab,TabController> tabControllers= new HashMap<>() ;

    File rootDir ;
    SceneSelector globalSceneController ;
    private fileExplorerTreeNode rootNodeFE;
    private directoryTreeNode rootNodeDir ;


    public void init_editor(File root_dir, SceneSelector controller) throws IOException {
        this.rootDir = root_dir ;
        this.globalSceneController = controller ;
        init_fileExplorer() ;


        Save.setOnAction(new SaveEventHandler());


    }

    private void savetoTempAll(){return; }

    private void saveToTemp(File temp){
        Tab tb  = ControllerClass_Editor_main.openTabs.get(temp);
        TextArea ta = ControllerClass_Editor_main.getTabController().get(tb).textEditor ;

        //get text from text area
        String s = ta.getText();
        // save to current temp file

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {
            writer.write(s);
            writer.newLine(); // Adds a new line
            System.out.println("File written successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    private void init_fileExplorer() throws IOException {
        // get the file root folder from the root dir
        directoryTreeNode root = scene_tools.getDirectoryTree(this.rootDir) ;
        fileExplorerTreeNode rootFE = new fileExplorerTreeNode(root.getName(),0 , FileExplorer , true , root , editorTabs)  ;
        scene_tools.createFileExplorer(root , rootFE);

        this.rootNodeDir = root ;
        this.rootNodeFE = rootFE ;
        return ;
    }


    public HashMap<File,Tab> getOpenTabs(){return openTabs; } ;
    private void open_new_tab(){} ;


    private class SaveEventHandler implements EventHandler<ActionEvent> {
        // save to the real file

        @Override
        public void handle(ActionEvent event) {

            Tab selectedTab = editorTabs.getSelectionModel().getSelectedItem();
            File fileTemp = findtempOnHashMap(selectedTab) ;

            // save
            saveToTemp(fileTemp);

            directoryTreeNode FileReal = directoryTreeNode.searchByTemp(rootNodeDir , fileTemp );
            // Using Real File location we write to it by the temp file reverse cop

            copyTempToFile(fileTemp , FileReal.getGetFileInstance());

        }

        private void copyTempToFile(File temp, File real ) {
            try {
                Files.copy(temp.toPath(), real.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // based on active tab return temp file
        private File findtempOnHashMap(Tab tb){
            for (File fl : openTabs.keySet()){
                if (tb == openTabs.get(fl)){
                    return fl ;
                }
            }
            return null;
        }
    }

    public static HashMap<Tab, TabController> getTabController(){
        return ControllerClass_Editor_main.tabControllers ;
    }
    public static void addTabController(Tab tb, TabController cntrl){
        ControllerClass_Editor_main.tabControllers.put(tb, cntrl) ;
    }

}













































