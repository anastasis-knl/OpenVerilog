package org.example.verilog_compiler.EditorScene.Simulator;//package org.example.verilog_compiler.EditorScene;
//
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.Group;
//import javafx.scene.shape.Line;
//import javafx.scene.control.Label;
//
//public class Graph extends StackPane {
//    // layout -> canvas
//    //
//    private Canvas canvas;
//    private int time ;
//    char prev ;
//    GraphicsContext gc ;
//    private Group group ;
//    Label l ;
//
//    Graph() {
//        this.setStyle("-fx-background-color:#000000");
//        this.setMinHeight(40);
//
//
//        Canvas canvas = new Canvas() ;
//        this.canvas = canvas;
//
//        prev = 'z' ;
//        this.gc = canvas.getGraphicsContext2D() ;
//
//        this.change_state();
//
//    }
//    void resize(){
//        this.setHeight(15);
//        this.setMaxWidth(Double.MAX_VALUE);
//
//
//        this.getChildren().add(canvas) ;
//
//        this.canvas.setHeight(40);
//        this.canvas.setWidth(this.getWidth());
//        gc.setFill(Color.BLUE);
//        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        gc.fillText("something" , 5,5 ) ;
//
//    }
//    void change_state(){
//        if (this.isVisible()){
//            // doesn't work
//            this.setVisible(false);
//            //this.explorer.setStyle("-fx-background-color: transparent;");
//            this.setManaged(false);
//        }else{
//            this.setVisible(true);
//            this.setManaged(true );
//        }
//
//    }
//    void update(char opt , int time){
//        System.out.println("updated "+ opt+ " time: "+ time );
//
//        if(opt== 'z' ) {
//            draw_line(time,Color.BLUE,14 );
//        }
//        else if(opt== 'x') {
//            draw_line(time,Color.RED,14 );
//
//        }else if(opt == '0') {
//            draw_line(time,Color.GREEN,2 );
//
//        }else if(opt == '1') {
//            draw_line(time,Color.GREEN,28 );
//
//        }
//        this.time = time ;
//
//    }
//    void draw_line(int time  , Color cl , int pos){
//
//        gc.strokeLine(this.time , pos , time , pos);
//
//        System.out.println("==========") ;
//        }
//    void update(int time){
//        if(this.time == time){
//            return ;
//        }
//        update(prev , time) ;
//    }
//}


//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//
//public class Graph extends StackPane {
//    private Canvas canvas;
//    private int time;
//    private char prev;
//    private GraphicsContext gc;
//
//    public Graph() {
//        this.setStyle("-fx-background-color:#000000");
//        this.setMinHeight(40);
//
//        canvas = new Canvas();
//        canvas.setHeight(40);
//
//        // Set up the GraphicsContext
//        gc = canvas.getGraphicsContext2D();
//
//        this.getChildren().add(canvas);
//
//        this.change_state();
//    }
//
//    @Override
//    protected void layoutChildren() {
//        super.layoutChildren();
//        canvas.setWidth(this.getWidth());
//        redrawCanvas();
//    }
//
//    private void redrawCanvas() {
//        // Set up the GraphicsContext
//        gc.setFill(Color.PURPLE);
//        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        draw_line(time, Color.BLUE, 14);
//    }
//    void change_state() {
//        if (this.isVisible()) {
//            this.setVisible(false);
//            this.setManaged(false);
//        } else {
//            this.setVisible(true);
//            this.setManaged(true);
//        }
//    }
//
//    void update(char opt, int time) {
//        System.out.println("updated " + opt + " time: " + time);
//
//        if (opt == 'z') {
//            draw_line(time, Color.BLUE, 14);
//        } else if (opt == 'x') {
//            draw_line(time, Color.RED, 14);
//        } else if (opt == '0') {
//            draw_line(time, Color.GREEN, 2);
//        } else if (opt == '1') {
//
//            draw_line(time, Color.GREEN, 28);
//        }
//        gc.setFill(Color.RED);
//        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//
//        super.layoutChildren();
//        this.time = time;
//    }
//
//    void draw_line(int time, Color cl, int pos) {
//        gc.setStroke(cl);
//        gc.strokeLine(this.time, pos, time, pos);
//    }
//
//    void update(int time) {
//        if (this.time == time) {
//            return;
//        }
//        update(prev, time);
//    }
//}
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Graph extends Group {
    private int time;
    private char prev;

    Graph(VBox upper ) {
        super() ;

        this.setStyle("-fx-background-color:#000000");

        Rectangle rect = new Rectangle(upper.getWidth(), 40) ;
        rect.setFill(Color.BLACK);
        rect.setLayoutX(0);
        rect.setLayoutY(0);

        this.getChildren().add(rect) ;

        this.change_state();
    }

    void change_state() {

        if (this.isVisible()) {
            this.setVisible(false);
            this.setManaged(false);
        } else {
            this.setVisible(true);
            this.setManaged(true);
        }
    }

    void update(char opt, int time) {
        System.out.println("updated " + opt + " time: " + time);

        if (opt == 'z') {
            draw_line(time, Color.BLUE, 14);
        } else if (opt == 'x') {
            draw_line(time, Color.RED, 14);
        } else if (opt == '0') {
            draw_line(time, Color.GREEN, 2);
        } else if (opt == '1') {

            draw_line(time, Color.GREEN, 28);
        }

       // super.layoutChildren();
        this.time = time;
    }

    void draw_line(int time, Color cl, int pos) {
        Rectangle rect = new Rectangle((time-this.time)*4,4) ;
        rect.setFill(cl);
        rect.setLayoutX(this.time*4);
        rect.setLayoutY(pos);

        this.getChildren().add(rect) ;

    }

    void update(int time) {
        if (this.time == time) {
            return;
        }
        update(prev, time);
    }
}
