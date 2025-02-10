package org.example.verilog_compiler;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

public  class Start extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Stage primary_stage = new Stage() ;
        primary_stage.setTitle("Compiler");
        SceneSelector controller = SceneSelector.get_controller() ;
        controller.setPrimaryStage(primary_stage);
        controller.launch_Selector();

    }
    public static void main(String[] args){

        launch(args) ;

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                // Your shutdown logic goes here
                System.out.println("Performing shutdown routine...");
                this.delete_temp_files() ;
                // You can close resources, save files, etc.
            }
            void delete_temp_files(){
                File tempDirectory = new File("src/main/resources/tempFiles") ;
                deleteDirectoryContents(tempDirectory);
            }

            // needs cleaner update in the future works for now
            public static void deleteDirectoryContents(File directory) {
                // Check if the directory exists and is indeed a directory
                if (directory.exists() && directory.isDirectory()) {
                    // Get all files and subdirectories in the directory
                    File[] files = directory.listFiles();

                    if (files != null) {
                        // Loop through each file and delete it or recurse if it's a subdirectory
                        for (File file : files) {
                            if (file.isDirectory()) {
                                // If it's a directory, recursively delete its contents
                                deleteDirectoryContents(file);
                            }
                            // Delete the file or the empty directory
                            if (file.delete()) {
                                System.out.println("Deleted: " + file.getAbsolutePath());
                            } else {
                                System.out.println("Failed to delete: " + file.getAbsolutePath());
                            }
                        }
                    }

                } else {
                    System.out.println("The specified path is not a valid directory or it does not exist.");
                }
            }


        });

    }

}



