package org.example.verilog_compiler.SelectorScene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.verilog_compiler.SceneSelector;
import org.example.verilog_compiler.Start;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class ControllerClass_Selector_main {

    @FXML
    private Button ButtonNew;
    @FXML
    private Button ButtonExisting;
    @FXML
    private Button ButtonGo;

    @FXML
    private Button SubmitNewName;
    @FXML
    private TextField GetNewName;


    private File selectedDirectory;
    SceneSelector global_scene_controller;

    Stage askProjectname_Stage;


    public ControllerClass_Selector_main() {
        global_scene_controller = SceneSelector.get_controller();
    }


    public void existingProjectPressed(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Directory");
        File defaultDirectory = new File(Start.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        chooser.setInitialDirectory(defaultDirectory);

        // get folder directory
        selectedDirectory = new File(chooser.showDialog(SceneSelector.get_primary_stage()).toString());
        String dir = selectedDirectory.toString();

        // get substring to put in button text
        String substr = "..." + dir.substring(max(0, dir.length() - 22));
        ButtonExisting.setText(substr);
    }


    public void newProjectPressed(ActionEvent event) throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Directory");
        File defaultDirectory = new File(Start.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        chooser.setInitialDirectory(defaultDirectory);
        selectedDirectory = new File(chooser.showDialog(SceneSelector.get_primary_stage()).toString());

        // CREATE THE NEW STAGE

        // ask for name of new directory on new stage
        askProjectname_Stage = new Stage();

        //load from the fxml filei
        File fxmlFilemain = new File("src/main/java/org/example/verilog_compiler/SelectorScene/newDirectoryName.fxml");
        URL fxmlUrlmain = fxmlFilemain.toURI().toURL();
        Parent selector = FXMLLoader.load(fxmlUrlmain);

        //create scene for the ask stage
        Scene ask_Scene = new Scene(selector);
        askProjectname_Stage.setScene(ask_Scene);
        askProjectname_Stage.show();
    }


    public void SubmitNewNamePressed(ActionEvent event) {
        // get name from textfield
        String dir = GetNewName.getText();

        // create directory
        selectedDirectory = new File(selectedDirectory.toString() + "/" + dir);
        if (!selectedDirectory.exists()) {
            selectedDirectory.mkdirs();
        }

        // update button
        dir = selectedDirectory.toString();
        String substr = "..." + dir.substring(max(0, dir.length() - 22));
        ButtonNew.setText(substr);

        // close stage
        askProjectname_Stage.close();

    }


    public void GoPressed(ActionEvent event) {
        try {
            SceneSelector.launch_Editor(selectedDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}









































