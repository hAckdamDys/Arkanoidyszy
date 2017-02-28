package Arkanoidyszy;

import javafx.scene.shape.Rectangle;

public class Padle {
    private double speed;
    private Rectangle rect;
    private double rectHeight;
    private double rectWidth;
    private final int appWidth;
    private final int appHeight;

    public int getAppHeight() {
        return appHeight;
    }

    public double getWidth(){
        return rectWidth;
    }
    public double getHeight(){
        return rectHeight;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getX(){
        return rect.getTranslateX();
    }

    public void setRectWidth(double rectWidth) {
        this.rectWidth = rectWidth;
        this.rect.setWidth(rectWidth);
    }
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
