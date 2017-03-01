package Arkanoidyszy;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ALevel_4 extends ALevel{
    public ALevel_4(Stage window, ALevel nextLevel) {
        super(window, nextLevel);
    }

    @Override
    protected void makeGrid(){
        Brick[] bricks = new Brick[49];
        /*ArrayList<Integer>[] list = new ArrayList[8];
        for(int i=0;i<8;i++){
            list[i]=new ArrayList<Integer>();
        }
        for (int i=0; i<7; i++) {
            for(int j=0;j<8;j++) {
                list[j].add(new Integer(i));
            }
        }
        for (int i = 0; i < 8; i++) {
            Collections.shuffle(list[i]);
        }


        for (int i = 0; i < 49; i++) {
            bricks[i] = new Brick(BrickKind.values()[new Random().nextInt(BrickKind.values().length)], Brick.BRICKSIZEWIDTH*list[0].get(i/7),Brick.BRICKSIZEHEIGHT*list[1+i/7].get(i%7),(int)(Math.random()*7),500, PowerupEffectKind.values()[new Random().nextInt(PowerupEffectKind.values().length)]);
        }
        */
        ArrayList<Integer> list = new ArrayList<Integer>();
        ArrayList<BrickKind> list2 = new ArrayList<BrickKind>();
        list2.add(BrickKind.BASIC);
        list2.add(BrickKind.POWERUP);
        for (int i=0; i<70; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i = 0; i < 49; i++) {
            bricks[i] = new Brick(list2.get((int)(Math.random()*2)), (int)(Brick.BRICKSIZEWIDTH)*(int)(list.get(i)/6),(int)(Brick.BRICKSIZEHEIGHT)*(int)(list.get(i)%10),(int)(Math.random()*7),500, PowerupEffectKind.values()[new Random().nextInt(PowerupEffectKind.values().length)]);
        }
        grid = new BrickGrid(appWidth,appHeight,bricks);
    }
}
