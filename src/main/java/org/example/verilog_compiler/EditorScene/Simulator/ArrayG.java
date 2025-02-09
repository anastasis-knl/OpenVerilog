package org.example.verilog_compiler.EditorScene.Simulator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;

import java.util.Vector;

public class ArrayG extends ScrollPane {
    private Canvas canvas;
    private int time ;
    String prev ;
    Vector<Graph> subs ;
    GraphicsContext gc ;

    ArrayG(Vector<Graph> subs ) {
        this.subs = subs;
        this.time = 0 ;
        Canvas canvas = new Canvas() ;
        this.canvas = canvas;
        this.getChildren().add(canvas) ;
        prev = "z" ;
        this.setHeight(40);
        this.setMaxWidth(Double.MAX_VALUE);
        gc = canvas.getGraphicsContext2D() ;
        this.change_state();
    }
    void change_state(){
        if (this.isVisible()){
            // doesn't work
            this.setVisible(false);
            //this.explorer.setStyle("-fx-background-color: transparent;");
            this.setManaged(false);
        }else{
            this.setVisible(true);
            this.setManaged(true );
        }

    }
    void update(String opt , int time){
        gc.fillText(opt , time  , 15 );
        //updates subs  ;
        if (opt.equals("x")|| opt.equals("z")){
            for(int i = this.subs.size()-1 ; i >=0   ; i--) {
                this.subs.get(i).update(opt.charAt(0) , time );
            }
        }else{
            // 0 -> LSD and max ->MSD
            // update what we have input for ->  we have for max  : min
            // ex if opt.size() = 4 && all = 6  sz-1 -> 0 || sz -2 -> 1
            for(int i = 0; i < opt.length() ; i ++ ) {
                this.subs.get(i).update( opt.charAt(opt.length()- i -1 ), time );
            }

            // update the rest

            for (int i = opt.length() ; i <this.subs.size() ; i++) {
                this.subs.get(i).update('0', time );

            }
        }

        this.time = time ;

    }

    void update(int time){
        if(this.time == time){
            return ;
        }
        update(prev , time) ;
    }
}
