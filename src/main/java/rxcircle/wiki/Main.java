package rxcircle.wiki;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

/**
 * Created by Madeleine on 2016-03-26.
 */
public class Main extends Application {


    private static final String SEARCH_URL = "https://sv.wikipedia.org/w/api.php?action=query&list=search&srsearch={query}&format=json&utf8=";
    private static final String ARTICLE_URL = "https://sv.wikipedia.org/w/api.php?action=query&titles={title}&prop=revisions&rvprop=content&format=json&utf8=";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}