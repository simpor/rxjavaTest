package rxcircle.wiki;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
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
import rx.Subscriber;

public class RxJavaMain extends Application {

    public static final ObservableList<String> data = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("List View Sample");
        final ListView<String> listView = new ListView<>(data);
        listView.setPrefSize(200, 250);
        listView.setEditable(false);

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

        Observable<String> stringObservable = Observable.create((Subscriber<? super String> subscriber) ->
                searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!subscriber.isUnsubscribed())
                        subscriber.onNext(searchField.getText());
                })
        ).sample(1, TimeUnit.SECONDS);

        Observable<String> titleObservable = Observable.create((Subscriber<? super String> subscriber) ->
                listView.setOnMouseClicked(e -> {
                    String selectedItem = listView.getSelectionModel().getSelectedItem();
                    if (!subscriber.isUnsubscribed())
                        subscriber.onNext(selectedItem);
                })
        ).filter(t -> t != null)
                .sample(1, TimeUnit.SECONDS);

        stringObservable.subscribe(text ->
                Platform.runLater(() -> {
                    List<String> result = Wikipedia.searchTitle(text);
                    data.clear();
                    data.addAll(result);
                })
        );

        titleObservable.subscribe(title ->
                Platform.runLater(() -> {
                    try {
                        String url = Wikipedia.searchArticle(title);
                        engine.load(url);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                })
        );

    }
}