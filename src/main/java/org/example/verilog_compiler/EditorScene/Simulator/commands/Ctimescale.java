package org.example.verilog_compiler.EditorScene.Simulator.commands;

public class Ctimescale implements commandType{
    String data ;
    @Override
    public int getType() {
        return 6;
    }

    @Override
    public String getData() {
        return data;
    }

    @Override
    public void setData(String data ) {
        this.data= data ;
    }
}
