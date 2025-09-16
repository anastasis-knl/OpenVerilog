package org.verilog_compiler.EditorScene;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.* ;
import javafx.scene.layout.VBox;
import org.verilog_compiler.EditorScene.CandR.Compile;
import org.verilog_compiler.EditorScene.Tools.Data_stractures.directoryTreeNode;
import org.verilog_compiler.EditorScene.Tools.Data_stractures.fileExplorerTreeNode;
import org.verilog_compiler.GlobalSceneController;

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
    GlobalSceneController globalSceneController ;
    private fileExplorerTreeNode rootNodeFE;
    private directoryTreeNode rootNodeDir ;


    public void init_editor(File root_dir, GlobalSceneController controller) throws IOException {
        this.rootDir = root_dir ;
        this.globalSceneController = controller ;
        init_fileExplorer() ;


        Save.setOnAction(new SaveEventHandler());
        Compile.setOnAction(new CompileEventHandler());
        Run.setOnAction(new RunEventHandler());
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
        // delete the conetnts of fileList.txt ;
        scene_tools.clearFileListfile();

        // get the file root folder from the root dir
        directoryTreeNode root = scene_tools.getDirectoryTree(this.rootDir) ;
        fileExplorerTreeNode rootFE = new fileExplorerTreeNode(root.getName(),0 , FileExplorer , true , root , editorTabs)  ;
        scene_tools.createFileExplorer(root , rootFE);

        this.rootNodeDir = root ;
        this.rootNodeFE = rootFE ;
    }

    public TextArea getConsole(){return this.console;} ;


    public HashMap<File,Tab> getOpenTabs(){return openTabs; } ;

    private void open_new_tab(){} ;

    // based on active tab return temp file
    private File findtempOnHashMap(Tab tb){
        for (File fl : openTabs.keySet()){
            if (tb == openTabs.get(fl)){
                return fl ;
            }
        }
        return null;
    }

    private class RunEventHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            // call the command to get output
            try {
                org.verilog_compiler.EditorScene.CandR.Compile.run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // call the new stage and dispaly the data back to the client

        }
    }
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


    }
    private void copyTempToFile(File temp, File real ) {
        try {
            Files.copy(temp.toPath(), real.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class CompileEventHandler implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {

            // check if your root is the current tab go to current tab and specify as root that one shit
            Tab selectedTab = editorTabs.getSelectionModel().getSelectedItem();
            File fileTemp = findtempOnHashMap(selectedTab) ;
            // the temp now is the same as the real we just need the module name the second word of the program without
            // the ; at the end
            directoryTreeNode FileReal = directoryTreeNode.searchByTemp(rootNodeDir , fileTemp );


            String moduleName = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(fileTemp))) {
                moduleName = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] words = moduleName.split(" ");

            // correct find modulef
            moduleName = words[1] ;
            if (moduleName.endsWith(";")){
                moduleName = moduleName.substring(0,moduleName.length()-1) ;
            }


            // compile EVERYTHING with specified root using compile class and compile text thing
            Compile compiler = new Compile();
            try {
                compiler.compile(moduleName,FileReal);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // change the .v back to how it was, from the .temp file
            copyTempToFile(fileTemp , FileReal.getGetFileInstance()) ;

        }
    }

    public static HashMap<Tab, TabController> getTabController(){
        return ControllerClass_Editor_main.tabControllers ;
    }

    public static void addTabController(Tab tb, TabController cntrl){
        ControllerClass_Editor_main.tabControllers.put(tb, cntrl) ;
    }

}













































