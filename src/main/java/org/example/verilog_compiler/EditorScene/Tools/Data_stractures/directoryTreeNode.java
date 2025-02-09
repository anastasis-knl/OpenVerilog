package org.example.verilog_compiler.EditorScene.Tools.Data_stractures;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class directoryTreeNode {

    private File relative_path;
    private String name;
    private List<directoryTreeNode> childNodes;
    private List<File> files;


    public directoryTreeNode(File relative_path, String name) {
        this.relative_path = relative_path;
        this.name = name;
        this.childNodes = new LinkedList<>();
        this.files = new LinkedList<>() ;
    }

    public void addChild(directoryTreeNode childNode) {
        this.childNodes.add(childNode);
    }

    public File getRelative_path() {
        return relative_path;
    }

    public List<directoryTreeNode> getChildNodes() {
        return childNodes;

    }
    public List<File> getFiles(){
        return this.files ;
    }
    public void addFile(String fileName ){
        File file = new File(relative_path,fileName);
        this.files.add(file) ;
    }

    public String getName(){return this.name; } ;

}
