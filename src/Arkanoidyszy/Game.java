package Arkanoidyszy;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * This class have elements of a level
 * it controls how things flow with keyframeso that everything is synced
 */
public class Game {

    public static final double tickTime=0.016;
    private static final int ballMax=9;

    private final double appWidth;
    private final double appHeight;
    private final ALevel nextLevel;

    private UserAction action;
    private UserAction action2;
    private Timeline timeline;
    private boolean isPlaying;

    private BrickGrid grid;

    private Ball[] balls;
    private Padle padle;
    private Powerups powerups;
    private int lifes;

    private Label ballXLabel,ballYLabel,lifeLabel,scoreLabel;
    public void setCoordLabels(Label ballXLabel, Label ballYLabel, Label lifeLabel,Label scoreLabel){
        this.ballXLabel=ballXLabel;
        this.ballYLabel=ballYLabel;
        this.scoreLabel=scoreLabel;
        this.lifeLabel=lifeLabel;
    }

    public void setAction(UserAction a){
        action=a;
    }
    public UserAction getAction() {
        return action;
    }
    public void setAction2(UserAction a){
        action2=a;
    }
    public UserAction getAction2() {
        return action2;
    }

    public Game(int appWidth, int appHeight, double ballRadius, double RectHeight, double RectWidth, BrickGrid grid, ALevel nextLevel) {
        //inicjalizacja danych
        this.nextLevel=nextLevel;
        this.grid = grid;
        this.appWidth = appWidth;
        this.appHeight = appHeight;
        //padle init:
        this.padle = new Padle(RectWidth,RectHeight,appWidth,appHeight,5);
        //ball init:
        this.balls = new Ball[ballMax];
        for (int i = 0; i < ballMax; i++) {
            balls[i] = new Ball(ballRadius,this.padle,appWidth,appHeight,grid);
        }
        balls[0].getCircle().centerXProperty().addListener((v,oldV,newV) -> ballXLabel.setText(String.valueOf(newV.intValue())));
        balls[0].getCircle().centerYProperty().addListener((v,oldV,newV) -> ballYLabel.setText(String.valueOf(newV.intValue())));
        //powerups init:
        this.powerups=new Powerups(balls,padle);
        this.grid.setPowerups(powerups);
        //others init:
        this.isPlaying=false;
        timeline=new Timeline();
        action=UserAction.NONE;
    }
    //making game screen
    public Parent screen(){
        Pane layout = new Pane();
        //keyframe is making game do sth every tickTime
        KeyFrame frame = new KeyFrame(Duration.seconds(tickTime), e -> {
            //handling pause
            if( action2==UserAction.PAUSE){
                isPlaying=!isPlaying;
                action2=UserAction.PAUSED;
            }
            if (!isPlaying) {
                return;
            }
            //when player win
            if(grid.checkWin()){
                winGame();
            }
            //action what players do gets padle
            padle.move(action);
            //operating balls
            boolean allLost = true;
            for (Ball ball : balls) {
                if (!ball.move(action)) {
                    allLost = false;
                }
            }
            if (allLost) {
                this.loseLife();
            }
            //operating powerups
            powerups.fall();
        });

        //timeline:
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        //setting layout size
        layout.setMinHeight(appHeight);
        layout.setMinWidth(appWidth);
        layout.setMaxHeight(appHeight);
        layout.setMaxWidth(appWidth);
        //modificating layout bricks and other
        grid.showBricks(layout.getChildren(),scoreLabel);
        layout.getChildren().addAll(padle.getRect());
        for (Ball ball:balls) {
            layout.getChildren().add(ball.getCircle());
        }
        powerups.addChildren(layout.getChildren());
        layout.setOnMouseClicked(e->layout.requestFocus());//focus on window
        layout.getStyleClass().add("gameBackground");
        return layout;
    }

    public void winGame(){
        isPlaying=false;
        timeline.stop();
        //update score to high score
        if(nextLevel==null){
            GameOver.show(scoreLabel.getText());
            return;
        }
        nextLevel.show();
    }

    public void loseGame(){
        isPlaying=false;
        timeline.stop();
        GameOver.show(scoreLabel.getText());//screen showing score
    }

    private void loseLife(){
        isPlaying=false;
        powerups.clear();
        if(lifes<=0){
            this.loseGame();
            return;
        }
        --lifes;
        lifeLabel.setText(String.valueOf(lifes));
        balls[0].start(appWidth/2,appHeight-75);
//        ball.start(appWidth/2+20,appHeight-75);
        padle.start();
    }

    //fresh game start
    public void startGame(){
        lifes=3;
        balls[0].start(appWidth/2,appHeight-75);
        padle.start();
        grid.restart();
        timeline.play();
        isPlaying=false;
    }

    public void setBallRadius(double radius){
        for (Ball ball:balls){
            ball.setRadius(radius);
        }
    }
    public void setRectWidth(double width){
        padle.setRectWidth(width);
    }
    public void setBallSpeed(double ballSpeed) {
        for (Ball ball:balls) {
            ball.setSpeed(ballSpeed,ballSpeed);
        }
    }

}
