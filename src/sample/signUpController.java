package sample;

import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
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

    private Boolean validate() {
        Boolean valid = false;
        Integer.parseInt(id.getText());
        signUpMessage.setFill(Color.RED);
        if (id.getText().isBlank() || password.getText().isBlank() || rePassword.getText().isBlank()) {
            signUpMessage.setText("Please fill in all required fields!");
        } else if (id.getLength() != 6) {
            signUpMessage.setText("ID must be 6 characters");
        } else if (!password.getText().equals(rePassword.getText())) {
            signUpMessage.setText("Password does not match!");
        } else if (password.getLength() < 6 || password.getLength() > 25) {
            signUpMessage.setText("Password must be between 6 and 25 characters!");
        } else {
            valid = true;
        }
        return valid;
    }

    public void signUp(MouseEvent event) throws IOException {

        try {
            if (!validate()) {
                return;
            }

            if (!rbStudent.isSelected() && !rbLecturer.isSelected()) {
                signUpMessage.setFill(Color.RED);
                signUpMessage.setText("Please select either Student or Lecturer!");
                return;
            }

            if (rbStudent.isSelected()) {
                StudentDAO.registerUser(id.getText(), password.getText());
            } else {
                LecturerDAO.registerUser(id.getText(), password.getText());
            }
            signUpMessage.setFill(Color.GREEN);
            signUpMessage.setText("SignUp Successful");

        } catch (SQLException exc) {
            signUpMessage.setFill(Color.RED);
            // TODO: Add more descriptive error messages
            //Check whether if the account exist or not
            signUpMessage.setText("An error occurred!");
        } catch (NumberFormatException e) {
            if (id.getText().isBlank()) {
                signUpMessage.setText("Please fill in all required fields!");
            } else {
                signUpMessage.setText("ID must be numbers only!");
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