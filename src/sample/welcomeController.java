package sample;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;

public class welcomeController extends Controller {

  @FXML
  private Hyperlink hyperlink;

  @FXML
  public void openLink(ActionEvent event) throws URISyntaxException, IOException {
    Desktop.getDesktop().browse(new URI("https://www.usm.my/"));
  }

  public void Login(MouseEvent event) throws IOException {
    switchTo(event, "login.fxml");
  }
}
