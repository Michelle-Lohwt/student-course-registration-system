package sample;

import sample.classes.Lecturer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class LecturerDAO extends DAO {

    public static void registerUser(String ID, String Pass) throws SQLException {
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("INSERT INTO LECTURER (LECTURER_ID, LECTURER_PASSWORD) VALUES (%s,'%s')", ID, Pass));

        } catch (SQLException exc) {
            exc.printStackTrace();
        } finally {
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }

    public static boolean loginUser(String ID, String Pass) throws SQLException {
        boolean status = false;
        Statement stmnt = null;
        ResultSet result = null;
        try {
            DBConnect();

            stmnt = conn.createStatement();
            result = stmnt.executeQuery(String.format("SELECT LECTURER_ID, LECTURER_PASSWORD FROM LECTURER WHERE LECTURER_ID = %s", ID));

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
}
