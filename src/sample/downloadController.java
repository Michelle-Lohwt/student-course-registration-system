package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class downloadController implements Initializable {
  @FXML
  private Label label;
  @FXML
  private AnchorPane anchorid;
  @FXML
  private TextField textfield;
  @FXML
  private Text pdfMessage;

  static String stuID;
  static String stuName;

  //Get StudentID
  public static void inputID(String text){
         stuID=text;
  }

  //Get student name
  public static void inputName(String getname){
    stuName= getname;
  }

  @FXML
  public void handleButtonAction(ActionEvent event) throws FileNotFoundException,IOException {
    final DirectoryChooser dirchooser = new DirectoryChooser();

    Stage stage = (Stage) anchorid.getScene().getWindow();
    File file = dirchooser.showDialog(stage);

     if (file != null) {

      System.out.println("Path: " + file.getAbsolutePath());
      textfield.setText(file.getAbsolutePath());

      //Image
      ImageData data= ImageDataFactory.create("C:/Users/user/student-course-registration-system/src/sample/images/usm-ringlogo.png");
      Image image1 = new Image(data);
      image1.scaleToFit(140f,120f);
      image1.setHorizontalAlignment(HorizontalAlignment.CENTER);
      
      //Table
      float TableColWidth[] = {50f,300f}; 
      Table courseTable = new Table(TableColWidth);

      courseTable.addCell("No.");
      courseTable.addCell("Course Taken");

      //Print course list in table
      try {
        File fileObj = new File("data/Student Registered Course/"+stuID+".txt");
        Scanner fileReader = new Scanner(fileObj);
        int count=1;
        while (fileReader.hasNextLine()) {
          courseTable.addCell(Integer.toString(count));
          courseTable.addCell(fileReader.nextLine());
          count++;
        }
        fileReader.close();
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      PdfWriter pdfWriter = new PdfWriter(file.getAbsolutePath() + "/CourseList.pdf");

      PdfDocument pdfDocument = new PdfDocument(pdfWriter);
      pdfDocument.addNewPage();

      Document document = new Document(pdfDocument);
      document.add(image1);
      document.add(new Paragraph("Universiti Sains Malaysia").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(30f));
      document.add(new Paragraph("Student ID:   "+stuID));
      if(stuName==null){
          document.add(new Paragraph("Student Name:   *THE STUDENT NAME IS BLANK*"));
      }
      else{
        document.add(new Paragraph("Student Name:   "+stuName));
      }
      document.add(courseTable);
      
      document.close();

      pdfMessage.setText("The course list is printed successfully!");
    }

  }
  
  @Override
  public void initialize(URL url, ResourceBundle rb) {

  }
}