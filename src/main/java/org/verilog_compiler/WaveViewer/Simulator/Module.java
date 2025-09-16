package org.verilog_compiler.WaveViewer.Simulator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.verilog_compiler.EditorScene.Tools.Data_stractures.fileExplorerTreeNode;
import org.verilog_compiler.GlobalSceneController;

import java.io.IOException;
import java.util.LinkedList;

public class Module {
    // a tree stractured module system arround the root module likely the tb

    LinkedList<Module> submodules ;
    LinkedList<String> vars ;
    LinkedList<String> var_names ;
    String name;


    Button button  ;
    Boolean hidden;
    VBox moduleEx ;

    public Module(String name) {
        // here we only make the file stracture
        this.name = name ;
        this.vars = new LinkedList<>() ;
        this.var_names = new LinkedList<>() ;
        this.submodules = new LinkedList<>()  ;


    }

    public void createModuleExplorer(VBox container , int level ) {
        // create self
        this.moduleEx = container ;

        button = new Button() ;
        button.getStyleClass().add("FE_Button") ;
        button.getStylesheets().add(getClass().getResource("/fxmlGraphics/EditorScene/stylesInternalFE.css").toExternalForm());
        button.setMaxWidth(Double.MAX_VALUE);   // Expands horizontally



        String txt = "" ;
        for (int i = 0 ; i < level-4 ; i++) {
            // proper indent for level
            txt = txt + " ";
        }
        if (level!=0) {
            txt += "L_ ";
        }
        txt += this.name ;
        button.setText(txt) ;
        this.hidden = true ;
        container.getChildren().add(this.button) ;

        this.button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (hidden){
                    show_children() ;

                }else {
                    hide_children()  ;

                }
                show_vars() ;
            }
        });

        // create children
        for (Module sub : this.submodules) {
            sub.createModuleExplorer(container ,  level + 3);
        }
    }


    private void show_vars() {
        GlobalSceneController.get_controller().showVars(this.vars , this.var_names) ;
    }


    private void show_children(){
        for (Module sub : submodules) {
            sub.show();
        }
        this.hidden = false ;
    }

    // hide all children of a directory
    // called without prior hide only by the <root> node
    public void hide_children() {
        for (Module sub : submodules) {
            sub.hide();
        }
        this.hidden = true  ;

    }

    public void show() {
        this.hidden = false ;
        this.button.setVisible(true);
        this.button.setManaged(true);
        this.moduleEx.requestLayout();
    }

    protected void hide() {
        hide_children();
        // hide itself
        this.button.setVisible(false);
        this.hidden = true ;
        this.button.setManaged(false );

    }


    public void addVar(String name ,String var) {
        this.vars.add(var) ;
        this.var_names.add(name)  ; //  different name for same var on different module
    }

    public void addSubmodule(Module sub) {
        this.submodules.add(sub)  ;
    }

    public LinkedList<Module> getSubmodules() {
        return this.submodules;
    }

    public LinkedList<String> getVars(){
        return this.vars ;
    }
}
