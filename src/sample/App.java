package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    try {
      Parent root = FXMLLoader.load(getClass().getResource("welcome.fxml"));
      Scene scene = new Scene(root, 1200, 770);
      Image icon = new Image("sample/images/usm-ringlogo.png");
      stage.getIcons().add(icon);
      stage.setTitle("CAT201 Student Course Registration System");
      stage.setResizable(false);
      stage.setScene(scene);
      stage.show();
      stage.setOnCloseRequest(e -> Platform.exit());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
