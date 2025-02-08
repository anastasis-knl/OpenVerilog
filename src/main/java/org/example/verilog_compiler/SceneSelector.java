package org.example.verilog_compiler;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.verilog_compiler.EditorScene.Editor_Scene;
import org.example.verilog_compiler.SelectorScene.Selector_Scene;

import java.io.File;
import java.io.IOException;

public  class SceneSelector {
    static Stage primary_stage ;
    Scene Selector ;
    static Scene Editor ;
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
    void launch_Selector() {
        if(Editor != null) {
            editor_shutdown_routine();
        }
        // create selection_scene

        this.Selector = new Selector_Scene(new VBox(),primary_stage) ;
        primary_stage.setScene(Selector);
        primary_stage.show() ;



    }

    // launch the program editor screen

    public static void launch_Editor(File dir ) throws IOException {

        Editor = new Editor_Scene(new VBox(),primary_stage,dir) ;
        primary_stage.setScene(Editor);
    }

    public static Stage get_primary_stage(){
        return  primary_stage;
    }
}
