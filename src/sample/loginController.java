package sample;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class loginController extends Controller implements Initializable {
  @FXML
  private ToggleGroup studentLecturer;
  @FXML
  private RadioButton rbLecturer, rbStudent;

  @FXML
  private TextField matricNo, textPassword;

  @FXML
  private PasswordField password;

  @FXML
  private Text loginMessage;

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
    if (matricNo.getText().isBlank() && password.getText().isBlank()) {
      loginMessage.setText("Please enter matrics number and password!");
    } else if (matricNo.getText().isBlank()) {
      loginMessage.setText("Please enter matrics number!");
    } else if (password.getText().isBlank()) {
      loginMessage.setText("Please enter password!");
    } else {
      try {
        File fileObj = new File(matricNo.getText() + ".txt");
        Scanner sc = new Scanner(fileObj);
        String correctStudentID = sc.nextLine();
        String correctPassword = sc.nextLine();

        if (!matricNo.getText().equals(correctStudentID)) {
          loginMessage.setText("Matrics number is incorrect!");
        } else if (!password.getText().equals(correctPassword)) {
          loginMessage.setText("Password is incorrect!");
        } else if (matricNo.getText().equals(correctStudentID) && password.getText().equals(correctPassword)) {
          if (rbStudent.isSelected()) {
            switchTo(event, "stuDash.fxml");
          } else if (rbLecturer.isSelected()) {
            switchTo(event, "lecDash.fxml");
          } else {
            loginMessage.setText("Please choose Student or Lecturer");
          }
        }

        while (sc.hasNextLine()) {
          System.out.println(sc.nextInt());

        }
        sc.close();
      } catch (IOException e) {
        loginMessage.setText("This matrics number has not registered yet!");
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
  }

  public void TriggerPasswordCheckBox() {
    if (showPassword.isSelected()) {

      textPassword.setDisable(false);
      textPassword.setVisible(true);

      password.setDisable(true);
      password.setVisible(false);
    } else {

      textPassword.setDisable(true);
      textPassword.setVisible(false);

      password.setDisable(false);
      password.setVisible(true);

    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    password.textProperty().bindBidirectional(textPassword.textProperty());
  }

}
