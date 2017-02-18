package Arkanoidyszy;

import javafx.scene.shape.Rectangle;

public class Padle {
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private double speed;
    private Rectangle rect;

    public Rectangle getRect() {
        return rect;
    }

    public double getX(){
        return rect.getTranslateX();
    }
    private double rectHeight;

    public void setRectWidth(double rectWidth) {
        this.rectWidth = rectWidth;
        this.rect.setWidth(rectWidth);
    }

    private double rectWidth;
    private final int appWidth;
    private final int appHeight;
    public Padle(double rectWidth, double rectHeight, int appWidth, int appHeight, double speed) {
        this.appHeight=appHeight;
        this.appWidth=appWidth;
        this.rectHeight=rectHeight;
        this.rectWidth=rectWidth;
        this.rect=new Rectangle(rectWidth,rectHeight-40);
        this.speed=speed;
    }
    public void move(UserAction action){
        switch (action){
            case LEFT:
                if (rect.getTranslateX() - speed >= 0){
                    rect.setTranslateX(rect.getTranslateX()-speed);
                }
                break;
            case RIGHT:
                if (rect.getTranslateX() + speed <= appWidth - rectWidth){
                    rect.setTranslateX(rect.getTranslateX()+speed);
                }
                break;
            case NONE:
                break;
        }
    }
    public void start(){
        rect.setTranslateX(appWidth/2);
        rect.setTranslateY(appHeight-rectHeight);
        rect.getStyleClass().add("padleStyle");
    }
}
