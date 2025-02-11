package org.example.verilog_compiler.EditorScene.CandR;

import javafx.scene.control.TextArea;
import org.example.verilog_compiler.EditorScene.Tools.Data_stractures.directoryTreeNode;
import org.example.verilog_compiler.SceneSelector;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Compile {
    private static File compFile = new File("src/main/resources/bin/compiledFiles/designCompiled") ;

    // global save temp to real data
    // compile
    // 1. you have the file tree and everything init if you want ot compile differnet dir files etc
    // compile all and run as root one file only the one you currently are at


    String pathTolst = "src/main/resources/bin/fileList.txt";
    String pathToCompiled = "src/main/resources/bin/compiledFiles";
    String pathToDatadump = "src/main/resources/bin/datadump/dump.vcd";
    File filelst ;
    File compdir ;
    File dataDump ;

    String rootModule ;
    static String dir = "src/main/resources/bin"  ;

    public Compile(){
        File filelst = new File(pathTolst)  ;
        File compiledFilelst = new File(pathToCompiled);

        this.dataDump = new File(pathToDatadump) ;
        this.filelst = filelst ;
        this.compdir = compiledFilelst;
    }

    public void compile(String rootModule, directoryTreeNode testBench) throws IOException, InterruptedException {

        // format the file with dumpvars
        this.rootModule = rootModule ;
        this.formatFile2Compile(testBench) ;

        //% iverilog -s main -o hello hello.v
        //% iverilog -o my_design -c file_list.txt% iverilog -o my_design -c file_list.txt

        List<String> command = new ArrayList<>();
        command.add("iverilog");
        command.add("-s");
        command.add(rootModule) ;
        command.add("-o") ;
        command.add(this.compdir.getAbsolutePath()+"/designCompiled") ;
        command.add("-c") ;
        command.add(this.filelst.getAbsolutePath()) ;


        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();
        update_console(process) ;
    }

    private void formatFile2Compile(directoryTreeNode testBench) {

        File tb = testBench.getGetFileInstance();
        try {

            List<String> lines = Files.readAllLines(Paths.get(tb.toURI()));

            List<String> writeLines = List.of("  initial", "    begin","  " +
                    "    $dumpfile(\"" + this.dataDump.getAbsolutePath()+"\");", // add dump file place
                    "      $dumpvars(0,"+ this.rootModule+ ");", // add root module name for dump relation
                    "    end",
                    "endmodule") ;


            // Ensure the file is not empty
            if (!lines.isEmpty()) {
                // Remove the last line
                lines.remove(lines.size() - 1);
                // Write the updated lines back to the file
                Files.write(Paths.get(tb.toURI()), lines, StandardCharsets.UTF_8);
            } else {
                System.out.println("File is empty. Nothing to remove.");
            }
            Files.write(Paths.get(tb.toURI()), writeLines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void run() throws IOException, InterruptedException {
        List<String> command = new ArrayList<>();
        command.add("vvp");
        command.add(compFile.getAbsolutePath());
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        // set at what directory must the console open
        Process process = processBuilder.start();

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
            writer.write("\n") ;
            writer.write("finish"); // The input you want to provide
            writer.newLine();
            writer.flush();
        }

        process.waitFor(1, TimeUnit.SECONDS) ;

        update_console(process) ;


    }

    static void update_console(Process process) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader readers = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        // Global controller is responsible for linkage
        TextArea console = SceneSelector.get_controller().getEditor().getConsole();

        String lines =null;
        console.appendText("\n");

        while((lines = reader.readLine())!=null) {
            console.appendText("\n") ;
            console.appendText(lines);
        }

        String lines1 =null;
        while((lines1 = readers.readLine())!=null) {
            console.appendText("\n") ;

            console.appendText(lines1);
        }
        console.appendText("\n") ;

        int exitCode = process.waitFor();
        console.appendText("\nCommand vd with exit code: " + exitCode);


    }


    // ask for compile instructions or preferences .... [FUTURE]

}


