package org.example.verilog_compiler.EditorScene;

import org.example.verilog_compiler.EditorScene.Tools.Data_stractures.directoryTreeNode;
import org.example.verilog_compiler.EditorScene.Tools.Data_stractures.fileExplorerTreeNode;

import java.io.File;

public class scene_tools {

    public static directoryTreeNode getDirectoryTree(File dirPath){

        // dir path -> absolute path to directory
        directoryTreeNode root = new directoryTreeNode(dirPath, dirPath.getName()) ;

        // dir path is the path to parent directory
        navigateDir(dirPath,  root);


    return root ;

}

    public static void createFileExplorer(directoryTreeNode root , fileExplorerTreeNode rootFE){
        // create root button bfs on dfs on root and create the rootFE one node left one node right

        // the two roots are ready
        dfs_twoTrees(root , rootFE );
        // create root children buttons
    }

    private static void dfs_twoTrees(directoryTreeNode root , fileExplorerTreeNode rootFE) {

        for (directoryTreeNode child : root.getChildNodes()) {
            // create the child node
            fileExplorerTreeNode childFE = new fileExplorerTreeNode(child.getName(),
                    rootFE.getLevel() + 6, rootFE.getFE(), root.getGetFileInstance().isDirectory(),
                    root, rootFE.getEditorTabs());

            // dfs on the child of hte node
            dfs_twoTrees(child, childFE);
            rootFE.add_child(childFE);

        }

        for(File file : root.getFiles()) {
            fileExplorerTreeNode childFE = new fileExplorerTreeNode(file.getName(),
                    rootFE.getLevel() + 3, rootFE.getFE(), false,
                    root, rootFE.getEditorTabs());
            rootFE.add_child(childFE);

        }
    }


    // working now
    private static void navigateDir(File dirPath , directoryTreeNode currentDir ){


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
                        currentDir.addChild(child);

                        // recursively navigate depth first directory
                        navigateDir(newDirectoryPath, child);

                    // handle files
                    } else  {
                        currentDir.addFile(file.getName());
                    }
                }
            }
     } else {
        System.out.println(dirPath.getName() + " is not a valid directory.");
    }
}



};




























































