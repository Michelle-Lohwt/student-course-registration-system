package sample;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class welcomeController {
  private Stage stage;
  private Scene scene;
  private Parent root;

  @FXML
  private Hyperlink hyperlink;

  @FXML
  private Button welcome;

  @FXML
  public void openLink(ActionEvent event) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.usm.my/"));
  }

  public void switchToLogin(MouseEvent event) throws IOException {
    root = FXMLLoader.load(getClass().getResource("login.fxml"));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
