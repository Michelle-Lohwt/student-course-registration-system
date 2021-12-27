package sample;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class signUpController extends Controller {

  @FXML
  private ToggleGroup studentLecturer;

  @FXML
  private Text signUpMessage;

  public void Login(MouseEvent event) throws IOException {
    switchTo(event, "login.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "contactUs.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void signUp(MouseEvent event) {
    signUpMessage.setText("Sign Up Successful");
  }
}