package sample;

import java.io.IOException;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class loginController {
  private Stage stage;
  private Scene scene;
  private Parent root;

  public void switchToSignUp(MouseEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToContactUs(MouseEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("contactUs.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToStuDashboard(MouseEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("stuDashboard.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
