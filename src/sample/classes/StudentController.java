package sample.classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class StudentController implements Initializable{
@FXML
private Label label;
@FXML
private AnchorPane anchorid;
@FXML 
private TextField textfield;

@FXML
public void handleButtonAction(ActionEvent event) throws IOException{
    final DirectoryChooser dirchooser = new DirectoryChooser();

    Stage stage = (Stage) anchorid.getScene().getWindow();

    File file = dirchooser.showDialog(stage);

    BufferedWriter bw = new BufferedWriter(new FileWriter("filepath.txt"));
    if(file !=null)
    {   String myfilepath= file.getAbsolutePath();
        System.out.println("Path: " + myfilepath);
        textfield.setText(myfilepath);
        bw.write(myfilepath);
        
    }

   bw.close();
}

@Override
public void initialize(URL url, ResourceBundle rb){
    // TODO
}
}
