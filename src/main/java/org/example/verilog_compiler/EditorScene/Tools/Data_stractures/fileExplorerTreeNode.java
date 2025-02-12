package org.example.verilog_compiler.EditorScene.Tools.Data_stractures;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import org.example.verilog_compiler.EditorScene.TabController;
import org.example.verilog_compiler.GlobalSceneController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.example.verilog_compiler.EditorScene.ControllerClass_Editor_main;

public class fileExplorerTreeNode {

    private VBox FE;
    // every file or directory under this directory button
    List<fileExplorerTreeNode> children;

    Button button;
    directoryTreeNode file ;
    TabPane editorTabs;
    // it is needed because based on this a file will open on press down

    Boolean isDir ;
    Boolean hidden ;
    String name ;
    int level ;

    public fileExplorerTreeNode(String name, int level, VBox fileExplorer, Boolean isDir, directoryTreeNode file
    , TabPane editorTabs){

        this.name = name ;
        this.level = level ;
        this.isDir = isDir ;
        this.hidden = false ;
        this.file = file ;
        this.editorTabs = editorTabs ;
        this.FE = fileExplorer ;
        this.children = new LinkedList<>() ;

        button = new Button() ;

        // format text of the button
        // for each dir
        String txt = " " ;
        if (this.isDir){

            for (int i = 0 ; i < this.level-4 ; i++) {
                // proper indent for level
                txt = txt + " ";
            }
            if (level!=0) {
                txt += "|___";
            }
        }else{
            // if not dir
            for (int i = 0 ; i < this.level-2; i++) {
                // proper indent for level
                txt = txt + " ";
            }
            txt += "|_" ;
        }
        txt += this.name ;
        button.setText(txt);

        // set what to do on press

        this.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isDir) {
                    if (hidden) {
                        show_children() ;
                    }else{
                        hide_children()  ;
                    }

                }else {

                    try {
                        open_file_to_text_editor() ;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        fileExplorer.getChildren().add(this.button) ;
    }

    //need for communication with the editor
    // in order to each time create new tab divider, line seperator , scrollable text pane and text area

    private void open_file_to_text_editor() throws IOException {
        if (!(existingTab())) {
            // add new tab


            Tab tab = new Tab() ;
            tab.setText(this.name);

            // style of the tabs
            File tabfileContent = new File("src/main/resources/fxmlGraphics/tabConfiguration.fxml") ;

            FXMLLoader loader = new FXMLLoader(tabfileContent.toURL());

            Region tabContent = loader.load();  // Load the content from FXML
            tab.setContent(tabContent);

            // Optionally, you can get the controller and modify it
            // this shit needs to be put on a seperate data stream in order to be accessed while onteh fly
            TabController controller = loader.getController();

            // add statically for global access later probably bad way but ok boomer
            ControllerClass_Editor_main.addTabController(tab, controller);

            GlobalSceneController.get_controller().getEditor().getOpenTabs().put(this.file.getTempFileInstance(),tab);
            // You can now access the controller's methods if needed.

            controller.write(this.file.getTempFileInstance());
            // Add the tab to the TabPane on the editor
            editorTabs.getTabs().add(tab);



        }
    }

    // change if it already exists
    private Boolean existingTab() {

        HashMap<File , Tab> map = GlobalSceneController.get_controller().getEditor().getOpenTabs() ;

        if (map.containsKey(this.file.getTempFileInstance())) {
            // move active tab to this asked for tab

            editorTabs.getSelectionModel().select(map.get(this.file.getTempFileInstance()));
            return true;
        }
        // create tab if not existing
        return false ;

    }


    // it works form here on

    public void add_child(fileExplorerTreeNode child ){
        this.children.add(child) ;
    }

    // show every child
    private void show_children(){
        for (fileExplorerTreeNode child : children) {
            child.show();
        }
        this.hidden = false ;
    }

    // hide all children of a directory
    // called without prior hide only by the <root> node
    public void hide_children() {
        for (fileExplorerTreeNode child : children) {
            child.hide();
        }
        this.hidden = true  ;

    }

    protected void show() {
        this.hidden = false ;
        this.button.setVisible(true);
        this.button.setManaged(true);
        this.FE.requestLayout();
    }

    protected void hide() {
        if (isDir) {
            // hide all the children
            hide_children();

        }
        // hide itself
        this.button.setVisible(false);
        this.hidden = true ;
        this.button.setManaged(false );

    }

    public VBox getFE(){ return this.FE;};

    public TabPane getEditorTabs() {
        return editorTabs;
    }

    public int getLevel(){return this.level ; } ;
}





























