package org.example.verilog_compiler.WaveViewer.Simulator.Timeline;

import java.util.LinkedList;

public class TimelineA implements TimeLine{

    String symbol ;
    String type ;

    LinkedList<Float> time;//in secs
    LinkedList<String> values ;

    Integer previous_lookup ;
    Integer bits;


    public TimelineA(String symbol , String type, Integer bits ){
        this.symbol = symbol;
        this.type = type ;
        this.bits = bits;
        this.previous_lookup = 0 ;
        this.time = new LinkedList<>() ;
        this.values = new LinkedList<>() ;

    }

    @Override
    public boolean isArray() {
        return false;
    }

    public void addChange(float time , String value){
        this.time.add(time) ;
        this.values.add(value) ;
    }

    public String findValueAt(float tm) {
        if ( previous_lookup < this.time.size()-1 ){
            if( this.time.get(previous_lookup + 1)<tm) {
                previous_lookup = previous_lookup + 1;
                return this.values.get(previous_lookup+1) ;
            }
        }
        return this.values.get((previous_lookup));

    }

    @Override
    public LinkedList<Float> getTimePeriods() {
        return this.time;
    }

    @Override
    public LinkedList<String> getValues() {
        return this.values;
    }

    @Override
    public Float getDuration() {
        return this.time.get(this.time.size()-1)  ;
    }


}
