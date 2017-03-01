package Arkanoidyszy;

/**
 * Created by Adm on 2017-03-01.
 */
public class PowerupEffectMultiball extends PowerupEffect {//adding 2 balls or less if already full
    public PowerupEffectMultiball(Ball[] balls) {
        int addCount=2;
        Ball aliveBall = null;
        //searching for first alive ball and then assinging new close to this
        for (int i = 0; i < balls.length; i++) {
            if(balls[i].isAlive()) {
                aliveBall = balls[i];
            }
        }
        for (int i = 0; i < balls.length && addCount>0 ; i++) {
            if(!balls[i].isAlive()){
                balls[i].start(aliveBall.getCircle().getCenterX(), aliveBall.getCircle().getCenterY());
                balls[i].setSpeed(aliveBall.getDx()+addCount,aliveBall.getDy());
                addCount-=1;
            }
        }
    }
}
