package Arkanoidyszy;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * Created by Adm on 2017-02-27.
 */
public class Powerups {
    private Ball[] balls;
    private Padle padle;
    //struktura z powerupami
    private Powerup[] powerup;
    private ObservableList<Node> children;

    public Powerups(Ball[] balls, Padle padle) {
        this.balls = balls;
        this.padle = padle;
        this.powerup=new Powerup[30];
    }

    public void add(double x,double y){
        for (int i = 0; i < 30; i++) {
            if(powerup[i]==null){
                powerup[i]=new Powerup(x,y,this.padle.getAppHeight());
                children.add(powerup[i].getCircle());
                break;
            }
        }
    }

    public void fall(){
        for (int i = 0; i < 30; i++) {
            if(powerup[i]!=null){
                if(powerup[i].fall()) {
                    children.remove(powerup[i].getCircle());
                }
            }
        }
    }

    public void addChildren(ObservableList<Node> children) {
        this.children=children;
    }
}
