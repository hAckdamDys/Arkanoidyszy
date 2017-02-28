package Arkanoidyszy;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import static java.lang.StrictMath.abs;

/**
 * Created by Adm on 2017-02-27.
 */
public class Powerup {
    private Circle circle;
//    private PowerupEffect effect; in constructor
    private Padle padle;
    public Circle getCircle() {
        return circle;
    }

    public Powerup(double startX, double startY, Padle padle) {
        this.padle = padle;
        this.circle = new Circle(startX,startY,30);
        this.circle.setFill(new ImagePattern(new Image("powerup_bigballs.png")));
    }
    public PowerupEffectKind fall(){
        double cY = circle.getCenterY();
        double limit=padle.getAppHeight()-padle.getHeight();
        if(cY<limit){
            circle.setCenterY(cY+3);
            if(cY>limit-circle.getRadius() && circle.getCenterX()>padle.getX()-circle.getRadius() && circle.getCenterX()<padle.getX()+padle.getWidth()+circle.getRadius()){
                return PowerupEffectKind.BIGBALL;
            }
            return PowerupEffectKind.NONE;
        }
        return PowerupEffectKind.DELETE;
    }
}
