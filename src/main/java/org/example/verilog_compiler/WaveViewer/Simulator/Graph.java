package org.example.verilog_compiler.WaveViewer.Simulator;

import javafx.scene.canvas.Canvas;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.TimeLine;

public interface Graph {

    public void drawGraph(TimeLine timeline, String name, Canvas canvas , Integer Level, Float timescale) ;
    public void deleteGraph() ;
    public void changeZoom(Float timscl) ;

}
