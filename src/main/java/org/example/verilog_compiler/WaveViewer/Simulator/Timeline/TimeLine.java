package org.example.verilog_compiler.WaveViewer.Simulator.Timeline;

public interface TimeLine {

    boolean isArray() ;
    void addChange(float i, String value);

    public String findValueAt(float tm);
}
