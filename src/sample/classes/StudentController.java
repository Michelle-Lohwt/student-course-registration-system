package sample.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class StudentController implements Initializable {
  @FXML
  private Label label;
  @FXML
  private AnchorPane anchorid;
  @FXML
  private TextField textfield;
  @FXML
  private Text pdfMessage;
  @FXML
  public void handleButtonAction(ActionEvent event) throws IOException {
    final DirectoryChooser dirchooser = new DirectoryChooser();

    Stage stage = (Stage) anchorid.getScene().getWindow();

    File file = dirchooser.showDialog(stage);

    if (file != null) {
      List list1 = new List();
      //input the text file for the course list
      try {
      File fileObj = new File("student1.txt");
      Scanner fileReader = new Scanner(fileObj);
      while (fileReader.hasNextLine()) {
      list1.add(fileReader.nextLine());
      }
      fileReader.close();
      } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      }

      System.out.println("Path: " + file.getAbsolutePath());
      textfield.setText(file.getAbsolutePath());

      PdfWriter pdfWriter = new PdfWriter(file.getAbsolutePath() + "/CourseList.pdf");

      PdfDocument pdfDocument = new PdfDocument(pdfWriter);
      pdfDocument.addNewPage();

      Document document = new Document(pdfDocument);
      document.add(list1);

      document.close();
      pdfMessage.setText("The course list is printed successfully!");
    }

  }


  @Override
  public void initialize(URL url, ResourceBundle rb) {

  }
}