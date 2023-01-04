package sample;

import sample.APIKeys;

import java.sql.*;

abstract public class DAO {

    protected static String DBUrl = String.format("jdbc:oracle:thin:%s/%s@%s", APIKeys.DBuser, APIKeys.DBPass, APIKeys.DBName);
    protected static Connection conn = null;

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

    protected static String nullFormat(String text) {
        if (text == null) {
            return null;
        } else {
            return "'" + text + "'";
        }
    }

    protected static Long nullFormat(long num) {
        return num == 0 ? null : num;
    }
}
