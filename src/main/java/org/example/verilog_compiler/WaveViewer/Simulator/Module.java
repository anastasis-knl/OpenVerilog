package org.example.verilog_compiler.WaveViewer.Simulator;

import java.util.LinkedList;

public class Module {
    // a tree stractured module system arround the root module likely the tb

    LinkedList<Module> submodules ;
    LinkedList<String> vars ;
    LinkedList<String> var_names ;
    String name;

    public Module(String name) {
        this.name = name ;
        this.vars = new LinkedList<>() ;
        this.var_names = new LinkedList<>() ;
        this.submodules = new LinkedList<>()  ;
    }

    public void addVar(String name ,String var) {
        this.vars.add(var) ;
        this.var_names.add(name)  ; //  different name for same var on different module
    }

    public void addSubmodule(Module sub) {
        this.submodules.add(sub)  ;
    }

    public LinkedList<Module> getSubmodules() {
        return this.submodules;
    }

    public LinkedList<String> getVars(){
        return this.vars ;
    }
}
