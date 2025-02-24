package org.example.verilog_compiler.WaveViewer;

import org.example.verilog_compiler.WaveViewer.Simulator.Module;
import org.example.verilog_compiler.WaveViewer.Simulator.Timeline.Timelines;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class dataExtractor {
    File dataFile ;


    Timelines timelines ;

    String Date ;
    String Version ;

    Float timescale ;

    LinkedList<Module> currentModule;


    Module topModule ;

    Float max_time ;

    public dataExtractor(){
        this.dataFile = new File("src/main/resources/bin/datadump/dump.vcd");

        // DATA EXTRACTION
        this.timelines = new Timelines() ;

        this.currentModule = new LinkedList<>() ;


        // could go in seperate class with tools
        this.extractData() ;

        // file explorer -> splitPane -> [ VBOX[modules] , VBOX[varsOfModule] ]
    }
    public Float getTimescale(){return this.timescale;} ;
    public Timelines getTimelines(){
        return this.timelines ;
    }
    public Module getTopModule(){
        return this.topModule ;
    }
    public String getDate(){
        return this.Date ;
    }
    public String getVersion(){
        return this.Version ;
    }


    void extractData() {
        this.max_time = 0F ;
        try {
            Scanner scanner = new Scanner(this.dataFile);
            extractHeader(scanner)  ;
            // extract(scanner)  ;

            // we are now at the dumpvars stage for initial values for each timeline

            this.setInitialValues(scanner) ;
            // from the #50 ticks and on we start timeline

            this.timelineExtraction(scanner) ;

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        for (String key : timelines.getTimelines().keySet()){
            max_time  = Math.max(max_time, timelines.getTimeline(key).getTimePeriods().getLast());
        }



    }

    void extractHeader(Scanner scanner){
        // ends at dump vars and end
        String type ;
        while (scanner.hasNext()) {
            type = scanner.next() ;
            if (type.equals("#0")){
                break ;
            }
            extract_log(scanner, type);
        }

    }

    private int extractDataPointer(String log){
        // returns where the data ends
        int ptr = 1;
        while(!(log.charAt(ptr)!= '1' &&
                log.charAt(ptr)!= '0' &&
                log.charAt(ptr)!= 'z' )){
            ptr = ptr +1 ;
        }
        return ptr;
    }


    void setInitialValues(Scanner scanner){
        // γετ τηε #0 input and put initial values on all variables
        String data ;
        scanner.next();  // skip dumpvars
        data = scanner.next() ;
        String value ;
        String symbol ;

        while(!(data.equals("$end"))) {

            if (data.charAt(0) == 'b' ) {
                // handle data of list  we want to turn it into 1002$ instead of b1002 $
                data = data.substring(1)  + scanner.next() ;

            }

            int ptr = this.extractDataPointer(data )  ;


            value = data.substring(0,ptr)  ; // extract value
            symbol = data.substring(ptr); // extract symbol

            timelines.getTimeline(symbol).addChange(0 , value); // add initial value


            data = scanner.next() ;
        }

    }
    void setTimeScale(Float timescale ){
        this.timescale = timescale;
    }
    void timelineExtraction(Scanner scanner){
        String timeTicksStr ;
        Float ticks;
        Float time = 0f;
        String pair = "";

        String value ;
        String symbol ;


        timeTicksStr = scanner.next().substring(1); // tick time

        while(scanner.hasNext()) {
            pair = scanner.next() ; // first var of new

            // #1^ > extract in one go but whta if b102 #

            ticks = Float.parseFloat(timeTicksStr);
            time = ticks*this.timescale ;


            // handle one timelog
            while(!(pair.charAt(0) == '#')) {

                if (pair.charAt(0) == 'b' ) {
                    // handle data of list  we want to turn it into 1002$ instead of b1002 $
                    pair = pair.substring(1)  + scanner.next() ;

                }
                int ptr = this.extractDataPointer(pair);

                value = pair.substring(0,ptr)  ; // extract value
                symbol = pair.substring(ptr); // extract symbol

                timelines.getTimeline(symbol).addChange(time , value); // add initial value

                if (!(scanner.hasNext())) {
                    break;
                }
                pair = scanner.next() ;
            }

            // now we have gone into the new timeline we need to pass the tick time
            timeTicksStr = pair.substring(1) ; // get just the time value

        }
        for (String key : timelines.getTimelines().keySet()){
            timelines.getTimelines().get(key).getTimePeriods().add(time);
        }

    } ;


    void extract_log(Scanner scanner, String type ) {
        // data - version - timescale  - scope(var ... reg/wire) - upscope
        // comment -
        if (type.equals("$date")) {
            String data = "";
            type = scanner.next();
            while (!(type.equals("$end"))) {
                data += " " + type;
                type = scanner.next();
            }
            this.Date = data ;

        } else if (type.equals("$timescale")) {
            String data = scanner.next();

            Float num =  Float.parseFloat(data.substring(0,1));
            String timeUnit = data.substring(1) ;


            if (timeUnit == "s"){
                this.timescale = 1.0F;
            }else if (timeUnit == "ms"){
                this.timescale = 0.01F;

            }else if (timeUnit == "μs"){
                this.timescale = 0.00001F;

            }else if (timeUnit == "ns"){
                this.timescale = 0.00000001F;

            }else {
                this.timescale = 1F;
            }

            scanner.next();// remove $end
        }
        else if (type.equals("$version")) {
            String data = "";
            type = scanner.next();
            while (!(type.equals("$end"))) {
                data += " " + type;
                type = scanner.next();
            }
            this.Version = data ;

        } else if (type.equals("$scope")) {
            // handle new module
            scanner.next() ;
            String moduleName = scanner.next() ;
            scanner.next() ;

            Module module = new Module(moduleName) ;

            // create root module ;
            if(this.currentModule.isEmpty()) {

                this.topModule = module ;
            }else {
                // add submodule to the parent module
                this.currentModule.get(this.currentModule.size()-1).addSubmodule(module) ;
                // current module change
            }
            this.currentModule.add(module) ;
        }

        else if (type.equals("$var")) {

            type =  scanner.next() ; // wire reg ..

            String bitCount = scanner.next();
            Integer bits = Integer.parseInt(bitCount) ;

            String symbolID = scanner.next();
            String name = scanner.next() ;
            String order = scanner.next() ; // $end skip

            if (bits > 1 ) {
                scanner.next();
            }


            // create the timeline
            if(!(this.timelines.contains(symbolID))) {

                this.timelines.add_timeline(symbolID ,type,bits);

            }
            this.currentModule.get(this.currentModule.size() -1).addVar(name , symbolID);
            // add relation
            }

        else if (type.equals("$upscope")) {
            scanner.next();
            this.currentModule.pop() ; // go one module up


        } else if (type.equals("$comment")) {
            String nxt  = scanner.next() ;
            while (!(nxt.equals("$end"))) {
                nxt = scanner.next();
            }
        }else if(type.equals("enddefinitions")) {
            scanner.next() ;
        }else{
            System.out.println(type);
            scanner.next();
        }

    }

    public void incraseTimeScale() {
        this.timescale = this.timescale *2 ;
    }

    public void decreaseTimeScale() {
        this.timescale = this.timescale /2 ;

    }

    // toolbar
    // left -> 2 things on split pane file explorer and files
    // right -> signals
}
