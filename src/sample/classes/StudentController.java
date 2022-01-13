package sample.classes;

import java.io.File;
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
private void handleButtonAction(ActionEvent event){
    final DirectoryChooser dirchooser = new DirectoryChooser();

    Stage stage = (Stage) anchorid.getScene().getWindow();

    File file = dirchooser.showDialog(stage);

    if(file !=null)
    {
        System.out.println("Path: " + file.getAbsolutePath());
        textfield.setText(file.getAbsolutePath());
    }

}

@Override
public void initialize(URL url, ResourceBundle rb){
    // TODO
}
}
