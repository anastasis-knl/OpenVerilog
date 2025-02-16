package org.example.verilog_compiler.EditorScene;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.text.TextFlow;

import java.io.*;

public class TabController {
    // future use for manipulating text
    // for extra things ...
    // used for what exaclty just here in order to put
    // the new text inside once the button is press


    @FXML
    TextArea line ;

    @FXML
    TextArea textEditor;

    Integer maxline =  0 ;


    // write when the new tab is opened the temp file contents on the text editor

    public void initialize(){
        textEditor.setOnKeyPressed(event -> {

            Integer lines = getLineCount(this.textEditor);
            if (maxline< lines  ) {
                // write the line numbers
                for (Integer i = maxline ; i < lines ; i ++) {
                    line.appendText(i +"\n" );
                }
                maxline = lines ;
            }

        });


    }

    private int getLineCount(TextArea textArea) {
        String text = textArea.getText();
        // Count how many new lines there are by splitting the text by the newline character
        String[] lines = text.split("\n");
        return lines.length;
    }

    public void write(File file) throws IOException {
        FileInputStream fs = new FileInputStream(file) ;
        InputStreamReader isr = new InputStreamReader(fs);
        BufferedReader reader = new BufferedReader(isr);

        String ln ;
        while ((ln = reader.readLine()) != null) {
            // write on the text area
            textEditor.appendText(ln) ;
            textEditor.appendText("\n") ;

        }
    }

}
