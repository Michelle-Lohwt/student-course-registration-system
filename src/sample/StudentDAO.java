package sample;

import sample.classes.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class StudentDAO extends DAO {

    public static void registerUser(String ID, String Pass) throws SQLException {
        Statement stmnt = null;
        try {
            DBConnect();
            stmnt = conn.createStatement();
            stmnt.execute(String.format("INSERT INTO STUDENT (STUDENT_ID, STUDENT_PASSWORD) VALUES (%s,'%s')", ID, Pass));
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
            result = stmnt.executeQuery(String.format("SELECT STUDENT_ID, STUDENT_PASSWORD FROM STUDENT WHERE STUDENT_ID = %s", ID));

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

    public static Student getStudentDetails(int ID) throws SQLException {
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

    public static void updateStudentDetails(Student stud) throws SQLException {
        Statement stmnt = null;
        try {
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
            if (stmnt != null) {
                stmnt.close();
            }
            DBDisconnect();
        }
    }
}
