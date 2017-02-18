package Arkanoidyszy;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ALevel_1 extends ALevel{
    @Override
    protected void makeGrid(){
        Brick[] bricks = new Brick[30];
        for (int i = 0; i < 10; i++) {
            bricks[i] = new Brick(BrickKind.BASIC,40+i*Brick.BRICKSIZEWIDTH,30,1,100);
            bricks[10+i] = new Brick(BrickKind.BASIC,40+i*Brick.BRICKSIZEWIDTH,30+Brick.BRICKSIZEHEIGHT,2,200);
            bricks[20+i] = new Brick(BrickKind.BASIC,40+i*Brick.BRICKSIZEWIDTH,30+2*Brick.BRICKSIZEHEIGHT,1,100);
        }
        grid = new BrickGrid(appWidth,appHeight,bricks);
    }
    public ALevel_1(Stage window){
        initALevel(window);
    }
}
