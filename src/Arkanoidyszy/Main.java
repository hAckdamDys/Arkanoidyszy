package Arkanoidyszy;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
    public static void main(String[] args){
        launch(args);
    }
    private int menuHeight = 500,menuWidth=500;
    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Arkanoidyszy");
        //init
        ALevel_1 level1 = new ALevel_1(window);
        ALevel_2 level2 = new ALevel_2(window);
        //kolejne levele

        //układ menu:
        Label mainText = new Label("Arkanoidyszy");
        mainText.getStyleClass().add("menuMainText");
        Button newGameButton = new Button("New Game");
        newGameButton.setOnAction(e->{
            //jesli bedzie progress to option box wyskakuje czy resetowac save
            level1.show();
        });
        newGameButton.getStyleClass().add("menuButtons");
        Button levelSelectButton = new Button("Level Select");
        levelSelectButton.setOnAction(e->{
            LevelSelect.show();
        });
        levelSelectButton.getStyleClass().add("menuButtons");
        Button scoresButton = new Button("High Scores");
        scoresButton.getStyleClass().add("menuButtons");
        VBox menuLayout = new VBox(mainText,newGameButton,levelSelectButton,scoresButton);
        menuLayout.setAlignment(Pos.TOP_CENTER);
        menuLayout.setSpacing(20);//odstepy miedzy buttonami
        menuLayout.getStyleClass().addAll("menuBackground");
        Scene menu = new Scene(menuLayout);
        menu.getStylesheets().add("MainStyleSheet.css");
        //ustawienie menu jako okienka ktore teraz jest pokazane
        //init gameovera i level selecta:
        GameOver.initGameOverScreen(menu,window,menuHeight,menuWidth,menuHeight,menuWidth);
        LevelSelect.initLevelSelectScreen(menu,window,menuHeight,menuWidth,menuHeight,menuWidth,level1,level2);
        //pokazanie menu:
        window.setScene(menu);
        window.setMinHeight(menuHeight);
        window.setMinWidth(menuWidth);
        window.show();
    }
}
