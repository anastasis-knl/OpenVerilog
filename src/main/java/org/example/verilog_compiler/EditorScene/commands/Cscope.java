package org.example.verilog_compiler.EditorScene.commands;

public class Cscope implements commandType{
    String data ;
    @Override
    public int getType() {
        return 5;
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public void setData(String data) {
        this.data = data;
    }
}
