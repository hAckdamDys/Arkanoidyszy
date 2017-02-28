package Arkanoidyszy;

import java.util.Stack;

/**
 * Created by Adm on 2017-02-28.
 */
public class PowerupEffectBigBall extends PowerupEffect{
    private Ball[] balls;
    private static Stack<Double> prevSizes=new Stack<>();
    public PowerupEffectBigBall(Ball[] balls){
        this.balls=balls;
        double prevSize =balls[0].getCircle().getRadius();
        prevSizes.push(prevSize);
        for (Ball ball:balls) {
            ball.setRadius(prevSize*1.5);
        }
    }
    @Override
    public void deactivate(){
        double prevSize = prevSizes.pop();
        for (Ball ball:balls) {
            ball.setRadius(prevSize);
        }
    }
}
