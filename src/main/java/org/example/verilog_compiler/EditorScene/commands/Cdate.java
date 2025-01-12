package org.example.verilog_compiler.EditorScene.commands;

public class Cdate implements commandType {
    String data;
    @Override
    public int getType() {
        return 1;
    }

    @Override
    public String getData() {
        return this.data;
    }

    @Override
    public void setData(String data ) {
        this.data = data ;

    }

}
