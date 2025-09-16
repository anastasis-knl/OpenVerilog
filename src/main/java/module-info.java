module org.example.verilog_compiler {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    exports org.verilog_compiler.EditorScene; // Export the package containing your controller
    opens org.verilog_compiler.EditorScene to javafx.fxml; // Open the package to javafx.fxml for reflection

    exports org.verilog_compiler.WaveViewer; // Export the package containing your controller
    opens org.verilog_compiler.WaveViewer to javafx.fxml; // Open the package to javafx.fxml for reflection

    opens org.verilog_compiler to javafx.fxml;
    exports org.verilog_compiler;
    exports org.verilog_compiler.SelectorScene;
    opens org.verilog_compiler.SelectorScene to javafx.fxml;
}