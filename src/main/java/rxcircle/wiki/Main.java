package rxcircle.wiki;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import rx.Observable;
import rx.observables.JavaFxObservable;
import rx.schedulers.JavaFxScheduler;

/**
 * Created by Madeleine on 2016-03-26.
 */
public class Main extends Application {

    public static final ObservableList data = FXCollections.observableArrayList();

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

        final WebView browser = new WebView();
        final WebEngine engine = browser.getEngine();

        BorderPane mainBorderPane = new BorderPane();
        BorderPane searchBorderPane = new BorderPane();
        mainBorderPane.setLeft(searchBorderPane);
        mainBorderPane.setCenter(browser);

        HBox hb = new HBox();
        hb.getChildren().addAll(searchLabel, searchField);
        hb.setSpacing(10);

        searchBorderPane.setLeft(listView);
        searchBorderPane.setTop(hb);

        StackPane root = new StackPane();
        root.getChildren().add(mainBorderPane);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

        Observable<String> stringObservable = JavaFxObservable.fromObservableValue(searchField.textProperty()).sample(1, TimeUnit.SECONDS);
        Observable<String> titleObservable = JavaFxObservable.fromObservableValue(listView.getSelectionModel().selectedItemProperty()).map(t -> t.toString()).sample(1, TimeUnit
                .SECONDS);

        stringObservable
                .observeOn(JavaFxScheduler.getInstance())
                .subscribe(text -> {
                    List<String> result = Wikipedia.searchTitle(text);
                    data.clear();
                    data.addAll(result);
                });

        titleObservable
                .observeOn(JavaFxScheduler.getInstance())
                .subscribe(title -> {
                    try {
                        String url = Wikipedia.searchArticle(title);
                        engine.load(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

    }
}