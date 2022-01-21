package sample;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.scene.input.MouseEvent;

public class contactUsController extends Controller {

  public void Login(MouseEvent event) throws IOException {
    switchTo(event, "login.fxml");
  }

  public void SignUp(MouseEvent event) throws IOException {
    switchTo(event, "signUp.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }
}