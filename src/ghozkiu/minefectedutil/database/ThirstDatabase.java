package ghozkiu.minefectedutil.database;

import ghozkiu.minefectedutil.thirst.PlayerThirst;
import ghozkiu.minefectedutil.thirst.Thirst;
import ghozkiu.minefectedutil.utilities.MessageManager;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.sql.*;
import java.util.HashMap;

public class ThirstDatabase {

    public static Connection newConnection() throws SQLException, ClassNotFoundException {

        //REGISTER JDBC DRIVER
        Class.forName("org.h2.Driver");
        //RETURN A CONNECTION
        return DriverManager.getConnection("jdbc:h2:./mfdatabase");
    }

    //Creates and loads the table
    public static void createLoadTable() {
        try {
            Connection connection = newConnection();
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PEOPLE"
                    + "(name VARCHAR(30) PRIMARY KEY,"
                    + "thirst INTEGER,"
                    + "lastSeen VARCHAR(35))";
            statement.executeUpdate(sql);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MessageManager.ConsoleInfo("database instance has been created");
    }

    //load players from database to playersT
    public static void loadPlayersT() throws SQLException, ClassNotFoundException {
        Connection connection = newConnection();
        String sql = "SELECT * FROM PEOPLE";
        ResultSet queryResult = connection.createStatement().executeQuery(sql);
        while (queryResult.next()) {
            DateTime lastSeen = new DateTime(queryResult.getString(3));
            DateTime currentDate = DateTime.now();
            Interval interval = new Interval(lastSeen, currentDate);
            System.out.println("lastSeen: " + lastSeen);
            System.out.println("currentDate: " + currentDate);
            System.out.println("interval: " + interval.toDuration().getStandardDays());

            //saves the query result in playersT hashmap: String name, int thirstLevel, date lastSeen
            if(interval.toDuration().getStandardDays() <= 4){
                Thirst.playersT.put(queryResult.getString(1),
                        new PlayerThirst(queryResult.getInt(2), lastSeen));
            }
        }
        connection.close();
    }

    public static void save() {
        try {
            Connection connection = newConnection();
            Statement statement = connection.createStatement();
            for (HashMap.Entry<String, PlayerThirst> entry : Thirst.playersT.entrySet()) {
                PlayerThirst playerT = entry.getValue();
                String name = entry.getKey();
                if(!isInserted(playerT, name)){
                    String sql =
                            String.format("UPDATE PEOPLE SET THIRST='%s',LASTSEEN='%s' WHERE NAME='%s'",
                                    playerT.getThirstLevel(), playerT.getLastSeen(), entry.getKey());
                    statement.executeUpdate(sql);
                }
            }
            connection.close();
            MessageManager.ConsoleGood("Database uploaded");
        } catch (Exception e) {
            e.printStackTrace();
            MessageManager.ConsoleBad("Error uploading database");
        }

    }

    public static void insert(String name, PlayerThirst playerT) {
        try {
            Connection connection = newConnection();
            String sql = String.format("INSERT INTO PEOPLE VALUES('%s', '%s', '%s')",
                    name, playerT.getThirstLevel(), playerT.getLastSeen());
            connection.createStatement().executeUpdate(sql);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isInserted(PlayerThirst playerT, String name){
        try{
            Connection connection = newConnection();
            String sql = String.format("INSERT INTO PEOPLE VALUES('%s', '%s', '%s')",
                    name, playerT.getThirstLevel(), DateTime.now());
            connection.createStatement().executeUpdate(sql);
            connection.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
