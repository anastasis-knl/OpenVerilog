package org.example.verilog_compiler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.verilog_compiler.EditorScene.ControllerClass_Editor_main;
import org.example.verilog_compiler.EditorScene.Editor_Scene;
import javafx.scene.Parent;
import org.example.verilog_compiler.SelectorScene.ControllerClass_Selector_main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public  class SceneSelector {
    static Stage primary_stage ;

    Scene Selector ;
    Scene Editor;

    ControllerClass_Selector_main login_controller ;
    ControllerClass_Editor_main editor_controller;

    File root_dir ;

    // static class global one instance of master scene selector
    private static SceneSelector controller ;
    private SceneSelector(){}  ;

    //  Scene selector is a static class it is meant to be run in the background
    // get controller is done by any class in order to change screens
    public static  SceneSelector get_controller(){
        if (controller  == null){
            controller = new SceneSelector() ;
        }
        return controller ;
    }


    void setPrimaryStage(Stage primary_stage_t){
        primary_stage = primary_stage_t;
    }

    // not implemented
    // in case you want to open new project while inside the editor of another project
    void editor_shutdown_routine(){
        return  ;
    }


    // at the beginning of the program to launch the folder/ project selection screen
    // works ok at this moment i don't know if it works on already running editor check
    void launch_Selector() throws IOException {

        if(Editor != null) {
            editor_shutdown_routine();
        }
        // create selection_scene

        //Parent selector = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SelectorScene/SelectionScene_main.fxml")));

        File fxmlFilemain = new File("src/main/java/org/example/verilog_compiler/SelectorScene/SelectionScene_main.fxml");
        URL fxmlUrlmain = fxmlFilemain.toURI().toURL();

        FXMLLoader login_loader = new FXMLLoader(fxmlUrlmain);

        Parent selector = login_loader.load() ;

        ControllerClass_Selector_main login_controller = login_loader.getController() ;

        // set up login_controller for later use for data mining
        this.login_controller = login_controller ;

        this.Selector = new Scene(selector) ;
        primary_stage.setScene(Selector);
        primary_stage.show() ;



    }

    // launch the program editor screen

    public void launch_Editor(File dir ) throws IOException {

        // set up project directory
        root_dir= this.login_controller.getProjectDirectory() ;


        File fxmlFilemain = new File("src/main/java/org/example/verilog_compiler/EditorScene/TextEditorScene.fxml");
        URL fxmlUrlmain = fxmlFilemain.toURI().toURL();

        // get loader for fxml file
        FXMLLoader loader = new FXMLLoader(fxmlUrlmain);

        // load fxml file
        Parent editor = loader.load() ;

        // get controller class instance object
        ControllerClass_Editor_main controller = loader.getController();

        this.Editor = new Scene(editor) ;

        // here go initialization commands for the editor

        controller.init_editor(root_dir, SceneSelector.controller);

        // ControllerClass_Editor_main controller = (FXMLLoader)editor.getController();
        primary_stage.setScene(Editor);
        primary_stage.show() ;



    }

    public static Stage get_primary_stage(){
        return  primary_stage;
    }

    public ControllerClass_Editor_main getEditor(){return this.editor_controller; }

}
















































