package Arkanoidyszy;

import java.util.Stack;

/**
 * Created by Adm on 2017-03-01.
 */
public class PowerupEffectLongPadle extends PowerupEffect {
    private Padle padle;
    private boolean isDeac=false;
    private static Stack<Double> prevSizes=new Stack<>();
    public PowerupEffectLongPadle(Padle padle) {
        super();
        this.padle=padle;
        double width=padle.getWidth();
        prevSizes.push(width);
        padle.setRectWidth(width*1.4);
    }
    @Override
    public void deactivate(){
        if(isDeac){
            return;
        }
        padle.setRectWidth(prevSizes.pop());
        isDeac=true;
    }
}
