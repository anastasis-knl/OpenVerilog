/*
package org.example.verilog_compiler.EditorScene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.verilog_compiler.EditorScene.Simulator.Simulation_Scene;

import java.io.*;
import java.util.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Editor_Scene extends Scene {
    File dir ;
    Stage primary_stage;
    VBox layout0 ;
    HBox l_toolbar ;
    VBox layout1 ;
    HBox layout2 ;
    StackPane tlayout ;
    VBox  explorer ;
    TextArea tEditor ;
    File topFile ;
    static int explorer_width = 180 ;
    Vector<Button> buttons = new Vector<Button>() ;
    private Label terminal;


    void compile() throws IOException, InterruptedException {
        File[] files = dir.listFiles();
        // Traverse through the files array
        Vector<String> filePending = new Vector<>() ;

        //get files
        assert files != null;
        save_to_tmp(tEditor.getText()) ;

        for (File file : files) {
            if (file.getName().endsWith(".v")) {
                save(file.getName().substring(0, file.getName().length()-2))  ;
                filePending.add(file.getName()) ;
            }
        }
        // compile files into resources iverilog -o /path/to/output_directory/output_file.vvp input_file.v
        run_command(filePending);

    }

    // save from .txt file that the console is saved to .v file
    void save(String filename) throws IOException {
        File v_file = new File(dir.getAbsolutePath() + "/" + filename +".v") ;
        File tmp = new File(dir.getAbsolutePath() + "/" + filename +".txt") ;

        String content = get_file_content(tmp) ;
        FileWriter fw  = new FileWriter(v_file.getAbsolutePath()) ;
        fw.write(content);
        fw.close() ;
    }
========================================================
    // compile the program
    void run_command(Vector<String> files) throws IOException, InterruptedException {
        File rPath = new File("src/main/resources/compiled_files");

        String[] filesArray = new String[files.size()];
        files.toArray(filesArray);


        List<String> command = new ArrayList<>();
        // Add the command and its arguments to the list
        command.add("iverilog");
        command.add("-o");
        command.add(rPath.getAbsolutePath() + "/endFile");
        command.addAll(files);


        ProcessBuilder processBuilder = new ProcessBuilder(command);

        processBuilder.directory(dir);
        Process process = processBuilder.start();
        update_console(process) ;

    }
========================================================

========================================================

    // update console with the output of the ccompiler
    void update_console(Process process ) throws IOException, InterruptedException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String lines =null;
        this.terminal.setText("") ;

        while((lines = reader.readLine())!=null) {
            this.terminal.setText(this.terminal.getText()+"\nlines:"+lines );

        }


        String lines1 =null;
        while((lines1 = readers.readLine())!=null) {
            this.terminal.setText(this.terminal.getText()+"\nerror lines:"+lines1);
        }

        int exitCode = process.waitFor();
        this.terminal.setText(this.terminal.getText()+"\nCommand vd with exit code: " + exitCode);

    }

========================================================
========================================================

    // run the comoppiled program
    void execute() throws IOException, InterruptedException {
        File rPath = new File("src/main/resources/compiled_files");
        File wFile = new File(rPath.getAbsolutePath() + "/endFile");



        List<String> command = new ArrayList<>();
        // Add the command and its arguments to the list
            command.add("vvp");
        command.add(wFile.getName());
        System.out.println(command.toString());

        ProcessBuilder processBuilder = new ProcessBuilder(command);


        processBuilder.directory(rPath);
        Process process = processBuilder.start();


        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {

            writer.write("finish"); // The input you want to provide
            writer.newLine();
            writer.flush();
        }

        process.waitFor(1, TimeUnit.SECONDS) ;

        update_console(process) ;

        Simulation_Scene ss = new Simulation_Scene() ;
    }
========================================================

    void create_dir(){
        //create dir if not exist
        if (!(dir.exists())) {
            new File(dir.toString()).mkdirs();
        }
    } ;

    // add files to explorer
    private void add_existing_files() {
        File[] files = dir.listFiles();

        // Traverse through the files array
        assert files != null;
        for (File file : files) {
            if (file.isDirectory() || !(file.toString().endsWith(".v"))) {
                continue ;
            } else {
                topFile= file;
                Button new_file = new Button(file.getName().substring(0,file.getName().length()-2));
                new_file.setMinHeight(25);
                buttons.add(new_file) ;
                try {
                    calibrate_fe_file(new_file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                explorer.getChildren().add(new_file) ;

            }
        }
    }

    // create new button if added by user a new file withing the file explorer
    class File_create implements EventHandler<ActionEvent>{

        @Override
        public void handle(ActionEvent actionEvent) {
            // ζηταμε ονομα και δημιουργια του αρχειου + να φαινεται σαν ονομα κουμπιου στο file explorer
            TextField tf = new TextField() ;
            explorer.getChildren().add(tf) ;
            tf.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        String s = tf.getText();
                        Button new_file = new Button(s);
                        new_file.setMinHeight(25);
                        buttons.add(new_file) ;
                        try {
                            calibrate_fe_file(new_file);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        explorer.getChildren().add(new_file) ;
                        explorer.getChildren().remove(tf) ;

                    }
                }
            });
        }
    }


    // used to change the button layout and borders inside the file explorer
    void calibrate_fe_file(Button b ) throws IOException {
        b.setMinWidth(explorer_width);
        // create file
        File fl = new File(dir.toString()+"/"+b.getText()+".v");
        if (!(fl.exists())){
            fl.createNewFile();
        }
        File file = new File(dir.toString()+"/"+b.getText()+".txt");
        if (!(file.exists())) {
            try {
                file.createNewFile() ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileWriter fw = new FileWriter(file);
        fw.write(get_file_content(new File(dir.toString()+"/"+b.getText()+".v")));
        topFile = file ;
        fw.close( ) ;


        // on press
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                File file = new File(dir.toString()+"/"+b.getText()+".txt");

                try {
                    // save to tmp file the progress
                    if(!(topFile == null)) {
                        save_to_tmp(tEditor.getText()) ;
                    }
                    // put to string file text
                    topFile = file ;
                    tEditor.clear();
                    tEditor.setText(get_file_content(file));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }



    // Create main stage scene , buttons and comnpile / run things
    public Editor_Scene(Parent parent,Stage primary_stage,  File directory ) throws IOException {
        super(parent);
        this.dir = directory ;
        this.primary_stage = primary_stage;
        this.layout0 = (VBox)parent;

        // format
        primary_stage.setResizable(true);
        primary_stage.setWidth(1920);
        primary_stage.setHeight(1080 );

        // background colors
        this.setFill(Color.web("#CCCCCC"));

        // back_end
        create_dir() ;


        // Toolbar
        HBox toolbar = new HBox() ;
        this.l_toolbar = toolbar ;
        this.layout0.getChildren().add(toolbar) ;
        this.l_toolbar.setStyle("-fx-background-color: #525251;");
        this.l_toolbar.setMinHeight(25);
        Button compile = new Button() ;
        compile.setStyle("-fx-background-color: #00FF00");
        toolbar.getChildren().add(compile) ;
        compile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    compile() ;
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Button run = new Button() ;
        compile.setStyle("-fx-background-color: #FF0000");
        toolbar.getChildren().add(run) ;
        run.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    execute();
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        //create_toolbar() ;


        // master for main
        VBox layout1 =  new VBox() ;
        this.layout1 = layout1 ;
        this.layout0.getChildren().add(layout1) ;

        // for file explorer and text field
        HBox layout2 = new HBox() ;
        this.layout2 = layout2 ;
        this.layout1.getChildren().add(layout2) ;
        this.layout2.setStyle("-fx-background-color: #525251;" +
                "-fx-border-color: black");
        this.layout2.setMinHeight(primary_stage.getHeight()/2);
        create_master() ;

        // terminal
        StackPane layout3 = new StackPane() ;
        this.tlayout = layout3 ;
        this.layout1.getChildren().add(layout3) ;
        this.tlayout.setStyle("-fx-background-color: #525251;-fx-border-color: black");
        this.tlayout.setMinHeight(primary_stage.getHeight()/2-25);
        create_terminal() ;
    }
   // create Label that is the terminal
    void create_terminal(){
        Label terminal = new Label() ;
        terminal.setMaxWidth(Double.MAX_VALUE);
        terminal.setMaxHeight(Double.MAX_VALUE);
        this.terminal = terminal ; 
        this.tlayout.getChildren().add(terminal);
        terminal.setStyle("-fx-background-color:#222222;");
        terminal.setTextFill(Color.color(1, 1, 1));
    }

    // create the explorer
    void create_master() throws IOException {
        // toolbar left
        VBox left_tool = new VBox() ;
        left_tool.setMinWidth(25);
        left_tool.setStyle("-fx-background-color: #525251;" +
                "-fx-border-color: black;"+
                "-fx-border-bottom-style:none;");
        this.layout2.getChildren().add(left_tool) ;

        // file explorer
        VBox explorer = new VBox() ;
        this.explorer = explorer ;
        explorer.setMinWidth(explorer_width);
        explorer.setStyle("-fx-background-color: #525252;" +
                "-fx-border-color: black")  ;
        this.layout2.getChildren().add(explorer) ;
        create_explorer() ;
        add_existing_files() ;



        //button left
        Button expand_fe = getButton();
        left_tool.getChildren().add(expand_fe) ;

        // text area
        tEditor = new TextArea() ;
        tEditor.setStyle("-fx-background-color: #888888;" +
                "-fx-border-color: black")  ;
        tEditor.setMinWidth(this.primary_stage.getWidth()-150);
        this.layout2.getChildren().add(tEditor) ;
        tEditor.setText(get_file_content(topFile));


    }

    // create button for expand file explorer functionality
    private Button getButton() {
        Button expand_fe = new Button() ;
        expand_fe.setMinWidth(explorer_width);

        class FE_expand implements EventHandler<ActionEvent>{
            final VBox explorer ;
            FE_expand(VBox  explorer){
                this.explorer = explorer ;

            }
            @Override
            public void handle(ActionEvent actionEvent) {
                expandFileExplorer();
            }
            private void expandFileExplorer() {

                if (this.explorer.isVisible()){
                    // doesn't work
                    this.explorer.setVisible(false);
                    //this.explorer.setStyle("-fx-background-color: transparent;");
                    this.explorer.setManaged(false);
                }else{
                    this.explorer.setVisible(true);
                    this.explorer.setManaged(true );



                }
            }
        }

        expand_fe.setOnAction(new FE_expand(this.explorer)) ;

        return expand_fe;
    };

    // button for create xplorer
    private void create_explorer( ){
        HBox top = new HBox() ;
        top.setMinHeight(25);
        top.setStyle("-fx-background-color: #525252;" +
                "-fx-border-color: black;"+
                "-fx-border-bottom:mull; "+
                "-fx-border-top:null; ")  ;
        this.explorer.getChildren().add(top) ;
        Button create_file = new Button() ;
        top.getChildren().add(create_file) ;
        top.setStyle("-fx-background-color: #888888");
        create_file.setOnAction(new File_create());

    }







    void save_to_tmp(String content ) throws IOException {
        FileWriter fw = new FileWriter(topFile) ;
        fw.write(content);
        fw.close();
    }

    String get_file_content(File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file ))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        }

        return stringBuilder.toString();
    }

    }

*/


