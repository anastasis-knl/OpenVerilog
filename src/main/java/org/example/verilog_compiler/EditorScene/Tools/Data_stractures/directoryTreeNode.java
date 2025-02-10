package org.example.verilog_compiler.EditorScene.Tools.Data_stractures;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class directoryTreeNode {

    private File getFileInstance;
    private String name;
    // path compared to the project src
    private String relativePath ;
    private List<directoryTreeNode> childNodes;
    private List<File> files;
    private File tempFile;

    public directoryTreeNode(File relative_path, String name) {
        this.getFileInstance = relative_path;
        this.name = name;
        this.childNodes = new LinkedList<>();
        this.files = new LinkedList<>() ;

        String tempFileName = "src/main/resources/tempFiles" ;
        //tempFileName = tempFileName +
        //this.tempFile = new File("")
    }

    public void addChild(directoryTreeNode childNode) {
        this.childNodes.add(childNode);
    }

    public File getGetFileInstance() {
        return getFileInstance;
    }

    public List<directoryTreeNode> getChildNodes() {
        return childNodes;

    }
    public List<File> getFiles(){
        return this.files ;
    }
    public void addFile(String fileName ){
        File file = new File(getFileInstance,fileName);
        this.files.add(file) ;
    }

    public String getName(){return this.name; } ;

}
