package Arkanoidyszy;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import static java.lang.StrictMath.abs;

/**
 * Created by Adm on 2017-02-27.
 */
public class Powerup {
    private final PowerupEffectKind effect;
    private Circle circle;
//    private PowerupEffect effect; in constructor
    private Padle padle;
    public Circle getCircle() {
        return circle;
    }

    public Powerup(double startX, double startY, Padle padle, PowerupEffectKind effect) {
        this.padle = padle;
        this.effect = effect;
        this.circle = new Circle(startX,startY,20);
        this.circle.setFill(new ImagePattern(new Image("powerup_bigballs.png")));
    }
    public PowerupEffectKind fall(){
        double cY = circle.getCenterY();
        double limit=padle.getAppHeight()-padle.getHeight();
        if(cY<limit){
            circle.setCenterY(cY+2);
            if(cY>limit-circle.getRadius() && circle.getCenterX()>padle.getX()-circle.getRadius() && circle.getCenterX()<padle.getX()+padle.getWidth()+circle.getRadius()){
                return effect;
            }
            return PowerupEffectKind.NONE;
        }
        return PowerupEffectKind.DELETE;
    }
}
