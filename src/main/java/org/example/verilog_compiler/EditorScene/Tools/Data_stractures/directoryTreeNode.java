package org.example.verilog_compiler.EditorScene.Tools.Data_stractures;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class directoryTreeNode {

    private File getFileInstance;
    private String name;

    // path compared to the project src
    private String relativePath ;
    private List<directoryTreeNode> dirNodes;
    private List<directoryTreeNode> fileNodes;
    private File tempFile;

    public directoryTreeNode(File relative_path, String name) {
        this.getFileInstance = relative_path;
        this.name = name;

        this.dirNodes = new LinkedList<>();
        this.fileNodes = new LinkedList<>() ;

        String tempFileName = "src/main/resources/tempFiles" ;
        //tempFileName = tempFileName +
        //this.tempFile = new File("")

    }

    public void addDirNode(directoryTreeNode childNode) {
        this.dirNodes.add(childNode);
    }

    public void addFileNode(directoryTreeNode fileNode ){
        // now it adds a tree node not a File object
        this.fileNodes.add(fileNode) ;
    }

    public File getGetFileInstance() {
        return getFileInstance;
    }

    public List<directoryTreeNode> getDirNodes() {
        return dirNodes;

    }
    public List<directoryTreeNode> getFileNodes(){
        return this.fileNodes;
    }




    // need to create either file directory inside the temporary directory
    // parent root dir will pass its own path and this function witl create the path for the temp file

    public void setRelativePath(String path){
        this.relativePath = path + "/" + this.name ;
        create_temp_file();

    }

    public void create_temp_file(){
        if (this.getFileInstance.isDirectory()) {
            File directory = new File(this.relativePath) ;
            directory.mkdirs() ;
            this.tempFile = directory ;

        }else{
            File file = new File(this.relativePath)  ;
            try {
                // Create the file if it does not exist
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());
                } else {
                    System.out.println("File already exists.");
                }
            } catch (IOException e) {
                // Handle the exception if file creation fails
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            this.tempFile = file  ;

        }
    }

    public String getRelativeTempPath(){
        return this.relativePath ;
    }
    public String getName(){return this.name; } ;

}
