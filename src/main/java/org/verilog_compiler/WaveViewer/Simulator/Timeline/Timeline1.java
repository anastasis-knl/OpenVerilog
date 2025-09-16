package org.verilog_compiler.WaveViewer.Simulator.Timeline;

import java.util.LinkedList;

public class Timeline1 implements TimeLine {
    String symbol ;
    String type ;

    LinkedList<Float> time;//in secs
    LinkedList<String> values ;

    Integer previous_lookup ;

    // will not work directly for array
    //  you don't know this is an array
    //  the linked list character takes one value not list -> need list
    // add change changes one value not an array
    // we want to create tinmeline ? or timelines.change(...) and directly that will happen
    // don't care wether array or not

    public Timeline1(String symbol , String type){
        this.symbol = symbol;
        this.type = type ;

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

    // bad search thing O(N) but like squared really shit
    public String findValueAt(float tm) {
        if ( previous_lookup < this.time.size()-1 ){
                if(this.time.get(previous_lookup + 1)<tm) {
                previous_lookup = previous_lookup + 1;
                return this.values.get(previous_lookup+1).toString() ;
            }
        }
        return this.values.get((previous_lookup)).toString();

    }

}
