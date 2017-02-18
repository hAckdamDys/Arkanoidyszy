package Arkanoidyszy;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class LevelSelect {
    private static Stage window;
    private static int levelSelectWidth;
    private static int levelSelectHeight;
    private static Scene levelSelectScene;
    private static boolean initDone=false;
    public static void initLevelSelectScreen(Scene menu, Stage window, int menuHeight, int menuWidth, int levelSelectHeight, int levelSelectWidth, ALevel_1 level_1,ALevel_2 level_2){
        LevelSelect.window=window;
        LevelSelect.levelSelectWidth=levelSelectWidth;
        LevelSelect.levelSelectHeight=levelSelectHeight;
        Label levelSelectLabel = new Label("Level Select");
        levelSelectLabel.getStyleClass().add("levelSelectText");
        //close button:
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e->{
            window.setScene(menu);
            window.setMinHeight(menuHeight);
            window.setMinWidth(menuWidth);
            if(!window.isMaximized()) {
                window.setHeight(menuHeight);
                window.setWidth(menuWidth);
            }
        });
        closeButton.getStyleClass().add("levelSelectButtons");
        //przyciski levele:
        Button l1Button = new Button("Level 1");
        l1Button.setOnAction(e->{
            //jesli bedzie progress to option box wyskakuje czy resetowac save
            level_1.show();
        });
        l1Button.getStyleClass().add("levelSelectButtons");
        Button l2Button = new Button("Level 2");
        l2Button.setOnAction(e->{
            //jesli bedzie progress to option box wyskakuje czy resetowac save
            level_2.show();
        });
        l2Button.getStyleClass().add("levelSelectButtons");
        //dodanie layoutu
        VBox layout = new VBox(levelSelectLabel,l1Button,l2Button,closeButton);
        layout.getStyleClass().add("levelSelectBackground");
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setSpacing(20);//odstepy miedzy buttonami
        levelSelectScene = new Scene(layout);
        levelSelectScene.getStylesheets().add("MainStyleSheet.css");
        initDone = true;
    }
    public static void show(){
        if(!initDone){
            MyAlertBox.show("Error","LevelSelect is not initialized error, shutting down");
            return;
        }
        window.setScene(levelSelectScene);
        window.setMinWidth(levelSelectWidth);
        window.setMinHeight(levelSelectHeight);
        if(!window.isMaximized()) {
            window.setHeight(levelSelectHeight);
            window.setWidth(levelSelectWidth);
        }
    }

}
