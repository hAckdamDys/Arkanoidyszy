package Arkanoidyszy;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * Created by Adm on 2017-02-27.
 */
public class Powerups {
    private static final int powerUpBufforSize=30;

    private Ball[] balls;
    private Padle padle;

    private Powerup[] powerup;//array of powerups
    private boolean[] powerupspots;
    private ObservableList<Node> children;//adding powerups to it so they can be seen
    private PowerupEffect[] effects;//array of effects from powerups

    public void clear(){//after losing life powerups are cleared
        for (int i = 0; i < powerUpBufforSize; i++) {
            if(powerup[i]!=null) {
                children.remove(powerup[i].getCircle());
                powerup[i] = null;
            }
            if(effects[i]!=null){
                effects[i].deactivate();
                effects[i]=null;
            }
        }
    }

    public Powerups(Ball[] balls, Padle padle) {
        this.balls = balls;
        this.padle = padle;
        this.powerup=new Powerup[powerUpBufforSize];
        this.effects=new PowerupEffect[powerUpBufforSize];
        this.powerupspots=new boolean[powerUpBufforSize];
        for (int i = 0; i < powerUpBufforSize; i++) {
            powerupspots[i]=true;
        }
    }

    public void add(double x,double y){
        for (int i = 0; i < powerUpBufforSize; i++) {
            if(powerupspots[i]){
                powerup[i]=new Powerup(x,y,this.padle);
                children.add(powerup[i].getCircle());
                powerupspots[i]=false;
                break;
            }
        }
    }

    public void fall(){
        for (int i = 0; i < powerUpBufforSize; i++) {
            if(effects[i]!=null){
                if(effects[i].makeAction()){
                    effects[i]=null;
                }
            }
            if(powerup[i]!=null){
                PowerupEffectKind pEffect = powerup[i].fall();
                if(pEffect==PowerupEffectKind.NONE){
                    continue;
                }
                switch (pEffect){
                    case BIGBALL:
                        effects[i]=new PowerupEffectBigBall(balls);
                        break;
                }
                children.remove(powerup[i].getCircle());
                powerup[i]=null;
            }
        }
    }

    public void addChildren(ObservableList<Node> children) {
        this.children=children;
    }
}
