package org.example.verilog_compiler.EditorScene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.verilog_compiler.EditorScene.commands.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class Simulation_Scene {
    Stage stage;
    VBox topLayout;
    Scene mainScene;
    private VBox variables;
    private VBox graphs;
    Scanner scanner;
    String date;
    String timescale;
    String version;
    HashMap<Character, Graph> vars;
    HashMap<Character, ArrayG> ars;
    int last_time_checked ;

    Simulation_Scene() throws FileNotFoundException {

        stage = new Stage();
        VBox topLayout = new VBox();
        stage.setTitle("Simulation");
        this.topLayout = topLayout;
        this.ars = new HashMap<Character, ArrayG>();
        this.vars = new HashMap<Character, Graph>()  ;
        this.last_time_checked = 0 ;
        mainScene = new Scene(topLayout);
        stage.setHeight(800);
        stage.setWidth(1000);

        create_toolbar();
        create_main();

        stage.setScene(mainScene) ;
        stage.show();
    }

    private void create_toolbar() {
        HBox tools = new HBox();
        tools.setMinHeight(40);
        tools.setMaxWidth(Double.MAX_VALUE);
        tools.setMinWidth(this.stage.getMinWidth()) ;

        tools.setStyle("-fx-background-color:#333333;"+
                        "-fx-border-color: black");
        this.topLayout.getChildren().add(tools);

        // future ασχολια

    }

    private void create_main() throws FileNotFoundException {
        HBox main = new HBox();
        this.topLayout.getChildren().add(main);
        main.setMaxHeight(Double.MAX_VALUE);
        main.setMaxWidth(Double.MAX_VALUE);

        VBox variables = new VBox();
        VBox graphs = new VBox();
        this.variables = variables;
        this.graphs = graphs;

        main.getChildren().add(variables);
        main.getChildren().add(graphs);

        this.variables.setMinHeight(this.stage.getHeight() - 40 );
        this.graphs.setMinHeight(this.stage.getHeight()-40 );

        this.variables.setMinWidth(150) ;
        this.graphs.setMinWidth(this.stage.getWidth() - 150 );
        this.variables.setStyle("-fx-background-color:#333333;"+
                "-fx-border-color: black");

        headerAnalyze();
        DataAnalyze() ;
    }
    void DataAnalyze() {
        // 10 reference points are to be analyzed each time called
        if (last_time_checked == 0 ) {
            String nx = scanner.next() ;
            if(nx.equals("$dumpvars")) {
                String data= "" ;
                String str = scanner.next() ;
                System.out.println("passed here") ;
                while (!(str.equals("$end"))) {
                    data += str+ " " ;
                    str = scanner.next() ;
                }
                update(data) ;
            }
        }
        last_time_checked = Integer.parseInt(scanner.next().substring(1)) ;
        for(int i = 0 ; i < 10 && scanner.hasNext()  ; i++) {
            String str;
            str  = scanner.next();
            String data = "" ;
            while(!(str.startsWith("#"))){
                data += str +" " ;
                if(!(scanner.hasNext())) {
                    update(data);
                    return ;
                }
                str = scanner.next() ;
            }
            update(data);
            this.last_time_checked = Integer.parseInt(str.substring(1)) ;
        }

    }
    void update(String s){
        String[] com = s.split(" ");
        String prev = "";
        for (String a : com) {
            if (a.startsWith("b")) {
                prev = a;
            } else {
                if (prev.isEmpty()) {
                    vars.get(a.charAt(1)).update(a.charAt(0),this.last_time_checked);
                } else {
                    ars.get(a.charAt(0)).update(prev.substring(1), this.last_time_checked);
                    prev = "";

                }
            }
        }
        if(last_time_checked != 0 ){
            update_all() ;
        }
    } ;
    void update_all(){
        for (Graph var: vars.values()){
            var.update(last_time_checked);
        }
        for (ArrayG ar : ars.values()){
            ar.update(last_time_checked);
        }
    }
    private void headerAnalyze() throws FileNotFoundException {
        // createButttons , graphics , hierarchy
        File rPath = new File("src/main/resources/compiled_files");
        File output = new File(rPath.getAbsolutePath() + "/dump.vcd");
        scanner = new Scanner(output);

        // scopes or arrays are going to be dirs
        // vars are gonig to be buttons simple
        // Vector<Button> levels = new Vector<>() ;

        Stack<VBox> levels = new Stack<VBox>();
        levels.push(this.variables);
        boolean flag = false;
        while (true) {
            if (flag) {
                break;
            }
            commandType line = getLine();
            switch (line.getType()) {
                case 0:
                    Carray ln = (Carray) line;
                    getArrayB(line.getData(), ln.getSize(), levels.lastElement(), levels.size());
                    break;
                case 1:
                    this.date = line.getData();
                    break;
                case 2:
                    String s = line.getData();
                    // read for all constants
                    update(s) ;
                    break;
                case 3:
                    flag = true;
                    break;
                case 4:
                    break;
                case 5:
                    VBox scope = getScopeB(line.getData(), levels.lastElement(), levels.size());
                    levels.push(scope);
                    break;
                case 6:
                    this.timescale = line.getData();
                    break;
                case 7:
                    levels.pop();
                    break;
                case 8:
                    // future : posibility of double or more declaration on save variable
                    getVarB(line.getData(), levels.lastElement(), levels.size());
                    break;
                case 9:
                    this.version = line.getData();
                    break;
                default:
                    System.out.println("bad data: " + line.getData());
                    System.exit(-1);
                    break;
            }


        }
        return;
    }

    VBox getScopeB(String name, VBox top, int sz) {
        // create button with inside layout hit -> hide or not
        String overlay = "";
        for (int i = 0; i < sz; i++) {
            overlay += " ";
        }

        Button button = new Button(overlay + name);

      //  button.setMinWidth(top.getWidth());
        button.setMaxWidth(Double.MAX_VALUE);
        button.setLayoutX(overlay.length()*5);

        top.getChildren().add(button);

        HBox helper = new HBox() ;
        helper.setStyle("-fx-background-color:#333333");
        helper.setMaxWidth(Double.MAX_VALUE);
        helper.setMinWidth(top.getWidth());

        StackPane helpie = new StackPane() ;
        helpie.setMinWidth(overlay.length()*5);

        VBox new_top = new VBox();
        new_top.setMaxWidth(Double.MAX_VALUE);
        new_top.setMinWidth(150-overlay.length()*5);

        helper.getChildren().add(helpie) ;
        helper.getChildren().add(new_top) ;

        helper.setVisible(false);
        //this.explorer.setStyle("-fx-background-color: transparent;");
        helper.setManaged(false);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (helper.isVisible()) {
                    // doesn't work
                    helper.setVisible(false);
                    //this.explorer.setStyle("-fx-background-color: transparent;");
                    helper.setManaged(false);
                } else {
                    helper.setVisible(true);
                    helper.setManaged(true);
                }
            }
        });
        top.getChildren().add(helper) ;
        return new_top;
    }

    void getArrayB(String data, int size, VBox top, int sz) {

        // array : W/V|$|name|8

        char type = data.charAt(0);
        char symbol = data.charAt(1);
        String name = data.substring(2, data.length());

        String overlay = "";
        for (int i = 0; i < sz; i++) {
            overlay += " ";
        }

        Button button = new Button(overlay + name);

        button.setMinWidth(top.getWidth());
        button.setMaxWidth(Double.MAX_VALUE);

        top.getChildren().add(button);

        HBox helper = new HBox() ;
        helper.setStyle("-fx-background-color:#333333");
        helper.setMaxWidth(Double.MAX_VALUE);
        helper.setMinWidth(top.getWidth());

        StackPane helpie = new StackPane() ;
        helpie.setMinWidth(overlay.length()*5);

        VBox new_top = new VBox();
        new_top.setMaxWidth(Double.MAX_VALUE);
        new_top.setMinWidth(150-overlay.length()*5);

        helper.getChildren().add(helpie) ;
        helper.getChildren().add(new_top) ;

        helper.setVisible(false);
        //this.explorer.setStyle("-fx-background-color: transparent;");
        helper.setManaged(false);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (helper.isVisible()) {
                    // doesn't work
                    helper.setVisible(false);
                    //this.explorer.setStyle("-fx-background-color: transparent;");
                    helper.setManaged(false);
                } else {
                    helper.setVisible(true);
                    helper.setManaged(true);
                }
            }
        });
        // array : W/V|$|name|8
        Vector<Graph> vars = new Vector<>();

        for (int i = 0; i < size; i++) {
            // array => b 101023 o ->
            getArrayVarB(String.valueOf(i), new_top, sz + 1, vars);
        }
        top.getChildren().add(helper) ;

        ArrayG ar = new ArrayG(vars);
        this.graphs.getChildren().add(ar) ;

        this.ars.put(symbol, ar);

    }

    void getVarB(String data, VBox top, int sz) {
        char type = data.charAt(0);
        char symbol = data.charAt(1);
        String name = type + "_" + data.substring(2);
        String overlay = "";
        for (int i = 0; i < sz; i++) {
            overlay += " ";
        }
        Button button = new Button(overlay + name);

       // button.setMinWidth(top.getWidth());
        button.setMaxWidth(Double.MAX_VALUE);
        button.setLayoutX(overlay.length()*5);

        top.getChildren().add(button);

        Graph graph = new Graph(this.graphs);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                graph.change_state();
            }
        });
        this.graphs.getChildren().add(graph) ;

        this.vars.put(symbol, graph);
    }

    void getArrayVarB(String data, VBox top, int sz, Vector<Graph> vec) {
        String overlay = "";
        for (int i = 0; i < sz; i++) {
            overlay += " ";
        }
        Button button = new Button(overlay + data);


        button.setMinWidth(top.getWidth());
        button.setMaxWidth(Double.MAX_VALUE);


        top.getChildren().add(button);
        Graph graph = new Graph(this.graphs);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                graph.change_state();
            }
        });
        this.graphs.getChildren().add(graph) ;

        vec.add(graph);
    }

    commandType getLine() {
        String str = scanner.next();
        if (str.equals("$date")) {
            String data = "";
            str = scanner.next();
            while (!(str.equals("$end"))) {
                data += " " + str;
                str = scanner.next();
            }
            Cdate dt = new Cdate();
            dt.setData(data);
            return dt;
        } else if (str.equals("$timescale")) {
            String data = scanner.next();
            Ctimescale ct = new Ctimescale();
            ct.setData(data);
            scanner.next();// remove $end
            return ct;
        }
        if (str.equals("$version")) {
            String data = "";
            str = scanner.next();
            while (!(str.equals("$end"))) {
                data += " " + str;
                str = scanner.next();
            }
            Cversion cv = new Cversion();
            cv.setData(data);
            return cv;

        } else if (str.equals("$scope")) {
            Cscope cs = new Cscope();
            scanner.next();
            cs.setData(scanner.next());
            scanner.next();
            return cs;

        } else if (str.equals("$var")) {
            String data = "";
            data += scanner.next().charAt(0);
            String s = scanner.next();
            data += scanner.next();
            data += scanner.next();
            //type(1)symbol(1)name
            if (s.equals("1")) {
                Cvar cv = new Cvar();
                cv.setData(data);
                scanner.next();
                return cv;
            } else {
                Carray ca = new Carray();
                ca.setSize(Integer.valueOf(s));
                ca.setData(data);
                // array : W/V|$|name|8
                scanner.next();
                if(!(data.charAt(0) == 'p')) {
                    scanner.next();
                }
                return ca;
            }
        } else if (str.equals("$upscope")) {
            scanner.next();
            return new Cup();
        } else if (str.equals("$dumpall")) {
            String data = "";
            String nxt = scanner.next();
            while (!(nxt.equals("$end"))) {
                data += nxt + " ";
                nxt = scanner.next();
            }
            Cdump cd = new Cdump();
            cd.setData(data);
            return cd;
        } else if (str.startsWith("#")) {
            last_time_checked = Integer.parseInt(str.substring(1)) ;
            return new Cend();
        }else if (str.equals("$comment")) {
            String nxt  = scanner.next() ;
            while (!(nxt.equals("$end"))) {
                nxt = scanner.next();
            }
            Cirrelevant ir = new Cirrelevant() ;
            ir.setData("comment") ;
            return ir;
        }

        System.out.println(str);
        return new Cirrelevant();

    }


    private void analyzeData() {
        // αναλυση vcd file σε readable format

    }
}

