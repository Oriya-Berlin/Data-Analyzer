package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../Views/MainView.fxml"));
        primaryStage.setTitle("Data Analyzer");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();


        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("../Views/TableSourceView.fxml"));
        Scene scene = new Scene(myLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Connection");
        stage.setScene(scene);
        stage.show();

//        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Views/TableSourceView.fxml"));
//        AnchorPane root2 = loader.load();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
