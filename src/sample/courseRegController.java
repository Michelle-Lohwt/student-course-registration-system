package sample;

import java.io.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.classes.Course;

public class courseRegController extends Controller implements Initializable {
    private ArrayList<Course> fullCourseList = new ArrayList<Course>();
    private final ArrayList<Course> availableCourseList = new ArrayList<Course>();
    private final ArrayList<Course> registeredCourseList = new ArrayList<Course>();

    @FXML
    private JFXButton addCourseButton, removeCourseButton;

    @FXML
    private ListView<String> courseList, registeredCourse;

    @FXML
    private ImageView printPreview;

    @FXML
    private Text courseTitle, time;

    @FXML
    private TextArea desc;

    @FXML
    private TextField searchCourse;

    static String stuID;
    static String stuName;

    public static void inputID(String text) {
        stuID = text;
    }

    public static void inputName(String text) {
        stuName = text;
    }

    public void StuDashboard(MouseEvent event) throws IOException {
        switchTo(event, "stuDash.fxml");
    }

    public void LogOut(MouseEvent event) throws IOException {
        switchTo(event, "logout.fxml");
    }

    public void ContactUs(MouseEvent event) throws IOException {
        switchTo(event, "stuReport.fxml");
    }

    public void printPreview(MouseEvent event) throws IOException {
        try {
            downloadController.registeredCourses = registeredCourseList;
            downloadController.studName = stuName;
            downloadController.stuID = stuID;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("download.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(new FileInputStream("src/sample/images/download.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Select Course List pdf directory");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }

    }

    public void openBrowser() throws URISyntaxException, IOException {
        openLink();
    }

    private void initCourseList() {
        try {
            fullCourseList = AppDAO.getCourseList();
        } catch (SQLException exc) {
            System.out.println("An error occurred while fetching course list");
        }
    }

    // Read and display list of all courses that can be registered from txt file
    public void displaycourselist() {
        availableCourseList.clear();
        for (Course course : fullCourseList) {
            if (!registeredCourseList.contains(course)) {
                availableCourseList.add(course);
                courseList.getItems().add(course.getCode());
            }
        }
    }

    // Read and display Courses Registered of the student from txt file
    public void displaycourseregistered() {
        registeredCourseList.clear();
        try {
            ArrayList<String> studReg = AppDAO.getStudentCourseList(stuID);
            for (Course course : fullCourseList) {
                if (studReg.contains(course.getCode())) {
                    registeredCourseList.add(course);
                }
            }
            registeredCourse.getItems().addAll(registeredCourseList.stream().map(Course::getCode).toList());
        } catch (SQLException exc) {
            System.out.println("An error occurred while fetching the student's course list");
        }
    }

    public void searchcourse() {
        // Get the list of courses that can be registered by the student and stored in
        // an ArrayList named "list"
        List<String> list = new ArrayList<>(availableCourseList.stream().map(Course::getCode).toList());


        // Perform the search(filter) in "list" and output the result in another
        // ArrayList named "result"
        List<String> result = list
                .stream()
                .filter(x -> x.toLowerCase().contains(searchCourse.getText().toLowerCase())).toList();

        // Clear the Course List ListView
        courseList.getItems().clear();
        // Display the search result in Listview
        courseList.getItems().addAll(result);
    }

    // Clear the Course Details
    public void cleardetails() {
        courseTitle.setText("");
        time.setText("");
        desc.setText("");
    }

    private Course getCourseByID(String ID) {
        return fullCourseList.stream().filter(course -> Objects.equals(course.getCode(), ID)).toList().get(0);
    }

    // When a Course in the Course List is Clicked, this function will be executed.
    public void courselistdetails() {
        if (courseList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        addCourseButton.setDisable(false);

        // Clear the Course Details before displaying
        cleardetails();

        // Display the Course Details
        Course selectedCourse = getCourseByID(courseList.getSelectionModel().getSelectedItem());
        time.setText(selectedCourse.getTime());
        courseTitle.setText(selectedCourse.getTitle());
        desc.setText(selectedCourse.getDesc());
    }

    // When a Course in the Course Registered is Clicked, this function will be
    // executed.
    public void courseregistereddetails() {
        if (registeredCourse.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        removeCourseButton.setDisable(false);
        // Clear the Course Details before displaying
        cleardetails();

        // Display the Course Details
        Course selectedCourse = getCourseByID(registeredCourse.getSelectionModel().getSelectedItem());
        time.setText(selectedCourse.getTime());
        courseTitle.setText(selectedCourse.getTitle());
        desc.setText(selectedCourse.getDesc());
    }

    public void addcourse() {
        if (courseList.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        try {
            // Get course
            Course selectedCourse = getCourseByID(courseList.getSelectionModel().getSelectedItem());
            // Add to DB
            AppDAO.registerStudentCourse(stuID, selectedCourse.getCode());
            // Add to registered list
            registeredCourseList.add(selectedCourse);
            // Remove from fullCourseList
            availableCourseList.remove(selectedCourse);
        } catch (SQLException exc) {
            return;
        }

        searchCourse.clear();

        updatebothlist();

        addCourseButton.setDisable(true);
        removeCourseButton.setDisable(true);

    }

    public void removecourse() {
        if (registeredCourse.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        Course selectedCourse = getCourseByID(registeredCourse.getSelectionModel().getSelectedItem());
        try {
            AppDAO.removeStudentCourse(stuID, selectedCourse.getCode());
            availableCourseList.add(selectedCourse);
            registeredCourseList.remove(selectedCourse);
        } catch (SQLException exc) {
            return;
        }

        searchCourse.clear();

        // Update both Registered Course ListView, Course List ListView and
        // filter out courses that have been registered by the student.
        updatebothlist();

        addCourseButton.setDisable(true);
        removeCourseButton.setDisable(true);
    }

    public void updatebothlist() {
        registeredCourse.getItems().clear();
        displaycourseregistered();
        courseList.getItems().clear();
        displaycourselist();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCourseList();
        displaycourseregistered();
        displaycourselist();
        addCourseButton.setDisable(true);
        removeCourseButton.setDisable(true);
    }
}