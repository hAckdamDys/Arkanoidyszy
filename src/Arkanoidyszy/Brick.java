package Arkanoidyszy;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Brick {
    public static final int BRICKSIZEWIDTH=60,BRICKSIZEHEIGHT=45;
    private Rectangle rect;
    private BrickKind kind;
    private int hp;
    private int hpStart;
    private int score;
    private Image myImage;
    private Powerups powerups;
    private PowerupEffectKind effect;

    public BrickKind getKind() {
        return kind;
    }

    public void setPowerups(Powerups powerups) {
        this.powerups = powerups;
    }

    public void setEffect(PowerupEffectKind effect){
        this.effect=effect;
    }

    public void restartBrick(){
        this.hp=hpStart;
        if(kind==BrickKind.BASIC) {
            chooseImage(hp);
        }
        else if(kind==BrickKind.METAL) {myImage = new Image("sadbrick.png"); rect.setFill(new ImagePattern(myImage));}
        else if(kind==BrickKind.POWERUP) {myImage = new Image("happybrick.png"); rect.setFill(new ImagePattern(myImage));}
    }
    private void init(BrickKind kind,int x,int y,int hp,int score){
        this.rect=new Rectangle(x,y,BRICKSIZEWIDTH,BRICKSIZEHEIGHT);
        hpStart=hp;
        this.score=score;
        this.kind=kind;
        restartBrick();
    }
    public Brick(BrickKind kind,int x,int y,int hp,int score){
        init(kind,x,y,hp,score);
    }
    public Brick(BrickKind kind,int x,int y,int hp,int score,PowerupEffectKind effect){
        init(kind,x,y,hp,score);
        setEffect(effect);
    }
    public int takeHit(){
        if(kind!=BrickKind.METAL){--hp;}
        if(hp<=0){
            if(kind==BrickKind.POWERUP){
                if(powerups==null || effect==null){
                    System.out.println("powerup musi byc zainicjalizowany dla brickow powerup");
                }
                else{
                    powerups.add(rect.getX()+BRICKSIZEWIDTH/2,rect.getY()+BRICKSIZEHEIGHT/2,effect);
                }
            }
            return score;
        }
        if(hp>0 && kind==BrickKind.BASIC){
            chooseImage(hp);
        }
        return 0;
    }
    public Rectangle getRect() {
        return rect;
    }
    private void chooseImage(int hp){
        switch (hp) {
            case 1:
                myImage = new Image("brick1.png");
                break;
            case 2:
                myImage = new Image("brick2.png");
                break;
            case 3:
                myImage = new Image("brick3.png");
                break;
            case 4:
                myImage = new Image("brick4.png");
                break;
            case 5:
                myImage = new Image("brick5.png");
                break;
            default:
                myImage = new Image("brick6.png");
                break;
        }
        rect.setFill(new ImagePattern(myImage));
    }
}
