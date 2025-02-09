package org.example.verilog_compiler;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.verilog_compiler.EditorScene.Editor_Scene;
import javafx.scene.Parent;
import java.io.File;
import java.io.IOException;
import java.net.URL;

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
    // works ok at this moment i don't know if it works on already running editor check
    void launch_Selector() throws IOException {

        if(Editor != null) {
            editor_shutdown_routine();
        }
        // create selection_scene

        //Parent selector = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/SelectorScene/SelectionScene_main.fxml")));
        File fxmlFilemain = new File("src/main/java/org/example/verilog_compiler/SelectorScene/SelectionScene_main.fxml");
        URL fxmlUrlmain = fxmlFilemain.toURI().toURL();

        Parent selector = FXMLLoader.load(fxmlUrlmain);
        this.Selector = new Scene(selector) ;
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
