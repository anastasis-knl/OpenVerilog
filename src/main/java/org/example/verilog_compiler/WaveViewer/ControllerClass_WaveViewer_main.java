package org.example.verilog_compiler.WaveViewer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.verilog_compiler.EditorScene.ControllerClass_Editor_main;
import org.example.verilog_compiler.GlobalSceneController;
import org.example.verilog_compiler.WaveViewer.Simulator.Graph;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.TimeLine;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.Timelines;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ControllerClass_WaveViewer_main {

    GlobalSceneController controller ;

    // similar style and view as the file explorer
    // when pressing also + show vars

    @FXML
    Button Zoomin ;

    @FXML
    Button Zoomout ;

    @FXML
    VBox moduleExplorer ;

    // create all possible buttons
    // show or hide them depending on case
    @FXML
    VBox vars ;

    @FXML
    VBox graphContainer ;

    @FXML
    VBox graphNameContainer;

    @FXML
    Canvas canvas;
    HashMap<String, Button> varsButtons ;


    Integer  times ;

    // can't call init in constructor because the containers haven't been initialized by the loader at that point
    @FXML
    public void initialize() {
        this.controller = GlobalSceneController.get_controller()  ;
        // call root module to create the buttons
        varsButtons  = new LinkedHashMap<>() ;

        Float timescale = GlobalSceneController.get_controller().getDataExtractor().getTimescale() ;
        this.times = (int) (GlobalSceneController.get_controller().getDataExtractor().max_time / timescale);
        drawRuler(timescale) ;

        this.init_vars();

        this.Zoomout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // clear canvas ;
                canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                LinkedList<Graph> Grlst = GlobalSceneController.get_controller().getGraphs();

                Float tmscl = GlobalSceneController.get_controller().getDataExtractor().getTimescale();
                // create new thingy with numbers
                drawRuler(tmscl*2) ;
                GlobalSceneController.get_controller().getDataExtractor().setTimeScale(tmscl*2);

                for (Graph gr : Grlst) {


                    gr.changeZoom(GlobalSceneController.get_controller().getDataExtractor().getTimescale());

                }

            }
        });
        this.Zoomin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                zoomIn() ;
            }

        });
        zoomIn();
        zoomIn();
        zoomIn();
        zoomIn();
        zoomIn();
        zoomIn();

    }

    public void zoomIn() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        LinkedList<Graph> Grlst = GlobalSceneController.get_controller().getGraphs();

        Float tmscl = GlobalSceneController.get_controller().getDataExtractor().getTimescale();
        // create new thingy with numbers
        drawRuler(tmscl/2) ;

        GlobalSceneController.get_controller().getDataExtractor().setTimeScale(tmscl/2);

        for (Graph gr : Grlst) {
            gr.changeZoom(GlobalSceneController.get_controller().getDataExtractor().getTimescale());
        }

    }

    private void drawRuler(Float timescale){

        GraphicsContext gc = this.canvas.getGraphicsContext2D()   ;
        gc.setStroke(Color.BLUE);

        gc.setLineWidth(2);

        gc.strokeLine(0, 1,  times/timescale , 1);
        gc.setLineWidth(1);

        for (int i = 0 ; i <= times ; i++) {
            gc.strokeLine(i/timescale, 0,  i/timescale , gc.getCanvas().getHeight());

        }

    }

    public Canvas getCanvas(){
        return this.canvas ;
    }
    public VBox getGraphContainer(){
        return this.graphContainer;
    }

    public VBox getGraphNameContainer(){
        return this.graphNameContainer;
    }
    private void init_vars() {
        // create all the buttons for every variable without name ?
        Timelines tmln = this.controller.getDataExtractor().getTimelines();
        HashMap<String, TimeLine> vars = tmln.getTimelines() ;

        for (String key : vars.keySet()){
            Button btn = new Button() ;

            btn.getStyleClass().add("FE_Button") ;
            btn.getStylesheets().add(getClass().getResource("/fxmlGraphics/EditorScene/stylesInternalFE.css").toExternalForm());
            btn.setMaxWidth(Double.MAX_VALUE);   // Expands horizontally

            this.varsButtons.put(key , btn) ;

            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //  open a new graph under the name we are currently on hte txt
                    String name = btn.getText();
                    GlobalSceneController.get_controller().newGraph(key , name); ;
                }
            });

            this.vars.getChildren().add(btn) ;


        }
        this.hideAllVars();


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
