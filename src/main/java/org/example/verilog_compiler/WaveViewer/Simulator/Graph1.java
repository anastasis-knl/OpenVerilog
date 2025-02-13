package org.example.verilog_compiler.WaveViewer.Simulator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
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
    private Float timescale ;

    Boolean isLOW = false ;
    Boolean isHIGH = false;
    Boolean iSMIDDLE = true ;
    @Override
    public void drawGraph(TimeLine timeline, String name, Canvas canvas, Integer Level, Float timescaleF) {
        // depending on 1) timescale 2) level => draw the lines
        this.startH = Level*26;

        // [0 , 50 , 100 , 150 , 200 , 250 ]
        // [1 , 0 , 1 , z , 0 ,  1 , 0 ]

        this.timescale = timescaleF;
        // 1 pixel for 1 second etc ;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(3);

        LinkedList<String> values = timeline.getValues() ;
        LinkedList<Float> times = timeline.getTimePeriods() ;


        Integer timescale = Math.round(timescaleF) ;
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

    }

    @Override
    public void changeZoom(int change) {

    }
}
