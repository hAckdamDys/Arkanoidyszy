package Arkanoidyszy;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ALevel_2 extends ALevel{
    @Override
    protected void makeGrid(){
        Brick[] bricks = new Brick[15];
        for (int i = 0; i < 5; i++) {
            bricks[i] = new Brick(BrickKind.POWERUP,120+i*Brick.BRICKSIZEWIDTH,120,1,100);
            bricks[5+i] = new Brick(BrickKind.BASIC,120+i*Brick.BRICKSIZEWIDTH,120+Brick.BRICKSIZEHEIGHT,5,200);
            bricks[10+i] = new Brick(BrickKind.BASIC,120+i*Brick.BRICKSIZEWIDTH,120+2*Brick.BRICKSIZEHEIGHT,1,100);
        }
        grid = new BrickGrid(appWidth,appHeight,bricks);
    }
    public ALevel_2(Stage window){
        initALevel(window);
    }
}
