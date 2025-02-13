package org.example.verilog_compiler.WaveViewer.Simulator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.TimeLine;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.TimelineA;

import java.util.LinkedList;

public class GraphA implements Graph{

    private int  height = 50 ;
    private int low = 24 ;
    private int middle = 13 ;
    private int high = 2 ;

    private int startH ;
    private Float timescale ;

    TimelineA tmln ;
    @Override
    public void drawGraph(TimeLine timeline, String name, Canvas canvas, Integer Level, Float timescaleF) {
        this.startH = Level*26;


        this.timescale = timescaleF;
        // 1 pixel for 1 second etc ;
        this.tmln = (TimelineA)timeline ;

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(3);

        LinkedList<String> values = timeline.getValues() ;
        LinkedList<Float> times = timeline.getTimePeriods() ;


        Integer timescale = Math.round(timescaleF) ;
        for (int x =0 ; x < values.size() ; x+=1) {

            // high/ low / z middle / x unknown
            // chnage of time times timescale for the thing


            Integer bits = this.tmln.getBits();
            String value = "";
            // value is like 101 even when five bits must become 00101
            for (int i = 0; i < bits - values.get(x).length(); i++) {
                value += '0';
            }
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

        }
    }

    @Override
    public void deleteGraph() {

    }

    @Override
    public void changeZoom(int change) {

    }
}
