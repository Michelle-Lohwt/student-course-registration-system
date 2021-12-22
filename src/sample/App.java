package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
    try{
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("CAT201 Student Course Registration System");
        stage.setScene(scene);
        stage.show();
      }catch(Exception e){
        e.printStackTrace();
      }
    }
}
