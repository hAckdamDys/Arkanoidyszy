package Arkanoidyszy;

import javafx.animation.KeyFrame;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
//otwieranie alertbox okienka dzialajacy jak standardowe okienko alert
public class GameOver {
    private static Stage window;
    private static Scene gameOverScene;
    private static int gameOverHeight,gameOverWidth;
    private static boolean initDone = false;
    private static Label scoreLabel;
    public static void initGameOverScreen(Scene menu, Stage window,int menuHeight,int menuWidth, int gameOverHeight, int gameOverWidth){
        GameOver.window=window;
        GameOver.gameOverWidth=gameOverWidth;
        GameOver.gameOverHeight=gameOverHeight;
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverText");
        Label scoreLabelName = new Label("Score:");
        scoreLabelName.getStyleClass().add("gameOverText");
        GameOver.scoreLabel = new Label("0");
        scoreLabel.getStyleClass().add("gameOverText");
        Button closeButton = new Button("OK :(");
        closeButton.setOnAction(e->{
            window.setScene(menu);
            window.setMinHeight(menuHeight);
            window.setMinWidth(menuWidth);
            if(!window.isMaximized()) {
                window.setHeight(menuHeight);
                window.setWidth(menuWidth);
            }
        });
        closeButton.getStyleClass().add("gameOverButtons");
        VBox layout = new VBox(gameOverLabel,scoreLabelName,scoreLabel,closeButton);
        layout.getStyleClass().add("gameOverBackground");
        layout.setAlignment(Pos.TOP_CENTER);
        gameOverScene = new Scene(layout);
        gameOverScene.getStylesheets().add("MainStyleSheet.css");
        initDone = true;
    }
    public static void show(String score){
        if(!initDone){
            MyAlertBox.show("Error","GameOver is not initialized error, shutting down");
            return;
        }
        scoreLabel.setText(score);
        window.setScene(gameOverScene);
        window.setMinWidth(gameOverWidth);
        window.setMinHeight(gameOverHeight);
        if(!window.isMaximized()) {
            window.setHeight(gameOverHeight);
            window.setWidth(gameOverWidth);
        }
    }
}
