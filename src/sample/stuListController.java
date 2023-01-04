package sample;

import sample.classes.Course;
import sample.classes.Student;
import sample.classes.StudentList;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class stuListController extends Controller implements Initializable {

    private ArrayList<Student> studentsList = new ArrayList<Student>();

    private ArrayList<Course> fullCourseList = new ArrayList<Course>();

    private final ArrayList<Course> availableCourseList = new ArrayList<Course>();

    private final ArrayList<Course> teachingCourseList = new ArrayList<Course>();

    @FXML
    private JFXButton addCourseButton, removeCourseButton, editCourseButton, saveCourseDetailsButton;

    @FXML
    private ListView<String> courseList, teachingCourse;

    @FXML
    private TextField searchCourse, searchStudent;

    @FXML
    public TableView<StudentList> studentList;

    @FXML
    public TableColumn<StudentList, String> name;

    @FXML
    public TableColumn<StudentList, String> matric;

    @FXML
    private Text courseTitle;

    @FXML
    private TextField time;

    @FXML
    private TextArea desc;

    @FXML
    private ImageView downloadStudent, downloadTeaching;

    static String lecID;

    public static void inputID(String text) {
        lecID = text;
    }

    public void LecDashboard(MouseEvent event) throws IOException {
        switchTo(event, "lecDash.fxml");
    }

    public void LogOut(MouseEvent event) throws IOException {
        switchTo(event, "logout.fxml");
    }

    public void ContactUs(MouseEvent event) throws IOException {
        switchTo(event, "lecReport.fxml");
    }

    public void openBrowser() throws URISyntaxException, IOException {
        openLink();
    }

    private void initCourseList() {
        try {
            fullCourseList = CourseDAO.getCourseList();
        } catch (SQLException exc) {
            System.out.println("An error occurred while fetching the course list");
        }
    }

    // Read and display list of all courses that can be registered from DB
    public void displaycourselist() {
        availableCourseList.clear();
        for (Course course : fullCourseList) {
            if (!teachingCourseList.contains(course)) {
                availableCourseList.add(course);
                courseList.getItems().add(course.getCode());
            }
        }
    }

    // Read and display teaching courses of the lecturer from DB
    public void displayteachingcourse() {
        teachingCourseList.clear();
        try {
            ArrayList<String> teachingCourses = LecturerDAO.getTeachingList(Integer.parseInt(lecID));
            for (Course course : fullCourseList) {
                if (teachingCourses.contains(course.getCode())) {
                    teachingCourseList.add(course);
                    teachingCourse.getItems().add(course.getCode());
                }
            }
        } catch (SQLException exc) {
            return;
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

    //Clear the Course Details
    public void cleardetails() {
        courseTitle.setText("");
        time.clear();
        desc.clear();
    }

    // When a Course in the Course List is Clicked, this function will be executed.
    public void courselist() {
        if (courseList.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        addCourseButton.setDisable(false);

        //Clear the TableView
        studentList.getItems().clear();

        //Clear the Course Details
        cleardetails();

        Course selectedCourse = getCourseByID(courseList.getSelectionModel().getSelectedItem());

        courseTitle.setText(selectedCourse.getTitle());
        desc.setText(selectedCourse.getDesc());
        time.setText(selectedCourse.getTime());

        getCourseStudentList();
    }

    // When a Course in the Course Registered is Clicked, this function will be
    // executed.
    public void teachingcourse() {
        if (teachingCourse.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        removeCourseButton.setDisable(false);

        //Clear the TableView
        studentList.getItems().clear();

        //Clear the Course Details
        cleardetails();

        Course selectedCourse = getCourseByID(teachingCourse.getSelectionModel().getSelectedItem());

        courseTitle.setText(selectedCourse.getTitle());
        desc.setText(selectedCourse.getDesc());
        time.setText(selectedCourse.getTime());

        editCourseButton.setDisable(false);

        getCourseStudentList();
    }

    private Course getCourseByID(String courseID) {
        return fullCourseList.stream().filter(course -> Objects.equals(course.getCode(), courseID)).toList().get(0);
    }

    public void addcourse() {
        if (courseList.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        try {
            LecturerDAO.addLecturerCourse(Integer.parseInt(lecID), courseList.getSelectionModel().getSelectedItem());
            Course selectedCourse = getCourseByID(courseList.getSelectionModel().getSelectedItem());
            availableCourseList.remove(selectedCourse);
            teachingCourseList.add(selectedCourse);
        } catch (SQLException exc) {
            return;
        }

        // Update both Teaching Course ListView, Course List ListView and
        // filter out courses that have been registered by the lecturer.
        updatebothlist();

        // Clear the searchCourse Textfield after a course is registered.
        searchCourse.clear();

        addCourseButton.setDisable(true);
        removeCourseButton.setDisable(true);

    }

    public void removecourse() {

        if (teachingCourse.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        try {
            LecturerDAO.removeLecturerCourse(Integer.parseInt(lecID), teachingCourse.getSelectionModel().getSelectedItem());
            Course selectedCourse = getCourseByID(teachingCourse.getSelectionModel().getSelectedItem());
            teachingCourseList.remove(selectedCourse);
            availableCourseList.add(selectedCourse);
        } catch (SQLException exc) {
            return;
        }

        updatebothlist();

        addCourseButton.setDisable(true);
        removeCourseButton.setDisable(true);
    }


    public void updatebothlist() {
        // Clear the Teaching Course ListView
        teachingCourse.getItems().clear();
        // Read and Display Teaching Courses from txt file
        displayteachingcourse();
        // Clear the Course List ListView
        courseList.getItems().clear();
        // Read and display list of all courses that can be registered from txt file
        displaycourselist();
    }

    public void EditCourse() {
        saveCourseDetailsButton.setDisable(false);
        editCourseButton.setDisable(true);
        courseList.setDisable(true);
        teachingCourse.setDisable(true);
        addCourseButton.setDisable(true);
        removeCourseButton.setDisable(true);

        time.setEditable(true);
        desc.setEditable(true);

        time.setStyle("-fx-border-color: #eb7231");
        desc.setStyle("-fx-border-color: #eb7231");
    }

    public void SaveCourse() {
        saveCourseDetailsButton.setDisable(true);
        editCourseButton.setDisable(false);
        courseList.setDisable(false);
        teachingCourse.setDisable(false);
        addCourseButton.setDisable(true);
        removeCourseButton.setDisable(true);

        time.setEditable(false);
        desc.setEditable(false);

        time.setStyle("-fx-border-color: transparent");
        desc.setStyle("-fx-border-color: transparent");

        try {
            Course selectedCourse = fullCourseList.stream().filter(course -> Objects.equals(course.getTitle(), courseTitle.getText())).toList().get(0);
            CourseDAO.updateCourse(time.getText(), desc.getText(), selectedCourse.getCode());
            selectedCourse.setTime(time.getText());
            selectedCourse.setDesc(desc.getText());
        } catch (SQLException exc) {
            return;
        }
    }

    private void getCourseStudentList() {
        studentsList.clear();
        ArrayList<String> students = null;
        try {
            Course selectedCourse = fullCourseList.stream().filter(course -> Objects.equals(course.getTitle(), courseTitle.getText())).toList().get(0);
            students = CourseDAO.getCourseStudent(selectedCourse.getCode());
            for (String studentID : students) {
                studentsList.add(StudentDAO.getStudentDetails(Integer.parseInt(studentID)));
            }
        } catch (SQLException exc) {
            System.out.println("Oh no! oh no! oh no no no!!");
        }
        Collection<StudentList> table = studentsList
                .stream()
                .map(student -> {
                    StudentList sl = new StudentList();
                    sl.setName(student.getName());
                    sl.setMatric(Integer.toString(student.getID()));
                    return sl;
                }).toList();

        // Clear the TableView
        studentList.getItems().clear();

        // Display the search result in Listview

        ObservableList<StudentList> details = FXCollections.observableArrayList(table);

        name.setCellValueFactory(data -> data.getValue().nameProperty());
        matric.setCellValueFactory(data -> data.getValue().matricProperty());

        studentList.setItems(details);
    }

    public void searchstudent() {
        if(searchStudent.getText().isEmpty()) {
            getCourseStudentList();
        }
        ArrayList<StudentList> filteredList = new ArrayList<StudentList>(studentsList.stream().filter(stud -> stud.getName().contains(searchStudent.getText())).map(stud -> {
            StudentList sl = new StudentList();
            sl.setName(stud.getName());
            sl.setMatric(Integer.toString(stud.getID()));
            return sl;
        }).toList());
        studentList.setItems(FXCollections.observableArrayList(filteredList));
    }

    public void printTeachCourse(MouseEvent event) throws IOException {
        try {
            downloadController.registeredCourses = teachingCourseList;
            downloadController.lectID = lecID;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TeachCourseDownload.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(new FileInputStream("src/sample/images/download.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Select Teaching Course List pdf directory");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }

    public void printStuList(MouseEvent event) throws IOException {
        try {
            downloadController.courseStudents = studentsList;
            Course selectedCourse = fullCourseList.stream().filter(course -> Objects.equals(course.getTitle(), courseTitle.getText())).toList().get(0);
            downloadController.CourseTitle = String.format("%s - %s", selectedCourse.getCode(), selectedCourse.getTitle());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StuListDownload.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Image icon = new Image(new FileInputStream("src/sample/images/download.png"));
            stage.getIcons().add(icon);
            stage.setTitle("Select Course Student List pdf directory");
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCourseList();
        displayteachingcourse();
        displaycourselist();
        addCourseButton.setDisable(true);
        removeCourseButton.setDisable(true);
        editCourseButton.setDisable(true);
        saveCourseDetailsButton.setDisable(true);
    }
}