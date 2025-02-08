package org.example.verilog_compiler.SelectorScene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.verilog_compiler.SceneSelector;
import org.example.verilog_compiler.Start;

import java.io.File;

public class ControllerClass_Selector_main {

    private File selectedDirectory;
    SceneSelector global_scene_controller ;

    public ControllerClass_Selector_main() {
        global_scene_controller = SceneSelector.get_controller() ;
    }


    public void existingProjectPressed(ActionEvent event) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Directory");
        File defaultDirectory = new File(Start.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        chooser.setInitialDirectory(defaultDirectory);
        selectedDirectory = new File(chooser.showDialog(SceneSelector.get_primary_stage()).toString());
        //existing.setText(selectedDirectory.toString());
    }

}



