package Arkanoidyszy;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class ALevel_2 extends ALevel{
    public ALevel_2(Stage window, ALevel nextLevel) {
        super(window, nextLevel);
    }

    @Override
    protected void makeGrid(){
        Brick[] bricks = new Brick[30];
        for (int i = 0; i < 5; i++) {
            bricks[i] = new Brick(BrickKind.POWERUP,120+i*Brick.BRICKSIZEWIDTH,120,1,100, PowerupEffectKind.BIGBALL);
            bricks[5+i] = new Brick(BrickKind.BASIC,120+i*Brick.BRICKSIZEWIDTH,120+Brick.BRICKSIZEHEIGHT,5,200);
            bricks[10+i] = new Brick(BrickKind.POWERUP,120+i*Brick.BRICKSIZEWIDTH,120+2*Brick.BRICKSIZEHEIGHT,1,100,PowerupEffectKind.LONGPADLE);
        }
        for (int i = 0; i < 5; i++) {
            bricks[15+i] = new Brick(BrickKind.POWERUP,440+i*Brick.BRICKSIZEWIDTH,60,1,100, PowerupEffectKind.MULTIBALL);
            bricks[20+i] = new Brick(BrickKind.BASIC,440+i*Brick.BRICKSIZEWIDTH,60+Brick.BRICKSIZEHEIGHT,1,200);
            bricks[25+i] = new Brick(BrickKind.POWERUP,440+i*Brick.BRICKSIZEWIDTH,60+2*Brick.BRICKSIZEHEIGHT,1,100,PowerupEffectKind.LONGPADLE);
        }
        grid = new BrickGrid(appWidth,appHeight,bricks);
    }
}
