package org.example.verilog_compiler.EditorScene.Simulator.commands;

public class Cdump implements commandType{
    String data ;
    @Override
    public int getType() {
        return 2;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String data) {
        this.data = data ;
    }
}
