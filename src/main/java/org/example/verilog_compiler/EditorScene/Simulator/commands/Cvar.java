package org.example.verilog_compiler.EditorScene.Simulator.commands;

public class Cvar implements commandType{
    String data ;
    @Override
    public int getType() {
        return 8;
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
