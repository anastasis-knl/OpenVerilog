package org.example.verilog_compiler.WaveViewer.Simulator.Timeline;

import java.util.HashMap;

public class Timelines {

    // Midleware thingy
    // all vars
    private HashMap<String , TimeLine> tmlns ;

    public Timelines() {
        tmlns = new HashMap<>() ;
    }

    public void add_timeline(String symbol , String type, int bitCount){
        // independant of module it is just that one module has access to specific
        // timelines
        TimeLine tmln;

        if(bitCount == 1) {
            tmln = new Timeline1(symbol , type );

        }else{
            tmln = new TimelineA(symbol, type , bitCount) ;
        }

        tmlns.put(symbol , tmln);

        // bit count to see what interface implementation to use
    }

    public TimeLine getTimeline(String ch) {
        return tmlns.get(ch) ;
    }

    public boolean contains(String symbol) {
        if (this.tmlns.containsKey(symbol)) {
            return true;
        }
        return false ;
    }

    public HashMap<String , TimeLine> getTimelines() {
        return this.tmlns;
    }

}
