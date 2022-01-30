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

    try {
      Integer.parseInt(id.getText());
      if (id.getText().isBlank() || password.getText().isBlank() || rePassword.getText().isBlank()) {
        signUpMessage.setText("Please fill in all required fields!");
      } else if (id.getLength() != 6) {
        signUpMessage.setText("ID must be 6 characters");
      } else if (!password.getText().equals(rePassword.getText())) {
        signUpMessage.setText("Password does not match!");
      } else if (password.getText().equals(rePassword.getText()) && password.getLength() < 6) {
        signUpMessage.setText("Password must have minimum 6 characters!");
      } else{
        if(rbStudent.isSelected()){
          File file1 = new File("data/Student Profile/"+id.getText()+".txt");
          File file2 = new File("data/Student Course List/"+id.getText()+".txt");
          File file3 = new File("data/Student Registered Course/"+id.getText()+".txt");
          File file4 = new File("data/Student Dashboard/"+id.getText()+".txt");
        
          //Check whether if the account exist or not
          if(!file1.exists() && !file2.exists() && !file3.exists() && !file4.exists()){
            try{
              //Create Student Profile txt file and save their matric number and password into it
              file1.createNewFile();
              BufferedWriter writer=new BufferedWriter(new FileWriter(file1));
              writer.write(id.getText() + "\n");
              writer.write(password.getText() + "\n");
              writer.close();
            
              //Create Student Course List txt file and copy the Course List into it
              file2.createNewFile();
              try{
                FileInputStream in = new FileInputStream(new File("data/Course List.txt"));
                FileOutputStream out = new FileOutputStream(file2);
                  try{
                    int n;
                    while ((n = in.read()) != -1){
                      out.write(n);
                    }
                  } finally{
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
  
              //Create Student Registered Course txt file
              file3.createNewFile();
              //Create Student Dashboard txt file
              file4.createNewFile();
              BufferedWriter writer1=new BufferedWriter(new FileWriter(file4));
              writer1.write("");
              writer1.write("\n" + "");
              writer1.write("\n" + id.getText());
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n" + "" + "\n");
              writer1.close();

              signUpMessage.setFill(Color.GREEN);
              signUpMessage.setText("Sign Up Successful!");
            } catch (IOException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
          } else if(file1.exists() || file2.exists() || file3.exists() || file4.exists()) {
            signUpMessage.setText("This ID has been registered before!");
          } else{
            signUpMessage.setText("An error occurred!");
          }  
        } else if(rbLecturer.isSelected()){
          File file1 = new File("data/Lecturer Profile/"+id.getText()+".txt");
          File file2 = new File("data/Lecturer Course List/"+id.getText()+".txt");
          File file3 = new File("data/Lecturer Teaching Course/"+id.getText()+".txt");
          File file4 = new File("data/Lecturer Dashboard/"+id.getText()+".txt");
        
          //Check whether if the account exist or not
          if(!file1.exists() && !file2.exists() && !file3.exists() && !file4.exists()){

            try{
              //Create Lecturer Profile txt file and save their staff id and password into it
              file1.createNewFile();
              BufferedWriter writer=new BufferedWriter(new FileWriter(file1));
              writer.write(id.getText() + "\n");
              writer.write(password.getText() + "\n");
              writer.close();
            
              //Create Student Course List txt file and copy the Course List into it
              file2.createNewFile();
              try{
                FileInputStream in = new FileInputStream(new File("data/Course List.txt"));
                FileOutputStream out = new FileOutputStream(file2);
                  try{
                    int n;
                    while ((n = in.read()) != -1){
                      out.write(n);
                    }
                  } finally{
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
            
              //Create Lecturer Registered Course txt file
              file3.createNewFile();
              //Create Lecturer Dashboard txt file
              file4.createNewFile();
              BufferedWriter writer1=new BufferedWriter(new FileWriter(file4));
              writer1.write("");
              writer1.write("\n");
              writer1.write("\n" + id.getText());
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n" + "");
              writer1.write("\n");
              writer1.close();

              signUpMessage.setFill(Color.GREEN);
              signUpMessage.setText("Sign Up Successful!");
            } catch (IOException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
          } else if(file1.exists() || file2.exists() || file3.exists() || file4.exists()) {
            signUpMessage.setText("This ID has been registered before!");
          } else{
            signUpMessage.setText("An error occurred!");
          }  
        } else{
          signUpMessage.setText("Please select either Student or Lecturer!");
        }
      }
    } catch (NumberFormatException e) {
      if (id.getText().isBlank()){
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