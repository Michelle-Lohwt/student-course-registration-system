package sample;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
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
import sample.classes.Course;
import sample.classes.Student;

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
    static String studName;
    static ArrayList<Course> registeredCourses;
    static ArrayList<Student> courseStudents;
    static String lectID;
    static String CourseTitle;

    //Get StudentID
    public static void inputID(String text) {
        stuID = text;
    }

    //Get LecturerID
    public static void inputLectID(String getLectID) {
        lectID = getLectID;
    }

    //Get CourseTitle
    public static void inputCourseTitle(String getCourseTitle) {
        CourseTitle = getCourseTitle;
    }

    //Button for printing student registered course
    @FXML
    public void handleButtonAction(ActionEvent event) throws FileNotFoundException, IOException, MalformedURLException {
        final DirectoryChooser dirchooser = new DirectoryChooser();

        Stage stage = (Stage) anchorid.getScene().getWindow();
        File file = dirchooser.showDialog(stage);

        if (file == null) {
            return;
        }

        System.out.println("Path: " + file.getAbsolutePath());
        textfield.setText(file.getAbsolutePath());


        //Table
        float TableColWidth[] = {50f, 300f};
        Table courseTable = new Table(TableColWidth);

        courseTable.addCell("No.");
        courseTable.addCell("Course Taken");

        //Print course list in table
        int count = 1;
        for (Course course : registeredCourses) {
            courseTable.addCell(Integer.toString(count));
            courseTable.addCell(String.format("%s - %s", course.getCode(), course.getTitle()));
            count++;
        }

        PdfWriter pdfWriter = new PdfWriter(file.getAbsolutePath() + "/CourseList.pdf");

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);

        //Image
        String logopath = "src/sample/images/usm-ringlogo.png";
        ImageData data = ImageDataFactory.create(logopath);
        Image image1 = new Image(data);
        image1.scaleToFit(140f, 120f);
        image1.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //Output PDF
        document.add(image1);
        document.add(new Paragraph("Universiti Sains Malaysia").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(30f));
        document.add(new Paragraph("Student ID:   " + stuID));
        if (studName == null) {
            document.add(new Paragraph("Student Name:   *THE STUDENT NAME IS BLANK*"));
        } else {
            document.add(new Paragraph("Student Name:   " + studName));
        }
        document.add(courseTable);

        document.close();
        pdfMessage.setText("The course list is printed successfully!");
    }


    //Button for printing lecturer teaching course
    @FXML
    public void LectTeachButton(ActionEvent event) throws FileNotFoundException, IOException, MalformedURLException {
        final DirectoryChooser dirchooser = new DirectoryChooser();

        Stage stage = (Stage) anchorid.getScene().getWindow();
        File file = dirchooser.showDialog(stage);

        if (file != null) {

            System.out.println("Path: " + file.getAbsolutePath());
            textfield.setText(file.getAbsolutePath());


            //Table
            float TableColWidth[] = {50f, 300f};
            Table courseTable = new Table(TableColWidth);

            courseTable.addCell("No.");
            courseTable.addCell("Course Teaching");

            //Print teaching course list in table
            int count = 1;
            for (Course course : registeredCourses) {
                courseTable.addCell(Integer.toString(count));
                courseTable.addCell(String.format("%s - %s", course.getCode(), course.getTitle()));
                count++;
            }
            PdfWriter pdfWriter = new PdfWriter(file.getAbsolutePath() + "/TeachingCourseList.pdf");

            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();

            Document document = new Document(pdfDocument);

            //Image
            String logopath = "src/sample/images/usm-ringlogo.png";
            ImageData data = ImageDataFactory.create(logopath);
            Image image1 = new Image(data);
            image1.scaleToFit(140f, 120f);
            image1.setHorizontalAlignment(HorizontalAlignment.CENTER);

            String lecName = null;
            try {
                lecName = AppDAO.getLecturerDetails(Integer.parseInt(lectID)).getName();
            } catch (SQLException exc) {
                return;
            }

            //Output PDF
            document.add(image1);
            document.add(new Paragraph("Universiti Sains Malaysia").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(30f));
            document.add(new Paragraph("Lecturer ID:   " + lectID));
            if (lecName == null) {
                document.add(new Paragraph("Lecturer Name:   *THE LECTURER NAME IS BLANK*"));
            } else {
                document.add(new Paragraph("Lectuer Name:   " + lecName));
            }
            document.add(courseTable);

            document.close();

            pdfMessage.setText("The course list is printed successfully!");
        }

    }

    //Button for printing lecturer course student list
    @FXML
    public void LectCourseStuList(ActionEvent event) throws FileNotFoundException, IOException, MalformedURLException {
        final DirectoryChooser dirchooser = new DirectoryChooser();

        Stage stage = (Stage) anchorid.getScene().getWindow();
        File file = dirchooser.showDialog(stage);

        if (file == null) {
            return;
        }

        System.out.println("Path: " + file.getAbsolutePath());
        textfield.setText(file.getAbsolutePath());


        //Table
        float TableColWidth[] = {50f, 300f, 100f};
        Table courseTable = new Table(TableColWidth);

        courseTable.addCell("No.");
        courseTable.addCell("Course Student List");
        courseTable.addCell("Student ID");

        //Print student list in table
        int count = 1;
        for (Student stud : courseStudents) {
            courseTable.addCell(Integer.toString(count));
            courseTable.addCell(stud.getName());
            courseTable.addCell(Integer.toString(stud.getID()));
            count++;
        }

        PdfWriter pdfWriter = new PdfWriter(file.getAbsolutePath() + "/CourseStudentList.pdf");

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);

        //Image
        String logopath = "src/sample/images/usm-ringlogo.png";
        ImageData data = ImageDataFactory.create(logopath);
        Image image1 = new Image(data);
        image1.scaleToFit(140f, 120f);
        image1.setHorizontalAlignment(HorizontalAlignment.CENTER);

        document.add(image1);
        document.add(new Paragraph("Universiti Sains Malaysia").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(30f));

        document.add(new Paragraph("Course: " + CourseTitle));
        document.add(courseTable);

        document.close();

        pdfMessage.setText("The course student list is printed successfully!");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}