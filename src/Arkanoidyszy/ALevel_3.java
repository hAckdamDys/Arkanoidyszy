package Arkanoidyszy;

import javafx.stage.Stage;

public class ALevel_3 extends ALevel{
    public ALevel_3(Stage window, ALevel nextLevel) {
        super(window, nextLevel);
    }

    @Override
    protected void makeGrid(){
        Brick[] bricks = new Brick[40];
        for (int i = 0; i < 10; i++) {
            bricks[i] = new Brick(BrickKind.POWERUP,0+i*Brick.BRICKSIZEWIDTH,0,2,200, PowerupEffectKind.BIGBALL);
            bricks[10+i] = new Brick(BrickKind.BASIC,appWidth+(i-10)*Brick.BRICKSIZEWIDTH,0+Brick.BRICKSIZEHEIGHT,5,500);
            bricks[20+i] = new Brick(BrickKind.POWERUP,0+i*Brick.BRICKSIZEWIDTH,100+2*Brick.BRICKSIZEHEIGHT,2,200,PowerupEffectKind.MULTIBALL);
            bricks[30+i] = new Brick(BrickKind.POWERUP,0+i*Brick.BRICKSIZEWIDTH,100+3*Brick.BRICKSIZEHEIGHT,2,200,PowerupEffectKind.LONGPADLE);
        }
        grid = new BrickGrid(appWidth,appHeight,bricks);
    }
}
