package Arkanoidyszy;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class BrickGrid {
    private Brick[] bricks;
    private int[][] theGrid;
    private int score;
    private int appHeight,appWidth;
    private int bricksDestroyed;
    Label scoreLabel;
    ObservableList<Node> brickField;
    public boolean checkWin(){
        return bricksDestroyed == bricks.length;
    }
    public void restart(){
        score=0;
        scoreLabel.setText("0");
        for (int i = 0; i < appWidth; i++) {
            for (int j = 0; j <appHeight ; j++) {
                theGrid[i][j]=-1;//no brick on gamescreen here
            }
        }
        for (int i = 0; i < bricks.length; i++) {
            bricks[i].restartBrick();
            Rectangle rect = bricks[i].getRect();
            if(!brickField.contains(rect)){
                brickField.add(rect);//czy error jak nie ma?
            }
            addBrick(bricks[i],i);
        }
    }
    public void showBricks(ObservableList<Node> brickField, Label scoreLabel){
        this.brickField = brickField;
        for (int i = 0; i < bricks.length; i++) {
            brickField.add(bricks[i].getRect());
        }
        this.scoreLabel=scoreLabel;
    }
    public BrickGrid(int appWidth, int appHeight, Brick[] bricks){
        theGrid = new int[appWidth][appHeight];
        this.appWidth = appWidth;
        this.appHeight = appHeight;
        this.bricks=bricks;
        this.bricksDestroyed=0;
        for (int i = 0; i < appWidth; i++) {
            for (int j = 0; j <appHeight ; j++) {
                theGrid[i][j]=-1;//pole bez bricka
            }
        }
        for (int i = 0; i < bricks.length; i++) {
            this.addBrick(bricks[i],i);
        }
    }
    public void addBrick(Brick brick,int hisnum){//dla kazdego miejsca przydzielamy numer bloczka
        int brickX = (int)(brick.getRect().getX());
        int brickY = (int)(brick.getRect().getY());
        for (int i = brickX; i < brickX+Brick.BRICKSIZEWIDTH; i++) {
            for (int j = brickY; j < brickY+Brick.BRICKSIZEHEIGHT; j++) {
                theGrid[i][j]=hisnum;
            }
        }
    }
    public boolean checkBrick(int x,int y){
        if(theGrid[x][y]==-1){
            return false;
        }
        return true;
    }
    public void takeHit(int x,int y){
        int brickId = theGrid[x][y];
        int hitScore =bricks[brickId].takeHit();
        if(hitScore!=0){
            //brick ginie
            int brickX = (int)(bricks[brickId].getRect().getX());
            int brickY = (int)(bricks[brickId].getRect().getY());
            for (int i = brickX; i < brickX+Brick.BRICKSIZEWIDTH; i++) {
                for (int j = brickY; j < brickY+Brick.BRICKSIZEHEIGHT; j++) {
                    theGrid[i][j]=-1;
                }
            }
            score+=hitScore;
            scoreLabel.setText(String.valueOf(score));
            brickField.remove(bricks[brickId].getRect());
            bricksDestroyed+=1;
        }
    }
    public void setPowerups(Powerups powerups){
        for (Brick brick:bricks) {
            if(brick.getKind()==BrickKind.POWERUP){
                brick.setPowerups(powerups);
            }
        }
    }
}
