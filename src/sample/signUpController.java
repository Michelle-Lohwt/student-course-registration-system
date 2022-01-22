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
  private TextField matricNo, TextPassword, reTextPassword;

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

  public void signUp(MouseEvent event) throws IOException{
    if(matricNo.getText().isBlank()==true && password.getText().isBlank()==true && rePassword.getText().isBlank()==true){
      signUpMessage.setText("Please enter matrics number and password!");
    }
 
    else if(matricNo.getText().isBlank()==false && password.getText().isBlank()==false && 
            password.getLength()<6 && rePassword.getText().isBlank()==false && rePassword.getLength()<6){
      signUpMessage.setText("Password must have minimum 6 characters!");
    }
 
    else if(matricNo.getText().isBlank()==true){
      signUpMessage.setText("Please enter matrics number!");
    }
 
    else if(password.getText().isBlank()==true || rePassword.getText().isBlank()==true){
      signUpMessage.setText("Please enter password!");
    }

    else if (!password.getText().equals (rePassword.getText())){
      signUpMessage.setText("Password does not match!");
    }

    else{
      File file1 = new File("data/Student Profile/"+matricNo.getText()+".txt");
      File file2 = new File("data/Student Course List/"+matricNo.getText()+".txt");
      File file3 = new File("data/Student Registered Course/"+matricNo.getText()+".txt");

      //Check whether if the account exist or not
      if(!file1.exists() && !file2.exists() && !file3.exists()){
        try{
          //Create Student Profile txt file and save their matric number and password into it
          file1.createNewFile();
          BufferedWriter writer=new BufferedWriter(new FileWriter("data/Student Profile/"+matricNo.getText()+".txt"));
          writer.write(matricNo.getText());
          writer.write("\n" + password.getText());
          writer.close();

          //Create Student Course List txt file and copy the Course List into it
          file2.createNewFile();
          try{
            FileInputStream in = new FileInputStream(new File("data/Course List.txt"));
            FileOutputStream out = new FileOutputStream(new File ("data/Student Course List/"+matricNo.getText()+".txt"));
              try{
                int n;
                while ((n = in.read()) != -1){
                  out.write(n);
                }
              }
              finally{
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

          signUpMessage.setText("Sign Up Successful!");
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
      }
      else if(file1.exists() || file2.exists() || file3.exists()) {
        signUpMessage.setText("This matric number has been registered before!");
      }
      else{
        signUpMessage.setText("An error occurred!");
      }  
    }
  }

  public void TriggerPasswordCheckBox() {
    if (showPassword.isSelected()) {
      TextPassword.setText(password.getText());
      reTextPassword.setText(rePassword.getText());

      TextPassword.setDisable(false);
      reTextPassword.setDisable(false);

      TextPassword.setVisible(true);
      reTextPassword.setVisible(true);

      password.setDisable(true);
      rePassword.setDisable(true);

      password.setVisible(false);
      rePassword.setVisible(false);

    } else {
      password.setText(TextPassword.getText());
      rePassword.setText(reTextPassword.getText());

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
}
