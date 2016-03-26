package rxcircle.wiki;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.Date;

import static javafx.application.Application.launch;

/**
 * Created by Madeleine on 2016-03-26.
 */
public class Main extends Application {


    private static final String SEARCH_URL = "https://sv.wikipedia.org/w/api.php?action=query&list=search&srsearch={query}&format=json&utf8=";
    private static final String ARTICLE_URL = "https://sv.wikipedia.org/w/api.php?action=query&titles={title}&prop=revisions&rvprop=content&format=json&utf8=";

    public static final ObservableList data =
            FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("List View Sample");

        final ListView listView = new ListView(data);
        listView.setPrefSize(200, 250);
        listView.setEditable(false);

        data.add("First hit");
        data.add("Second hit");

        Label searchLabel = new Label("Search:");
        TextField searchField = new TextField();

        Button searchButton = new Button();
        searchButton.setText("Say 'Hello World'");
        searchButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                data.add("Hej: " + new Date().getTime());
            }
        });

        WebView  browser = new WebView();
        WebEngine engine = browser.getEngine();
        String url = "http://www.wikipedia.com/";
        engine.load(url);

        BorderPane mainBorderPane = new BorderPane();
        BorderPane searchBorderPane = new BorderPane();
        mainBorderPane.setLeft(searchBorderPane);
        mainBorderPane.setCenter(browser);

        HBox hb = new HBox();
        hb.getChildren().addAll(searchLabel, searchField, searchButton);
        hb.setSpacing(10);

        searchBorderPane.setLeft(listView);
        searchBorderPane.setTop(hb);

        StackPane root = new StackPane();
        root.getChildren().add(mainBorderPane);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}