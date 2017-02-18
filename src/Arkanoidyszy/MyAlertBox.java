package Arkanoidyszy;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
//otwieranie alertbox okienka dzialajacy jak standardowe okienko alert
public class MyAlertBox {
    public static void show(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);//blokuje interakcje zwiazana z innymi oknami dopóki nie załatwimy tej czyli dokladnie tak jak alert dziala
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e->window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();//czeka dodatkowo na zamkniecie tego zanim powroci do poprzedniego
    }
}
