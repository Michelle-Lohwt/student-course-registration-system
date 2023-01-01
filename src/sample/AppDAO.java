package sample;

import sample.classes.Course;
import sample.classes.Lecturer;
import sample.classes.Student;

import java.sql.*;
import java.util.ArrayList;

// TODO: Turn AppDAO into an interface and implement it in StudentDAO, LecturerDAO, and CourseDAO
public class AppDAO {
    public enum UserType {
        STUDENT, LECTURER
    }

    public enum ChoiceBoxItems{
        SEMESTER, SCHOOL, CAMPUS, PROGRAMME, MAJOR, MINOR, POSITION
    }

    private static final String DBUrl = String.format("jdbc:oracle:thin:%s/%s@%s", APIKeys.DBuser, APIKeys.DBPass, APIKeys.DBName);
    private static Connection conn = null;

    public static void DBConnect() throws SQLException {
        try {
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            conn = DriverManager.getConnection(DBUrl);
            if (conn != null) {
                System.out.println("Connected Successfully");
            }
        } catch (SQLException exc) {
            System.out.println("Something terrible happened!");
        }
    }

    public static void DBDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public static void registerUser(String ID, String Pass, UserType User) throws SQLException {
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            switch (User) {
                case STUDENT ->
                        stmnt.execute(String.format("INSERT INTO STUDENT (STUDENT_ID, STUDENT_PASSWORD) VALUES (%s,'%s')", ID, Pass));
                case LECTURER ->
                        stmnt.execute(String.format("INSERT INTO LECTURER (LECTURER_ID, LECTURER_PASSWORD) VALUES (%s,'%s')", ID, Pass));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static boolean loginUser(String ID, String Pass, UserType User) throws SQLException {
        boolean status = false;
        Statement stmnt = null;
        ResultSet result = null;
        try {
            DBConnect();

            stmnt = conn.createStatement();
            result = switch (User) {
                case STUDENT ->
                        stmnt.executeQuery(String.format("SELECT STUDENT_ID, STUDENT_PASSWORD FROM STUDENT WHERE STUDENT_ID = %s", ID));
                case LECTURER ->
                        stmnt.executeQuery(String.format("SELECT LECTURER_ID, LECTURER_PASSWORD FROM LECTURER WHERE LECTURER_ID = %s", ID));
            };

            result.next();
            String correctPassword = result.getString(2);
            if (Pass.equals(correctPassword)) {
                status = true;
            }

        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (result != null) {
                result.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }

            DBDisconnect();
        }
        return status;
    }

    public static Student getStudentDetails(int ID) throws SQLException{
        Statement stmnt = null;
        ResultSet result = null;
        Student stud = null;
        try {
            DBConnect();

            stmnt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            result = stmnt.executeQuery(String.format("SELECT * FROM STUDENT WHERE STUDENT_ID = %s", ID));
            result.first();
            stud = new Student(ID);
            stud.setName(result.getString("STUDENT_NAME"));
            stud.setCampus(result.getString("CAMPUS_NAME"));
            stud.setCGPA(result.getFloat("STUDENT_GPA"));
            stud.setMajor(result.getString("MAJOR_CODE"));
            stud.setMinor(result.getString("MINOR_CODE"));
            stud.setSchool(result.getString("SCHOOL_NAME"));
            stud.setNRIC(result.getLong("STUDENT_NRIC"));
            stud.setProgramme(result.getString("DEGREE_NAME"));
            stud.setSem(result.getString("SEMESTER_CODE"));
            stud.setYear(result.getInt("STUDENT_YEAR"));
            stud.setStatus(result.getString("STATUS_NAME"));

        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {

            if (result != null) {
                result.close();
            }
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
        return stud;
    }

    private static String nullFormat(String text){
        if(text == null){
            return null;
        } else{
            return "'" + text + "'";
        }
    }

    private static Long nullFormat(long num){
        return num == 0 ? null : num;
    }

    public static void updateStudentDetails(Student stud) throws SQLException{
        Statement stmnt = null;
        try{
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format(
                    "UPDATE STUDENT SET STUDENT_NAME = %s, STUDENT_NRIC = %s, STATUS_NAME = %s, SEMESTER_CODE = %s, STUDENT_GPA = %s, STUDENT_YEAR = %s, SCHOOL_NAME = %s, CAMPUS_NAME = %s, DEGREE_NAME = %s, MAJOR_CODE = %s, MINOR_CODE = %s WHERE STUDENT_ID = %s",
                    nullFormat(stud.getName()), nullFormat(stud.getNRIC()), nullFormat(stud.getStatus()), nullFormat(stud.getSem()), stud.getCGPA(), stud.getYear(),
                    nullFormat(stud.getSchool()), nullFormat(stud.getCampus()), nullFormat(stud.getProgramme()), nullFormat(stud.getMajor()), nullFormat(stud.getMinor()),
                    stud.getID()
                    ));
            System.out.println("Student record updated successfully!");
        } catch (SQLException exc) {
            System.out.println("Failed to update student");
        } finally {
            if(stmnt != null){
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static ArrayList<String> getChoiceBoxItems(ChoiceBoxItems choice) throws SQLException{
        Statement stmnt = null;
        ResultSet result = null;
        ArrayList<String> degrees = new ArrayList<String>();
        try{
            DBConnect();
            stmnt = conn.createStatement();
            final String query = "SELECT * FROM " + switch (choice) {
                case SEMESTER -> "SEMESTER";
                case SCHOOL -> "SCHOOLS";
                case CAMPUS -> "CAMPUS";
                case PROGRAMME -> "DEGREE";
                case MAJOR -> "MAJOR";
                case MINOR -> "MINOR";
                case POSITION -> "POSITIONS";
            };

            final String colName = switch (choice) {
                case SEMESTER -> "SEMESTER_CODE";
                case SCHOOL -> "SCHOOL_NAME";
                case CAMPUS -> "CAMPUS_NAME";
                case PROGRAMME -> "DEGREE_NAME";
                case MAJOR -> "MAJOR_CODE";
                case MINOR -> "MINOR_CODE";
                case POSITION -> "POSITION_TITLE";
            };

            result = stmnt.executeQuery(query);

            while(result.next()){
                degrees.add(result.getString(colName));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (stmnt != null){
                stmnt.close();
            }
            if(result != null){
                result.close();
            }
            DBDisconnect();
        }
        return degrees;
    }

    public static ArrayList<Course> getCourseList() throws SQLException{
        Statement stmnt = null;
        ResultSet result = null;
        ArrayList<Course> courses= new ArrayList<Course>();
        try {
            DBConnect();
            stmnt = conn.createStatement();
            result = stmnt.executeQuery("SELECT * FROM COURSES");

            while(result.next()){
                courses.add(new Course(result.getString("COURSE_CODE"), result.getString("COURSE_TITLE"), result.getString("COURSE_DESC"), result.getString("COURSE_TIME")));
            }
        } catch (SQLException exc) {
            System.out.println("Something went wrong while getting the course list");
        } finally {
            if(stmnt != null){
                stmnt.close();
            }
            if(result != null){
                result.close();
            }
            DBDisconnect();
        }
        return courses;
    }

    public static ArrayList<String> getStudentCourseList (String ID) throws SQLException {
        Statement stmnt = null;
        ResultSet result = null;
        ArrayList<String> studentCourseList = new ArrayList<String>();
        try {
            DBConnect();
            stmnt = conn.createStatement();
            result = stmnt.executeQuery(String.format("SELECT COURSE_CODE FROM STUDENT_COURSES WHERE STUDENT_ID = %s", ID));

            while(result.next()){
                studentCourseList.add(result.getString("COURSE_CODE"));
            }
        } catch (SQLException exc) {
            System.out.println("An error occurred while fetching the student's course list");
        } finally {
            if (stmnt != null){
                stmnt.close();
            }
            if (result != null){
                result.close();
            }
            DBDisconnect();
        }
        return studentCourseList;
    }

    public static void registerStudentCourse(String studID, String courseCode) throws SQLException {
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("INSERT INTO STUDENT_COURSES VALUES ('%s', '%s')", studID, courseCode));

        } catch (SQLException exc) {
            System.out.println("Couldn't register student to course");
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static void removeStudentCourse(String studID, String courseCode) throws SQLException{
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("DELETE FROM STUDENT_COURSES WHERE STUDENT_ID = '%s' AND COURSE_CODE = '%s'", studID, courseCode));

        } catch (SQLException exc) {
            System.out.println("Couldn't unregister student to course");
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static Lecturer getLecturerDetails(int ID) throws SQLException {
        Statement stmnt = null;
        ResultSet result = null;
        Lecturer lect = new Lecturer(ID);
        try {
            DBConnect();
            stmnt = conn.createStatement();
            result = stmnt.executeQuery(String.format("SELECT * FROM LECTURER WHERE LECTURER_ID = %s", ID));
            result.next();

            lect.setName(result.getString("LECTURER_NAME"));
            lect.setNRIC(result.getLong("LECTURER_NRIC"));
            lect.setStatus(result.getString("STATUS_NAME"));
            lect.setPosition(result.getString("POSITION_TITLE"));
            lect.setSchool(result.getString("SCHOOL_NAME"));
            lect.setCampus(result.getString("CAMPUS_NAME"));
        } catch (SQLException exc) {
            System.out.println("An error occurred while retrieving lecturer's details");
        } finally {
            if (stmnt != null){
                stmnt.close();
            }
            if (result != null) {
                result.close();
            }
            DBDisconnect();
        }
        return lect;
    }

    public static void updateLecturerDetails(Lecturer lect) throws SQLException{
        Statement stmnt = null;
        try{
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format(
                    "UPDATE LECTURER SET LECTURER_NAME = %s, LECTURER_NRIC = %s, STATUS_NAME = %s, POSITION_TITLE = %s, SCHOOL_NAME = %s, CAMPUS_NAME = %s WHERE LECTURER_ID = %s",
                    nullFormat(lect.getName()), nullFormat(lect.getNRIC()), nullFormat(lect.getStatus()), nullFormat(lect.getPosition()),
                    nullFormat(lect.getSchool()), nullFormat(lect.getCampus()), lect.getID()
            ));
            System.out.println("Lecturer record updated successfully!");
        } catch (SQLException exc) {
            System.out.println("Failed to update lecturer details");
            exc.printStackTrace();
        } finally {
            if(stmnt != null){
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static ArrayList<String> getTeachingList(int ID) throws SQLException{
        Statement stmnt = null;
        ResultSet result = null;
        ArrayList<String> lecturerTeachingList = new ArrayList<String>();
        try {
            DBConnect();
            stmnt = conn.createStatement();
            result = stmnt.executeQuery(String.format("SELECT COURSE_CODE FROM LECTURER_COURSES WHERE LECTURER_ID = %s", ID));

            while(result.next()){
                lecturerTeachingList.add(result.getString("COURSE_CODE"));
            }
        } catch (SQLException exc) {
            System.out.println("An error occurred while fetching the lecturer's course list");
        } finally {
            if (stmnt != null){
                stmnt.close();
            }
            if (result != null){
                result.close();
            }
            DBDisconnect();
        }
        return lecturerTeachingList;
    }

    public static void addLecturerCourse(int lectID, String courseCode) throws SQLException {
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("INSERT INTO LECTURER_COURSES VALUES ('%s', '%s')", lectID, courseCode));

        } catch (SQLException exc) {
            System.out.println("Couldn't assign course to lecturer");
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static void removeLecturerCourse(int lectID, String courseCode) throws SQLException {
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("DELETE FROM LECTURER_COURSES WHERE LECTURER_ID = '%s' AND COURSE_CODE = '%s'", lectID, courseCode));

        } catch (SQLException exc) {
            System.out.println("Couldn't remove course from lecturer's teaching list");
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static void updateCourse(String time, String desc, String courseCode) throws SQLException {
        Statement stmnt = null;
        try{
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("UPDATE COURSES SET COURSE_TIME = %s, COURSE_DESC = %s WHERE COURSE_CODE = %s", nullFormat(time), nullFormat(desc), nullFormat(courseCode)));
            System.out.println("Course updated successfully!");
        } catch (SQLException exc) {
            System.out.println("Failed to update course");
        } finally {
            if(stmnt != null){
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static ArrayList<String> getCourseStudent(String courseCode) throws SQLException {
        Statement stmnt = null;
        ResultSet result = null;
        ArrayList<String> studentIDs = new ArrayList<String>();
        try {
            DBConnect();
            stmnt = conn.createStatement();
            result = stmnt.executeQuery(String.format("SELECT STUDENT_ID FROM STUDENT_COURSES WHERE COURSE_CODE = %s", nullFormat(courseCode)));

            while(result.next()){
                studentIDs.add(result.getString("STUDENT_ID"));
            }
        } catch (SQLException exc) {
            System.out.println("An error occurred while fetching the course's student list");
        } finally {
            if (stmnt != null){
                stmnt.close();
            }
            if (result != null){
                result.close();
            }
            DBDisconnect();
        }
        return studentIDs;
    }

}
