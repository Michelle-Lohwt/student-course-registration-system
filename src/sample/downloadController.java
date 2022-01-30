package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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
  static String lectID;
  static String CourseTitle;

  //Get StudentID
  public static void inputID(String text){
         stuID=text;
  }

  //Get LecturerID
  public static void inputLectID(String getLectID){
    lectID= getLectID;
  }

  //Get CourseTitle
  public static void inputCourseTitle(String getCourseTitle){
    CourseTitle= getCourseTitle;
  }

  //Button for printing student registered course
  @FXML
  public void handleButtonAction(ActionEvent event) throws FileNotFoundException,IOException,MalformedURLException {
    final DirectoryChooser dirchooser = new DirectoryChooser();

    Stage stage = (Stage) anchorid.getScene().getWindow();
    File file = dirchooser.showDialog(stage);

     if (file != null) {

      System.out.println("Path: " + file.getAbsolutePath());
      textfield.setText(file.getAbsolutePath());


      
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

      //Image
      String logopath= "src/sample/images/usm-ringlogo.png";
      ImageData data= ImageDataFactory.create(logopath);
      Image image1 = new Image(data);
      image1.scaleToFit(140f,120f);
      image1.setHorizontalAlignment(HorizontalAlignment.CENTER);
      // Get student name
      File stuinfoFile = new File("data/Student Dashboard/" + stuID + ".txt");
      Scanner sc = new Scanner(stuinfoFile);
      String stuName;
      stuName = Files.readAllLines(Paths.get("data/Student Dashboard/" + stuID + ".txt")).get(0);
      //Output PDF
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
      sc.close();
      pdfMessage.setText("The course list is printed successfully!");
    }

  }
  //Button for printing lecturer teaching course
  @FXML
  public void LectTeachButton(ActionEvent event) throws FileNotFoundException,IOException,MalformedURLException {
    final DirectoryChooser dirchooser = new DirectoryChooser();

    Stage stage = (Stage) anchorid.getScene().getWindow();
    File file = dirchooser.showDialog(stage);

     if (file != null) {

      System.out.println("Path: " + file.getAbsolutePath());
      textfield.setText(file.getAbsolutePath());


      
      //Table
      float TableColWidth[] = {50f,300f}; 
      Table courseTable = new Table(TableColWidth);

      courseTable.addCell("No.");
      courseTable.addCell("Course Teaching");

      //Print teaching course list in table
      try {
        File fileObj = new File("data/Lecturer Teaching Course/"+lectID+".txt");
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
      PdfWriter pdfWriter = new PdfWriter(file.getAbsolutePath() + "/TeachingCourseList.pdf");

      PdfDocument pdfDocument = new PdfDocument(pdfWriter);
      pdfDocument.addNewPage();

      Document document = new Document(pdfDocument);

      //Image
      String logopath= "src/sample/images/usm-ringlogo.png";
      ImageData data= ImageDataFactory.create(logopath);
      Image image1 = new Image(data);
      image1.scaleToFit(140f,120f);
      image1.setHorizontalAlignment(HorizontalAlignment.CENTER);
      
      //Get lecturer name
      String lectName;
      Scanner sc = new Scanner(new File("data/Lecturer Dashboard/" + lectID + ".txt"));
      lectName= Files.readAllLines(Paths.get("data/Lecturer Dashboard/" + lectID + ".txt")).get(0);
      
      //Output PDF
      document.add(image1);
      document.add(new Paragraph("Universiti Sains Malaysia").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(30f));
      document.add(new Paragraph("Lecturer ID:   "+lectID));
      if(lectName==null){
          document.add(new Paragraph("Lecturer Name:   *THE LECTURER NAME IS BLANK*"));
      }
      else{
        document.add(new Paragraph("Lectuer Name:   "+lectName));
      }
      document.add(courseTable);
      
      sc.close();
      document.close();

      pdfMessage.setText("The course list is printed successfully!");
    }

  }
  //Button for printing lecturer course student list
  @FXML
  public void LectCourseStuList(ActionEvent event) throws FileNotFoundException,IOException,MalformedURLException {
    final DirectoryChooser dirchooser = new DirectoryChooser();

    Stage stage = (Stage) anchorid.getScene().getWindow();
    File file = dirchooser.showDialog(stage);

     if (file != null) {

      System.out.println("Path: " + file.getAbsolutePath());
      textfield.setText(file.getAbsolutePath());


      
      //Table
      float TableColWidth[] = {50f,300f,100f}; 
      Table courseTable = new Table(TableColWidth);

      courseTable.addCell("No.");
      courseTable.addCell("Course Student List");
      courseTable.addCell("Student ID");
      //Print student list in table
      try {
      File fileObj = new File("data/Course Student List/"+CourseTitle+".txt");
      Scanner fileReader = new Scanner(fileObj);
      int count=1;
      while (fileReader.hasNextLine()) {
      courseTable.addCell(Integer.toString(count));
      String stuLine=fileReader.nextLine();
      char[] chars =stuLine.toCharArray();
      StringBuilder studentName = new StringBuilder();
      StringBuilder studentID = new StringBuilder();
      for(char c : chars){
        if(Character.isDigit(c)){
          studentID.append(c);
        }
        else{
          studentName.append(c);
        }
      }
      courseTable.addCell(studentName.toString());
      courseTable.addCell(studentID.toString());
      count++;
      }
      fileReader.close();
      } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      }
      PdfWriter pdfWriter = new PdfWriter(file.getAbsolutePath() + "/CourseStudentList.pdf");

      PdfDocument pdfDocument = new PdfDocument(pdfWriter);
      pdfDocument.addNewPage();

      Document document = new Document(pdfDocument);

      //Image
      String logopath= "src/sample/images/usm-ringlogo.png";
      ImageData data= ImageDataFactory.create(logopath);
      Image image1 = new Image(data);
      image1.scaleToFit(140f,120f);
      image1.setHorizontalAlignment(HorizontalAlignment.CENTER);

      document.add(image1);
      document.add(new Paragraph("Universiti Sains Malaysia").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(30f));

      document.add(new Paragraph("Course: "+CourseTitle));
      document.add(courseTable);
      
      document.close();

      pdfMessage.setText("The course student list is printed successfully!");
    }

  }
  @Override
  public void initialize(URL url, ResourceBundle rb) {

  }
}