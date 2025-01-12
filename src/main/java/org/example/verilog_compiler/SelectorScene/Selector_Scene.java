package org.example.verilog_compiler.SelectorScene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.verilog_compiler.SceneSelector;
import org.example.verilog_compiler.Start;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.Flow;

public class Selector_Scene extends Scene {
    private File selectedDirectory;
    private Button neo ;
    private  Stage primary_stage;
    private VBox layout ;
    public Selector_Scene(Parent parent, Stage primary_stage) {
        super(parent,400,800);
        this.primary_stage = primary_stage;
        this.layout = (VBox)parent;
        prepare_scene();
    }
    private void getdirname(){
        Stage stage = new Stage() ;
        TextField textField = new TextField();
        textField.setMinHeight(stage.getMinWidth()-50);

        VBox root = new VBox(textField);
        root.setAlignment(Pos.CENTER);
        Button b1 = new Button("Submit") ;
        b1.setMinWidth(30);
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                 String dir = textField.getText();
                 selectedDirectory = new File(selectedDirectory.toString() + "/"+ dir) ;
                 neo.setText(selectedDirectory.toString());
                 stage.close() ;
            }
        });
        Scene scene = new Scene(root, 100, 40);
        root.getChildren().add(b1) ;
        stage.setScene(scene);
        stage.show();


    }
    private void prepare_scene() {
        primary_stage.setTitle("Selector");
        primary_stage.setHeight(400);
        primary_stage.setWidth(800) ;
        primary_stage.setResizable(false);
        layout.setSpacing(30);
        // Background

        super.setFill(Color.GRAY);
        //Functions
        Label l = new Label("Select Project" ) ;
        layout.setAlignment(Pos.CENTER);
        l.setFont(Font.font("verdana", 30));

        //Button new project
        neo = new Button("new project ") ;
        neo.setFont(new Font("verdana" , 20));
        neo.setMinHeight(40);
        neo.setMinWidth(200);
       // neo.setId("selection_button");

        neo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DirectoryChooser chooser = new DirectoryChooser();
                chooser.setTitle("new project");

                File defaultDirectory = new File(Start.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                chooser.setInitialDirectory(defaultDirectory);
                selectedDirectory = new File(chooser.showDialog(primary_stage).toString()) ;
                getdirname() ;
                neo.setText(selectedDirectory.toString());


            }
        }
        );

        //Button existing  project
        Button existing = new Button("existing  project ") ;
        existing.setFont(new Font("verdana" , 20));
        existing.setMinHeight(40);
        existing.setMinWidth(200);
        existing.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    DirectoryChooser chooser = new DirectoryChooser();
                    chooser.setTitle("Choose Directory");

                    File defaultDirectory = new File(Start.class.getProtectionDomain().getCodeSource().getLocation().getPath());
                    selectedDirectory = new File(chooser.showDialog(primary_stage).toString()) ;
                    existing.setText(selectedDirectory.toString());

                }
            }
        );


        // Button start
        Button start = new Button("confirm ") ;
        start.setFont(new Font("verdana" , 20));
        start.setId("selection_button");
        start.setMinHeight(40);
        start.setMinWidth(200);
        start.setOnAction(new EventHandler<ActionEvent>() {
                                 @Override
                                 public void handle(ActionEvent event) {
                                     try {
                                         SceneSelector.launch_Editor(selectedDirectory);
                                     } catch (IOException e) {
                                         throw new RuntimeException(e);
                                     }
                                 }
                             }
        );
        layout.getChildren().add(l) ;
        layout.getChildren().add(neo) ;
        layout.getChildren().add(existing) ;
        layout.getChildren().add(start) ;


    }
}
