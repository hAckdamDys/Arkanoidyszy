package Arkanoidyszy;

/**
 * Created by Adm on 2017-02-28.
 */
public class PowerupEffect {
    private int tickClock;
    public PowerupEffect(){
        tickClock=(int) (10/Game.tickTime)+1;//10 seconds
    }
    public void deactivate(){}
    public boolean makeAction(){//returns true if this object needs to be deleted
        tickClock-=1;
        if(tickClock==0){
            this.deactivate();
            return true;
        }
        return false;
    }
}
