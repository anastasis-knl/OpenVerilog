package org.example.verilog_compiler.EditorScene.commands;

public class Carray implements commandType{
    String data ;
    int size;
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

    public void setSize(Integer integer) {
        this.size = integer ;
    }
    public int getSize() {
        return this.size;
    }

}
