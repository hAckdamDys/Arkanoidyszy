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
 * Ta klasa zawiera w sobie elementy grywalne danego poziomu
 * posiada padle, pilke, uklad poziomu, score
 * kontroluje ilosc zyc jesli sie skoncza to wywala gameover i zwraca gdzies score
 * decyduje o wykonywanych akcjach zaleznie od action wyslanego przez level
 *
 */
public class Game {

    private KeyFrame frame;

    private final double appWidth;
    private final double appHeight;

    private UserAction action;

    private Timeline timeline;
    private boolean isPlaying;

    private BrickGrid grid;

    private Ball ballA;
    private Ball ballB;
    private Padle padle;

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

    //private IntegerProperty intBallXProperty,intBallYProperty; to byly zmienne ktore trzmaly wspolrzedne pilki

    public Game(int appWidth, int appHeight, double ballRadius, double RectHeight, double RectWidth, BrickGrid grid) {
        //inicjalizacja danych
        this.grid = grid;
        this.appWidth = appWidth;
        this.appHeight = appHeight;
        //padle init:
        this.padle = new Padle(RectWidth,RectHeight,appWidth,appHeight,5);
        //ball init:
        this.ballA = new Ball(ballRadius,this.padle,appWidth,appHeight,grid);
        this.ballB = new Ball(ballRadius,this.padle,appWidth,appHeight,grid);
        //property na wspolrzedne pilki
        //intBallXProperty = new SimpleIntegerProperty();
        //intBallYProperty = new SimpleIntegerProperty();
        //odpalenie listenerow na labele w mainie
        //zeby zoptymalizowac mozna zrobic double properites ktore sa zbindowane do tych od circla
        //intBallXProperty.bind(ballA.getCircle().translateXProperty());
        ballA.getCircle().translateXProperty().addListener((v,oldV,newV) -> ballXLabel.setText(String.valueOf(newV.intValue())));
        //intBallYProperty.bind(ballA.getCircle().translateYProperty());
        ballA.getCircle().translateYProperty().addListener((v,oldV,newV) -> ballYLabel.setText(String.valueOf(newV.intValue())));
        this.isPlaying=false;
        timeline=new Timeline();
        action=UserAction.NONE;
    }
    //tworzenie ekranu gry
    public Parent screen(){
        Pane layout = new Pane();
        //keyframe dziala tak ze co dany odstep czasu czyli np u mnie 0.016 sekund robi
        frame = new KeyFrame(Duration.seconds(0.016),e -> {
            //jesli isPlaying false to nic nie robimy
            if(!isPlaying){
                return;
            }
            //akcja to to co wykonuje w tym momencie gracz czyli np poruszenie w lewo
            padle.move(action);
            //poruszenie pilką i co zrobic gdy spadnie, dostaje tez akcje
            ballB.move(action);
            if(ballA.move(action)){
                this.loseLife();
            }
            //update info o pilce
            /* albo tutaj mozna dac wypisywanie za kazdym razem albo lepiej podlaczyc sie do properties przy inicjalizacji
            ballXLabel.setText(String.valueOf(ballA.getCircle().getTranslateX()));
            */

        });

        //timeline:
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        layout.setMinHeight(appHeight);
        layout.setMinWidth(appWidth);
        layout.setMaxHeight(appHeight);
        layout.setMaxWidth(appWidth);
        //dodajemy do layouta najpierw bloczki przez grid
        grid.showBricks(layout.getChildren(),scoreLabel);
        layout.getChildren().addAll(ballB.getCircle(),ballA.getCircle(),padle.getRect());
        layout.setOnMouseClicked(e->layout.requestFocus());//sprawdzamy
        layout.getStyleClass().add("gameBackground");
        return layout;
    }

//    public void restartGame(){
//        stopGame();
//        startGame();
//    }

//    public void stopGame(){
//        isPlaying=false;
//        timeline.stop();
//    }

    public void loseGame(){
        isPlaying=false;
        timeline.stop();
        GameOver.show(scoreLabel.getText());//mozna zmienic zeby wyswietlalo score
    }

    private void loseLife(){
        if(lifes<=0){
            this.loseGame();
            return;
        }
        --lifes;
        lifeLabel.setText(String.valueOf(lifes));
        ballA.start(appWidth/2,appHeight-75);
        ballB.start(appWidth/2+20,appHeight-75);
        padle.start();
    }

    //poczatek gry swiezy
    public void startGame(){
        //ustawienie ball i Rect na default
        lifes=3;
        ballA.start(appWidth/2,appHeight-75);
        ballB.start(appWidth/2+20,appHeight-75);
        padle.start();
        grid.restart();
        timeline.play();
        isPlaying=true;
    }

    public void setBallRadius(double radius){
        ballA.setRadius(radius);
        ballB.setRadius(radius);
    }
    public void setRectWidth(double width){
        padle.setRectWidth(width);
    }
    public void setPadleSpeed(double padleSpeed) {
        padle.setSpeed(padleSpeed);
    }

}
