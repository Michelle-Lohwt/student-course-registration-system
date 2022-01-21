package sample;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseEvent;

public class logoutController extends Controller {

  @FXML
  private Hyperlink hyperlink;

  @FXML
  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void Welcome(MouseEvent event) throws IOException {
    switchTo(event, "welcome.fxml");
  }
}