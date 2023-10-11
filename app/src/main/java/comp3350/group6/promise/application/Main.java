package comp3350.group6.promise.application;

public class Main {
    private static String dbName="PROMISE";

    public static void setDBPath(final String name) {
        try {
            Class.forName("org.hsqldb.jdbcDriver").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        dbName = name;
    }
    public static String getDBPath() {
        return dbName;
    }
}
