package sample;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.scene.control.TextField;
import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sample.classes.Student;

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

    private Boolean validate() {
        Boolean valid = false;
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
            valid = true;
        }
        return valid;
    }

    public void SignIn(MouseEvent event) throws IOException, SQLException {
        try {
            if (!validate()) {
                return;
            }

            if (!rbStudent.isSelected() && !rbLecturer.isSelected()) {
                loginMessage.setFill(Color.RED);
                loginMessage.setText("Please select either Student or Lecturer");
                return;
            }

            if (rbStudent.isSelected()) {
                if (StudentDAO.loginUser(id.getText(), password.getText())) {
                    stuDashController.id = id.getText();
                    switchTo(event, "stuDash.fxml");
                }
            } else {
                if (LecturerDAO.loginUser(id.getText(), password.getText())) {
                    lecDashController.id = id.getText();
                    switchTo(event, "lecDash.fxml");
                }
            }

            loginMessage.setFill(Color.RED);
            loginMessage.setText("An error occurred!");
        } catch (SQLException exc) {
            //  TODO: Add more descriptive error messages
            //  loginMessage.setText("ID number is incorrect!");
            //  loginMessage.setText("Password is incorrect!");
            //  loginMessage.setText("This ID has not registered yet!");
        } catch (NumberFormatException e) {
            if (id.getText().isBlank()) {
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
