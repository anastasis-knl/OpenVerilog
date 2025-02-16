package org.example.verilog_compiler.WaveViewer.Simulator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.verilog_compiler.GlobalSceneController;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.TimeLine;

import java.io.CharArrayReader;
import java.util.LinkedList;

public class Graph1 implements Graph {
    // this shit supposed to draw the timeline on the graph based on the canvas level and data form the extractor

    private int  height = 50 ;
    private int low = 24 ;
    private int middle = 13 ;
    private int high = 2 ;

    private int startH ;

    private Text lbl ;
    private Integer level;
    private TimeLine tmln;
    private String name ;
    private Canvas canvas;

    private Float timescale ;

    Boolean isLOW = false ;
    Boolean isHIGH = false;
    Boolean iSMIDDLE = true ;


    @Override
    public void drawGraph(TimeLine timeline, String name, Canvas canvas, Integer Level, Float timescaleF) {

        timescale = 0F ;
        this.draw(timeline , name , canvas ,Level ,  timescaleF) ;


        this.level = Level  ;
        this.canvas = canvas;
        this.name = name;
        this.tmln = timeline ;
        this.timescale= timescaleF ;

        VBox names = GlobalSceneController.get_controller().getWave_controller().getGraphNameContainer();
        Text lbl = new Text();
        lbl.setFont(Font.font("Arial", 15));
        lbl.setText(name);
        lbl.setVisible(true) ;
        names.getChildren().add(lbl) ;


        this.lbl  = lbl  ;


    }

    private void draw(TimeLine timeline, String name, Canvas canvas, Integer Level, Float timescaleF) {
        this.startH = Level*26;

        this.timescale = timescaleF;


        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(3);

        LinkedList<String> values = timeline.getValues() ;
        LinkedList<Float> times = timeline.getTimePeriods() ;

        if (timescale >= 0.01){
            timescale = timescale* 1 ;

        }
        /*else if (timescale >= 0.001){
            timescale = timescale* 1000 ;

        }else if (timescale >= 0.000001){
            timescale = timescale* 1000000;

        }*/


        Integer timescale = Math.round(this.timescale) ;

        for (int x =0 ; x < values.size() ; x+=1) {

            // high/ low / z middle / x unknown
            // chnage of time times timescale for the thing

            Character value = values.get(x).charAt(0) ;

            Integer i = Math.round(times.get(x));

            if (value == '1') {
                gc.setStroke(Color.GREEN);
                if (isLOW) {
                    this.isLOW = false;
                    gc.strokeLine(timescale * i, this.startH + this.low, timescale * i, this.startH + this.high);
                } else {
                    this.iSMIDDLE = false;
                    gc.strokeLine(timescale * i, this.startH + this.middle, timescale * i, this.startH + this.high);

                }

                // draw line to next point
                gc.strokeLine(timescale * i, this.startH + this.high, timescale * times.get(x + 1), this.startH + this.high);
                this.isHIGH = true;

            }else if (value =='0') {
                gc.setStroke(Color.GREEN);
                if (isHIGH) {
                    this.isHIGH = false;
                    gc.strokeLine(timescale * i, this.startH + this.low, timescale * i, this.startH + this.high);
                } else {
                    this.iSMIDDLE = false;
                    gc.strokeLine(timescale * i, this.startH + this.middle, timescale * i, this.startH + this.low);
                }

                // draw line to next point
                gc.strokeLine(timescale * i, this.startH + this.low, timescale * times.get(x + 1), this.startH + this.low);
                this.isLOW = true;
            }else if (value == 'z') {
                gc.setStroke(Color.BLUE);
                if (isHIGH) {
                    this.isHIGH = false;
                    gc.strokeLine(timescale * i, this.startH + this.middle, timescale * i, this.startH + this.high);
                } else if (isLOW) {
                    this.isLOW = false;
                    gc.strokeLine(timescale * i, this.startH + this.middle, timescale * i, this.startH + this.low);
                }

                // draw line to next point
                gc.strokeLine(timescale * i, this.startH + this.middle, timescale * times.get(x + 1), this.startH + this.middle);
                this.iSMIDDLE = true;

            }else if (value == 'x') {
                gc.setStroke(Color.RED);
                if (isHIGH) {
                    this.isHIGH = false;
                    gc.strokeLine(timescale * i, this.startH + this.middle, timescale * i, this.startH + this.high);
                } else if (isLOW) {
                    this.isLOW = false;
                    gc.strokeLine(timescale * i, this.startH + this.middle, timescale * i, this.startH + this.low);
                }
                // draw line to next point
                gc.strokeLine(timescale*i, this.startH+this.middle, timescale*times.get(x+1), this.startH+this.middle);
                this.iSMIDDLE= true ;
            }
            else{
                System.out.println(value + "this should be value")  ;
            }

        }



    }


    @Override
    public void deleteGraph() {
        // the canvas has been cleared here and we can just redraw ad different timescale
    }


    @Override
    public void changeZoom(Float timscl) {
        this.draw(this.tmln, this.name , this.canvas , this.level , 1/timscl);

    }

}
