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

public class ALevel {
    protected int appWidth=800, appHeight=600;
    protected double ballRadius=10,batHeight=60,batWidth=100;
    protected Game game;
    protected Parent gameScreen;
    protected BorderPane display;
    protected Stage window;
    protected Scene levelScene;
    protected BrickGrid grid;
    public void show(){
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
        Label padlePreSpeedLabel = new Label("Padle Speed: ");
        Label padleSpeedLabel = new Label("5");
        TextField padleSpeedField = new TextField();
        padleSpeedField.setPromptText("ballRad");
        padleSpeedField.setOnAction(e->{
            String tmpStr = padleSpeedField.getText();
            if(isGoodDouble(tmpStr)){
                double tmpDouble = Double.parseDouble(tmpStr);
                game.setPadleSpeed(tmpDouble);
                padleSpeedLabel.setText(tmpStr);
            }
            gameScreen.requestFocus();
        });

        VBox padleSpeed = new VBox(new HBox(padlePreSpeedLabel,padleSpeedLabel),padleSpeedField);
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
        button1.setOnAction(e->{
            game.loseGame();
        });
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
            if(value1<1){
                return false;
            }
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    protected void makeGrid(){};
    protected void initALevel(Stage window){
        this.window = window;
        makeGrid();
        this.game = new Game(appWidth,appHeight,ballRadius,batHeight,batWidth,grid);
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
                    game.setAction(UserAction.LEFT);
                    break;
                case D:
                    game.setAction(UserAction.RIGHT);
                    break;
                case ENTER:
                    game.setAction(UserAction.ACCEPT);
                    break;
                case SPACE:
                    game.setAction(UserAction.ACCEPT);
                    break;
                case P:
                    game.setAction(UserAction.PAUSE);
                    break;
            }
        });
        levelScene.setOnKeyReleased(e->{
            switch (e.getCode()){
                case A:
                    if(game.getAction()==UserAction.LEFT) {
                        game.setAction(UserAction.NONE);
                    }
                    break;
                case D:
                    if(game.getAction()==UserAction.RIGHT) {
                        game.setAction(UserAction.NONE);
                    }
                    break;
                case ENTER:
                    game.setAction(UserAction.NONE);
                    break;
                case SPACE:
                    game.setAction(UserAction.NONE);
                    break;
                case P:
                    game.setAction(UserAction.NONE);
                    break;
            }
        });
    }
}