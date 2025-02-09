module org.example.verilog_compiler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports org.example.verilog_compiler.EditorScene; // Export the package containing your controller
    opens org.example.verilog_compiler.EditorScene to javafx.fxml; // Open the package to javafx.fxml for reflection

    opens org.example.verilog_compiler to javafx.fxml;
    exports org.example.verilog_compiler;
    exports org.example.verilog_compiler.SelectorScene;
    opens org.example.verilog_compiler.SelectorScene to javafx.fxml;
}