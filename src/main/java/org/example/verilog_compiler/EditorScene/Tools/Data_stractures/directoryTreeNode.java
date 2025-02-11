package org.example.verilog_compiler.EditorScene.Tools.Data_stractures;
import java.io.*;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.nio.file.StandardCopyOption;

public class directoryTreeNode {

    private File getFileInstance;
    private String name;

    // path compared to the project src
    private String relativePath ;
    private List<directoryTreeNode> dirNodes;
    private List<directoryTreeNode> fileNodes;
    private File tempFile;
    private boolean isDir  ;
    
    public directoryTreeNode(File relative_path, String name) {
        this.getFileInstance = relative_path;
        this.isDir = relative_path.isDirectory() ; 
        this.name = name;

        this.dirNodes = new LinkedList<>();
        this.fileNodes = new LinkedList<>() ;


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
    public File getTempFileInstance(){return this.tempFile; } ;

    public List<directoryTreeNode> getDirNodes() {
        return dirNodes;

    }
    public List<directoryTreeNode> getFileNodes(){
        return this.fileNodes;
    }




    // need to create either file directory inside the temporary directory
    // parent root dir will pass its own path and this function witl create the path for the temp file

    public void setRelativePath(String path) throws IOException {
        this.relativePath = path + "/" + this.name ;
        create_temp_file();

    }

    public void create_temp_file() throws IOException {
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

            if(isValidFile()) {
                cpFileToTemp() ;

                // add to file list
                this.appendToList() ;
            }else{
                FileOutputStream fs = new FileOutputStream(this.tempFile) ;
                fs.write("Invalid File Type".getBytes());

            }



        }
    }

    private void appendToList() throws IOException {
        String path = this.tempFile.getAbsolutePath();
        FileOutputStream fs = new FileOutputStream("src/main/resources/bin/fileList.txt",true) ;
        fs.write(path.getBytes()) ;
        fs.write("\n".getBytes());

    }

    private Boolean isValidFile() {
        if (this.name.endsWith(".v")
                || this.name.endsWith(".vh")
                || this.name.endsWith(".txt")
                || this.name.endsWith("sv")) {
            return true;
        } else {
            return false;
        }
    }

    // based on the temporary file return the directory tree node connected to it
    // dfs normal tree search
    //return null or return node pointer
    public static directoryTreeNode searchByTemp(directoryTreeNode root , File temp){
        directoryTreeNode  answer = null ; 
        if (root.isDir) {
            for (directoryTreeNode dirNode : root.getDirNodes()) {
                answer = searchByTemp(dirNode , temp);
                if (answer != null) {
                    return answer ; 
                }
            }

            for (directoryTreeNode fileNode : root.getFileNodes()) {
                if (temp == fileNode.getTempFileInstance()){
                    return fileNode ;
                }
            }
        }else{
            if (temp == root.getTempFileInstance()) {
                return root;
            }
        }
        return null ;
    }

    private void cpFileToTemp() {
        try {
            Files.copy(this.getFileInstance.toPath(), this.tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getRelativeTempPath(){
        return this.relativePath ;
    }
    public String getName(){return this.name; } ;

}
