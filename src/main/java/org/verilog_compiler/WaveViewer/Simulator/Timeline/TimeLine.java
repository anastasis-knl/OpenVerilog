package org.verilog_compiler.WaveViewer.Simulator.Timeline;

import java.util.LinkedList;

public interface TimeLine {

    boolean isArray() ;
    void addChange(float i, String value);
    public Float getDuration() ;
    public String findValueAt(float tm);

    LinkedList<Float> getTimePeriods()  ;
    LinkedList<String> getValues() ;
}
