package comp3350.group6.promise.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

import comp3350.group6.promise.application.Main;


public class DBConnectorUtil {

    /**
     * create local database
     */
    public static void initialLocalDB() {
        File app_db = new File("src/main/java/comp3350/group6/promise/app_db");
        Main.setDBPath("src/main/java/comp3350/group6/promise/app_db");
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!app_db.exists()) {
            try {
                Connection conn = getConnection();
                assert conn != null;
                Statement state = conn.createStatement();
                BufferedReader bf = new BufferedReader(new FileReader("src/main/assets/localDB/PROMISE.script"));
                String line;
                while ((line = bf.readLine()) != null) {
                    state.execute(line);
                }
                bf.close();
                state.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * clean local DB
     */
    public static void cleanLocalDB() {
        File app_db_script = new File("src/main/java/comp3350/group6/promise/app_db.script");
        File app_db_properties = new File("src/main/java/comp3350/group6/promise/app_db.properties");

        if (app_db_script.exists() && app_db_script.delete()) {
            Log.i("anchor", "clear app_db.script completed");
        }

        if (app_db_properties.exists() && app_db_properties.delete()) {
            Log.i("anchor", "clear app_db.properties completed");
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:hsqldb:file:" + Main.getDBPath() + ";shutdown=true", "SA", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}



