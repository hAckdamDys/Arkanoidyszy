package Arkanoidyszy;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ALevel {//this class needs refactoring...
    protected int appWidth=800, appHeight=600;
    protected double ballRadius=10,batHeight=60,batWidth=100;
    protected Game game;
    protected Parent gameScreen;
    protected BorderPane display;
    protected Stage window;
    protected Scene levelScene;
    protected BrickGrid grid;
    private ALevel nextLevel;
    public ALevel(Stage window,ALevel nextLevel){
        initALevel(window,nextLevel);
    }
    public void show(){
        initALevel(window,nextLevel);//making lvl need to load again
        window.setScene(levelScene);
        window.setMinWidth(appWidth + 300);
        window.setMinHeight(appHeight + 100);
        if(!window.isMaximized()) {
            window.setWidth(appWidth + 300);
            window.setHeight(appHeight + 100);
        }
        game.startGame();
    }
    protected void initSides(){

        //layout całej gry
        display = new BorderPane();

        //góra:
        //ustawienie dlugosci
        Label padlePreLenLabel = new Label("Paddle Length:");
        Label padleLenLabel = new Label("100");
        TextField padleLenField = new TextField();
        padleLenField.setPromptText("paddleLength");
        padleLenField.setOnAction(e->{
            String tmpStr = padleLenField.getText();
            if(isGoodDouble(tmpStr)){
                double tmpDouble = Double.parseDouble(tmpStr);
                game.setRectWidth(tmpDouble);
                padleLenLabel.setText(tmpStr);
            }
            gameScreen.requestFocus();
        });

        VBox padleStuffPadle = new VBox(new HBox(padlePreLenLabel,padleLenLabel),padleLenField);
        padleStuffPadle.setPadding(new Insets(10,10,10,10));

        //ustawienie promienia:
        Label padlePreBallLabel = new Label("Ball Radius: ");
        Label padleBallLabel = new Label("10");
        TextField ballRadField = new TextField();
        ballRadField.setPromptText("ballRad");
        ballRadField.setOnAction(e->{
            String tmpStr = ballRadField.getText();
            if(isGoodDouble(tmpStr)){
                double tmpDouble = Double.parseDouble(tmpStr);
                game.setBallRadius(tmpDouble);
                padleBallLabel.setText(tmpStr);
            }
            gameScreen.requestFocus();
        });

        VBox padleStuffBall = new VBox(new HBox(padlePreBallLabel,padleBallLabel),ballRadField);
        padleStuffBall.setPadding(new Insets(10,10,10,10));

        //ustawienie predkosci:
        Label ballPreSpeedLabel = new Label("Ball Speed: ");
        Label ballSpeedLabel = new Label("5");
        TextField ballSpeedField = new TextField();
        ballSpeedField.setPromptText("ballSpeed");
        ballSpeedField.setOnAction(e->{
            String tmpStr = ballSpeedField.getText();
            if(isGoodDouble(tmpStr)){
                double tmpDouble = Double.parseDouble(tmpStr);
                game.setBallSpeed(tmpDouble);
                ballSpeedLabel.setText(tmpStr);
            }
            gameScreen.requestFocus();
        });

        VBox padleSpeed = new VBox(new HBox(ballPreSpeedLabel,ballSpeedLabel),ballSpeedField);
        padleSpeed.setPadding(new Insets(10,10,10,10));

        //pokazanie wspolrzednych kulki
        Label ballCoordLabelX = new Label("Ball's X:");
        Label ballCoordLabelY = new Label("Ball's Y:");
        Label ballXLabel = new Label("400");
        Label ballYLabel = new Label("300");
        Label scoreLabelName = new Label("Score: ");
        Label scoreLabel = new Label("0");
        Label lifeLabelName = new Label("Lifes: ");
        Label lifeLabel = new Label("3");
        game.setCoordLabels(ballXLabel,ballYLabel,lifeLabel,scoreLabel);
        HBox ballCoordXY = new HBox(new VBox(new HBox(ballCoordLabelX,ballXLabel),new HBox(ballCoordLabelY,ballYLabel)),
                new VBox(new HBox(scoreLabelName,scoreLabel),new HBox(lifeLabelName,lifeLabel)));
        ballCoordXY.setPadding(new Insets(10,10,10,10));
        //przycisk nowej gry:
        Button button1 = new Button("Leave");
        button1.setOnAction(e-> game.loseGame());
        button1.setPadding(new Insets(10,10,10,10));

        HBox hboxTop = new HBox(button1,padleStuffBall,padleStuffPadle,padleSpeed,ballCoordXY);

        hboxTop.getStyleClass().add("sideStyle");
        display.setTop(hboxTop);
        //prawa:
        Pane rightSide = new Pane();
        rightSide.getStyleClass().add("sideStyle");
        Label levelLabel = new Label("LEVEL SELECT");
        rightSide.getChildren().addAll(levelLabel);
        display.setRight(rightSide);
        gameScreen = game.screen();
    }
    private boolean isGoodDouble (String value){
        try{
            double value1 = Double.parseDouble(value);
            return !(value1 < 1);
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    protected void makeGrid(){}

    protected void initALevel(Stage window, ALevel nextLevel){
        this.window = window;
        this.nextLevel=nextLevel;
        makeGrid();
        this.game = new Game(appWidth,appHeight,ballRadius,batHeight,batWidth,grid,nextLevel);
        initSides();
        //srodek czyli gra:
        display.setCenter(gameScreen);
        //wstawienie naszego ekranu do sceny
        levelScene = new Scene(display);
        levelScene.getStylesheets().add("MainStyleSheet.css");
        //obsluga klawiszy:
        levelScene.setOnKeyPressed(e->{
            switch (e.getCode()){
                case A:
                case LEFT:
                    game.setAction(UserAction.LEFT);
                    break;
                case D:
                case RIGHT:
                    game.setAction(UserAction.RIGHT);
                    break;
                case ENTER:
                case P:
                    if(game.getAction2()!=UserAction.PAUSED) {
                        game.setAction2(UserAction.PAUSE);
                    }
                    break;
            }
        });
        levelScene.setOnKeyReleased(e->{
            switch (e.getCode()){
                case A:
                case LEFT:
                    if(game.getAction()==UserAction.LEFT) {
                        game.setAction(UserAction.NONE);
                    }
                    break;
                case D:
                case RIGHT:
                    if(game.getAction()==UserAction.RIGHT) {
                        game.setAction(UserAction.NONE);
                    }
                    break;
                case ENTER:
                case PAUSE:
                    game.setAction2(UserAction.NONE);
                    break;
            }
        });
    }
}
