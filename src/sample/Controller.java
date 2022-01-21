package sample;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

abstract class Controller {
  private Stage stage;
  private Scene scene;
  private Parent root;

  protected void switchTo(MouseEvent event, String fileName) throws IOException {
    root = FXMLLoader.load(getClass().getResource(fileName));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  protected void openLink() throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.usm.my/"));
  }
}