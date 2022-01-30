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
  private TextField id, textPassword;

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
    try {
      Integer.parseInt(id.getText());
      if (id.getText().isBlank() && password.getText().isBlank()) {
        loginMessage.setText("Please enter ID and password!");
      } else if (id.getText().isBlank()) {
        loginMessage.setText("Please enter ID!");
      } else if (password.getText().isBlank()) {
        loginMessage.setText("Please enter password!");
      } else if (id.getLength() != 6) {
        loginMessage.setText("ID must be 6 characters");
      } else {
        if(rbStudent.isSelected()){
          try {
            File fileObj = new File("data/Student Profile/" + id.getText() + ".txt");
            Scanner sc = new Scanner(fileObj);
            String correctStudentID = sc.nextLine();
            String correctPassword = sc.nextLine();
    
            if (!id.getText().equals(correctStudentID)) {
              loginMessage.setText("ID number is incorrect!");
            } else if (!password.getText().equals(correctPassword)) {
              loginMessage.setText("Password is incorrect!");
            } else if (id.getText().equals(correctStudentID) && password.getText().equals(correctPassword)) {
              courseRegController.inputID(id.getText());
              downloadController.inputID(id.getText());
              stuDashController.getID(id.getText());
              switchTo(event, "stuDash.fxml");
              sc.close();
            } 
          } catch (IOException e) {
            loginMessage.setText("This ID has not registered yet!");
            e.printStackTrace();
            
          }
        } else if (rbLecturer.isSelected()){
          try {
            File fileObj = new File("data/Lecturer Profile/" + id.getText() + ".txt");
            Scanner sc = new Scanner(fileObj);
            String correctLecturerID = sc.nextLine();
            String correctPassword = sc.nextLine();
    
            if (!id.getText().equals(correctLecturerID)) {
              loginMessage.setText("ID number is incorrect!");
            } else if (!password.getText().equals(correctPassword)) {
              loginMessage.setText("Password is incorrect!");
            } else if (id.getText().equals(correctLecturerID) && password.getText().equals(correctPassword)) {
              stuListController.inputID(id.getText());
              lecDashController.getID(id.getText());
              downloadController.inputLectID(id.getText());
              switchTo(event, "lecDash.fxml");
              sc.close();
            }
          } catch (IOException e) {
            loginMessage.setText("This ID has not registered yet!");
            e.printStackTrace();
          }
        } else {
          loginMessage.setText("Please select either Student or Lecturer!");
        }
      }
    } catch (NumberFormatException e) {
      if (id.getText().isBlank()){
        loginMessage.setText("Please fill in all required fields!");
      } else {
      loginMessage.setText("ID must be numbers only!");
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
