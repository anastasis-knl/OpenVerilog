package org.example.verilog_compiler;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
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
    public static  SceneSelector get_controller(){
        if (controller  == null){
            controller = new SceneSelector() ;
        }
        return controller ;
    }
    void setPrimaryStage(Stage primary_stage_t){
        primary_stage = primary_stage_t;
    }
    void editor_shutdown_routine(){
        return  ;
    }

    void launch_Selector() {
        if(Editor != null) {
            editor_shutdown_routine();
        }
        // create selection_scene

        this.Selector = new Selector_Scene(new VBox(),primary_stage) ;
        primary_stage.setScene(Selector);
        primary_stage.show() ;



    }
    public static void launch_Editor(File dir ) throws IOException {

        Editor = new Editor_Scene(new VBox(),primary_stage,dir) ;
        primary_stage.setScene(Editor);
    }

}
