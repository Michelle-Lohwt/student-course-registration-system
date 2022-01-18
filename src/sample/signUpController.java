package sample;

import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;

import com.jfoenix.controls.JFXCheckBox;

import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;

public class signUpController extends Controller {

  @FXML
  private TextField matricNo;

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

  public void signUp(MouseEvent event) {

    // These 3 things are actually You Quan's part, but I help him to do a bit.
    // These 3 things are actually need to happen whenever a new user sign up. No
    // need if log in.

    // 1. Create student1courselist.txt file
    try {
      File fileObj = new File("student1courseList.txt");
      if (fileObj.createNewFile()) {
        System.out.println("File created: " + fileObj.getName());
        // System.out.println(fileObj.getAbsolutePath());
      } else {
        System.out.println(fileObj.getName() + " already exists.");
        // System.out.println(fileObj.getAbsolutePath());
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    // 2. Copy the content of courseList.txt into student1courseList.txt
    try {
      FileInputStream in = new FileInputStream(new File("courseList.txt"));
      FileOutputStream out = new FileOutputStream(new File("student1courseList.txt"));

      try {
        int n;
        while ((n = in.read()) != -1) {
          out.write(n);
        }
      }

      finally {
        if (in != null) {
          in.close();
        }
        if (out != null) {
          out.close();
        }
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    // 3. Create student1registeredCourse.txt file
    try {
      File fileObj = new File("student1registeredCourse.txt");
      if (fileObj.createNewFile()) {
        System.out.println("File created: " + fileObj.getName());
        // System.out.println(fileObj.getAbsolutePath());
      } else {
        System.out.println(fileObj.getName() + " already exists.");
        // System.out.println(fileObj.getAbsolutePath());
      }
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    signUpMessage.setText("Sign Up Successful");
  }
}