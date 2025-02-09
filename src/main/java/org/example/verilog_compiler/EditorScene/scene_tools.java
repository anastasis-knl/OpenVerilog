package org.example.verilog_compiler.EditorScene;

import org.example.verilog_compiler.EditorScene.Tools.Data_stractures.directoryTreeNode;

import javax.swing.tree.TreeNode;
import java.io.File;

public class scene_tools {

    public directoryTreeNode getDirectoryTree(File dirPath){

        // dir path -> absolute path to directory
        directoryTreeNode root = new directoryTreeNode(dirPath, dirPath.getName()) ;

        // dir path is the path to parent directory
        navigateDir(dirPath,  root);


    return root ;

}

    private void navigateDir(File dirPath , directoryTreeNode curerntDir ){


        if (dirPath.isDirectory()) {
         // Get all files and directories in the specified directory
            File[] files = dirPath.listFiles();

        // If files are not null, loop through them
            if (files != null) {
                for (File file : files) {

                    // handle directories
                    if (file.isDirectory()) {


                        File newDirectoryPath = new File(dirPath, file.getName());

                        // create new node for the tree
                        directoryTreeNode child = new directoryTreeNode(newDirectoryPath , file.getName()) ;
                        curerntDir.addChild(child);

                        // recursively navigate depth first directory
                        navigateDir(newDirectoryPath, curerntDir);

                    // handle files
                    } else if (file.isFile()) {
                        curerntDir.addFile(file.getName());
                    }
                }
            }
     } else {
        System.out.println(dirPath.getName() + " is not a valid directory.");
    }
}

};




























































