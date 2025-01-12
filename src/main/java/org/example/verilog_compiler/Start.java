package org.example.verilog_compiler;

import javafx.application.Application;
import javafx.stage.Stage;

public  class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Stage primary_stage = new Stage() ;
        primary_stage.setTitle("Compiler  ");
        SceneSelector controller = SceneSelector.get_controller() ;
        controller.setPrimaryStage(primary_stage);
        controller.launch_Selector();

    }
    public static void main(String[] args){
        System.out.println("yes") ;

        launch(args) ;
    }
}
