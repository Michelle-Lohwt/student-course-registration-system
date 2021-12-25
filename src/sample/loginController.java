package sample;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

public class loginController extends Controller {

  @FXML
  private ToggleGroup studentLecturer;

  public void SignUp(MouseEvent event) throws IOException {
    switchTo(event, "signUp.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "contactUs.fxml");
  }

  public void StuDashboard(MouseEvent event) throws IOException {
    switchTo(event, "stuDashboard.fxml");
  }

  public void openBrowser(MouseEvent event) throws URISyntaxException, IOException {
    openLink(event);
  }
}
