package Arkanoidyszy;

import java.util.Stack;

/**
 * Created by Adm on 2017-02-28.
 */
public class PowerupEffectBigBall extends PowerupEffect{
    private Ball[] balls;
    private boolean isDeac;
    private static Stack<Double> prevSizes=new Stack<>();
    public PowerupEffectBigBall(Ball[] balls){
        super();
        this.balls=balls;
        this.isDeac=false;
        double prevSize =balls[0].getCircle().getRadius();
        System.out.println("pushing on stack:"+prevSize);
        prevSizes.push(prevSize);
        for (Ball ball:balls) {
            ball.setRadius(prevSize*1.5);
        }
    }
    @Override
    public void deactivate(){
        if(isDeac){
            return;
        }
        double prevSize = prevSizes.pop();
        System.out.println("poping from stack:"+prevSize);
        for (Ball ball:balls) {
            ball.setRadius(prevSize);
        }
        isDeac=true;
    }
}
