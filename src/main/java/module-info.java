module org.example.verilog_compiler {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.verilog_compiler to javafx.fxml;
    exports org.example.verilog_compiler;
    exports org.example.verilog_compiler.SelectorScene;
    opens org.example.verilog_compiler.SelectorScene to javafx.fxml;
}