package org.example.verilog_compiler.EditorScene.Tools.Data_stractures;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import org.example.verilog_compiler.EditorScene.ControllerClass_Editor_main;
import org.example.verilog_compiler.EditorScene.TabController;
import org.example.verilog_compiler.SceneSelector;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

public class fileExplorerTreeNode {

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
        this.hidden = true ;
        this.file = file ;
        this.editorTabs = editorTabs ;

        button = new Button() ;

        // format text of the button
        String txt = " " ;
        for (int i = 0 ; i < this.level ; i++) {
            txt = txt + " ";
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

            File tabfile = new File("src/main/resources/fxmlGraphics/tabConfiguration.fxml") ;

            FXMLLoader loader = new FXMLLoader(tabfile.toURL());

            Region tabContent = loader.load();  // Load the content from FXML
            tab.setContent(tabContent);

            // Optionally, you can get the controller and modify it
            TabController controller = loader.getController();
            // You can now access the controller's methods if needed.

            // Add the tab to the TabPane on the editor
            editorTabs.getTabs().add(tab);



        }
    }

    // change if it already exists
    private Boolean existingTab() {
        HashMap<File , Tab> map = SceneSelector.get_controller().getEditor().getOpenTabs() ;
        if (map.containsKey(this.file.getRelative_path())) {
            // move active tab to this asked for tab
            editorTabs.getSelectionModel().select(map.get(this.file.getRelative_path()));
            return true;
        }
        // create tab if not existing
        return false ;

    }


    // show every child
    private void show_children(){
        for (fileExplorerTreeNode child : children) {
            child.show();
        }

    }

    // hide all children of a directory
    // called without prior hide only by the <root> node
    public void hide_children() {
        for (fileExplorerTreeNode child : children) {
            child.hide();
        }

    }

    protected void show() {
        this.button.setVisible(true);
    }

    protected void hide() {
        if (isDir) {
            // hide all the children
            hide_children();

        }
        // hide itself
        this.button.setVisible(false);

    }






}





























