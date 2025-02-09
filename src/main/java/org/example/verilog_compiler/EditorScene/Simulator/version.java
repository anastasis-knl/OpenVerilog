package org.example.verilog_compiler.EditorScene.Simulator;

import org.example.verilog_compiler.EditorScene.Simulator.commands.commandType;

public class version implements commandType {
    String data;
    @Override
    public int getType() {
        return 0;
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public void setData(String data) {
        this.data = data ;
    }

}
