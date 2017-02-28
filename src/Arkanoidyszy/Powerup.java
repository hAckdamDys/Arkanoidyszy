package Arkanoidyszy;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Created by Adm on 2017-02-27.
 */
public class Powerup {
    private Circle circle;
//    private PowerupEffect effect; in constructor
    private int appHeight;
    public Circle getCircle() {
        return circle;
    }

    public Powerup(double startX, double startY, int appHeight) {
        this.appHeight = appHeight;
        this.circle = new Circle(startX,startY,30);
        this.circle.setFill(new ImagePattern(new Image("powerup_bigballs.png")));
    }
    public boolean fall(){
        double cY = circle.getTranslateY();
        if(cY<appHeight){
            circle.setTranslateY(cY+3);
            System.out.println(cY);
            return false;
        }
        return true;
    }
}
