package org.example.verilog_compiler.WaveViewer.Simulator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.example.verilog_compiler.GlobalSceneController;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.TimeLine;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.TimelineA;

import java.awt.*;
import java.util.LinkedList;

public class GraphA implements Graph{

    private int  height = 50 ;
    private int low = 24 ;
    private int middle = 13 ;
    private int high = 2 ;

    private int startH ;
    private Float timescale ;

    private Text lbl ;
    private Integer level;
    private String name ;
    private Canvas canvas;


    TimelineA tmln ;

    @Override
    public void drawGraph(TimeLine timeline, String name, Canvas canvas, Integer level, Float timescaleF) {
        // now put the text for the variable name

        this.draw(timeline , name , canvas , level , timescaleF) ;

        this.level = level  ;
        this.canvas = canvas;
        this.name = name;
        this.timescale= timescaleF ;

        VBox names = GlobalSceneController.get_controller().getWave_controller().getGraphNameContainer();
        Text lbl = new Text();
        lbl.setText(name);
        lbl.setVisible(true) ;
        names.getChildren().add(lbl) ;

    }

    private void draw(TimeLine timeline, String name, Canvas canvas, Integer Level, Float timescaleF) {
        this.startH = Level*26;


        this.timescale = timescaleF*3;
        // 1 pixel for 1 second etc ;
        this.tmln = (TimelineA)timeline ;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(3);

        LinkedList<String> values = timeline.getValues() ;
        LinkedList<Float> times = timeline.getTimePeriods() ;

        if (timescale >= 1){
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


            Integer bits = this.tmln.getBits();
            String value = "";
            // value is like 101 even when five bits must become 00101
            /*for (int i = 0; i < bits - values.get(x).length(); i++) {
                value += '0';
            }*/
            value += values.get(x);
            Integer i = Math.round(times.get(x));

            gc.setStroke(Color.GREEN);

            if (value.indexOf('x') != -1) {
                gc.setStroke(Color.RED);

            }else if (value.indexOf('z') != -1) {
                gc.setStroke(Color.BLUE);
            }

            gc.strokeLine(timescale * i, this.startH + this.low, timescale * i, this.startH + this.high);
            gc.strokeLine(timescale * times.get(x+1), this.startH + this.low, timescale * times.get(x+1), this.startH + this.high);
            gc.strokeLine(timescale * i, this.startH + this.low, timescale * times.get(x+1), this.startH + this.low);
            gc.strokeLine(timescale * i, this.startH + this.high, timescale * times.get(x+1), this.startH + this.high);
            gc.setFont(javafx.scene.text.Font.font(10)); // Font size
            gc.fillText(value ,timescale * i,this.startH + this.low );
        }


    }

    @Override
    public void deleteGraph() {

    }

    @Override
    public void changeZoom(Float timscl) {

        // works on the other way round with this shit
        this.draw(this.tmln, this.name , this.canvas , this.level , 1/timscl);

    }
}
