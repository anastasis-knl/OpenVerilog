package org.example.verilog_compiler.WaveViewer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.example.verilog_compiler.EditorScene.ControllerClass_Editor_main;
import org.example.verilog_compiler.GlobalSceneController;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.TimeLine;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.Timelines;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ControllerClass_WaveViewer_main {

    GlobalSceneController controller ;

    // similar style and view as the file explorer
    // when pressing also + show vars

    VBox moduleExplorer ;

    // create all possible buttons
    // show or hide them depending on case
    VBox vars ;

    HashMap<String, Button> varsButtons ;

    public ControllerClass_WaveViewer_main(){
        this.controller = GlobalSceneController.get_controller()  ;
        // call root module to create the buttons
        varsButtons  = new LinkedHashMap<>() ;

        this.init_vars() ;

    }

    private void init_vars() {
        // create all the buttons for every variable without name ?
        Timelines tmln = this.controller.getDataExtractor().getTimelines();
        HashMap<String, TimeLine> vars = tmln.getTimelines() ;

        for (String key : vars.keySet()){
            Button btn = new Button() ;
            this.varsButtons.put(key , btn) ;

            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    // paint waveform
                }
            });

            this.vars.getChildren().add(btn) ;


        }


    }

    public void showVar(String Symbol , String name) {
        this.varsButtons.get(Symbol).setText(name);
        this.varsButtons.get(Symbol).setVisible(true);
        this.varsButtons.get(Symbol).setManaged(true );

    }

    public void hideAllVars() {
        for (String key : this.varsButtons.keySet()) {
            this.varsButtons.get(key).setVisible(false);
            this.varsButtons.get(key).setManaged(false);
        }
    }

    public VBox getModuleExplorer(){
        return this.moduleExplorer;
    }

    public VBox getVars(){
        return this.vars ;
    }

}
