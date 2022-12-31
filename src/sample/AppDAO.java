package sample;

import java.sql.*;

public class AppDAO {
    public enum UserType {
        STUDENT, LECTURER
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
                        stmnt.executeQuery(String.format("SELECT LECTURER_ID, LECTURER_PASSWORD FROM STUDENT WHERE LECTURER_ID = %s", ID));
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
}
