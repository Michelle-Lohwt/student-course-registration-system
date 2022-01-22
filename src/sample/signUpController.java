package sample;

import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;

import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class signUpController extends Controller implements Initializable {

  @FXML
  private TextField id, TextPassword, reTextPassword;

  @FXML
  private PasswordField password, rePassword;

  @FXML
  private RadioButton rbLecturer, rbStudent;

  @FXML
  private JFXCheckBox showPassword;

  @FXML
  private Text signUpMessage;

  @FXML
  private ToggleGroup studentLecturer;

  public void Login(MouseEvent event) throws IOException {
    switchTo(event, "login.fxml");
  }

  public void ContactUs(MouseEvent event) throws IOException {
    switchTo(event, "contactUs.fxml");
  }

  public void openBrowser() throws URISyntaxException, IOException {
    openLink();
  }

  public void signUp(MouseEvent event) throws IOException {
    if (id.getText().isBlank() || password.getText().isBlank() || rePassword.getText().isBlank()) {
      signUpMessage.setText("Please fill in all required fields!");
    } else if (id.getLength() != 6) {
      signUpMessage.setText("ID must be 6 characters");
    } else if (!password.getText().equals(rePassword.getText())) {
      signUpMessage.setText("Password does not match!");
    } else if (password.getText().equals(rePassword.getText()) && password.getLength() < 6) {
      signUpMessage.setText("Password must have minimum 6 characters!");
    } else {
      try {
        File fileObj = new File(id.getText() + ".txt");

        if (fileObj.exists()) {
          System.out.println(fileObj.getName() + " already exists.");
          signUpMessage.setText("This ID has been registered before!");
          // System.out.println(fileObj.getAbsolutePath());
        }

        else if (!fileObj.exists()) {
          if (rbStudent.isSelected() || rbLecturer.isSelected()) {
            signUpMessage.setFill(Color.GREEN);
            signUpMessage.setText("Sign Up Successful!");
            System.out.println("File created: " + fileObj.getName());
            BufferedWriter writer = new BufferedWriter(new FileWriter(id.getText() + ".txt"));
            writer.write(id.getText());
            writer.write("\n" + password.getText());
            writer.close();
            // System.out.println(fileObj.getAbsolutePath());
            // System.out.println(fileObj.getAbsolutePath());
          } else {
            signUpMessage.setText("Please choose Student or Lecturer!");
          }
        } else {
          signUpMessage.setText("An error occurred!");
        }
      }

      catch (IOException e) {
        signUpMessage.setText("This ID has been registered before!");
      }

    }
  }

  public void TriggerPasswordCheckBox() {
    if (showPassword.isSelected()) {

      TextPassword.setDisable(false);
      reTextPassword.setDisable(false);

      TextPassword.setVisible(true);
      reTextPassword.setVisible(true);

      password.setDisable(true);
      rePassword.setDisable(true);

      password.setVisible(false);
      rePassword.setVisible(false);

    } else {

      TextPassword.setDisable(true);
      reTextPassword.setDisable(true);

      TextPassword.setVisible(false);
      reTextPassword.setVisible(false);

      password.setDisable(false);
      rePassword.setDisable(false);

      password.setVisible(true);
      rePassword.setVisible(true);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    password.textProperty().bindBidirectional(TextPassword.textProperty());
    rePassword.textProperty().bindBidirectional(reTextPassword.textProperty());
  }
}
