package sample;

import java.io.IOException;
import java.net.URISyntaxException;

import com.jfoenix.controls.JFXCheckBox;

import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;

public class loginController extends Controller {
  @FXML
  private ToggleGroup studentLecturer;
  @FXML
  private RadioButton rbLecturer, rbStudent;

  @FXML
  private TextField matricNo, textPassword;

  @FXML
  private PasswordField password;

  @FXML
  private JFXCheckBox showPassword;

  public void SignUp(MouseEvent event) throws IOException {
    switchTo(event, "signUp.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "contactUs.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void SignIn(MouseEvent event) throws IOException {
    if (rbStudent.isSelected()) {
      switchTo(event, "stuDash.fxml");
    } else if (rbLecturer.isSelected()) {
      switchTo(event, "lecDash.fxml");
    }
  }

  public void TriggerPasswordCheckBox() {
    if (showPassword.isSelected()) {
      textPassword.setText(password.getText());

      textPassword.setDisable(false);
      textPassword.setVisible(true);

      password.setDisable(true);
      password.setVisible(false);
    } else {
      password.setText(textPassword.getText());

      textPassword.setDisable(true);
      textPassword.setVisible(false);

      password.setDisable(false);
      password.setVisible(true);
    }
  }
}