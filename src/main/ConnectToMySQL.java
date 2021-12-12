package main;

import java.sql.*;
import java.util.HashMap;
import java.util.UUID;

/**
 * ConnectToMySQL uses jdbc driver to establish connection with mysql. At the end of this page the commands for creating
 * table using SQL is provided.
 *
 */
public class ConnectToMySQL {
    public static void readFeedback() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", "", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM computer_feedback");
            while (resultSet.next()) {
                String userData = resultSet.getString("id") +  "\t" + resultSet.getString("userName") + "\t" + resultSet.getString("correctNumberLocation") + "\t" + resultSet.getString("correctNumberOnly") + "\t" + resultSet.getString("incorrectGuess")+ "." ;
                System.out.println(userData);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void writeFeedbackIntoDB(HashMap<String, Integer> userFeedbackObj, String userName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", "menhajsharaf", "helloworld.com");
            Statement statement = connection.createStatement();
            String sqlCommand = "INSERT INTO computer_feedback (userName, correctNumberLocation, correctNumberOnly, incorrectGuess)" + "VALUES ('" + userName + "'" + "," +"'"+ userFeedbackObj.get("correctNumberLocation") +"'" +"," +"'"+ userFeedbackObj.get("correctNumberOnly") +"'"+ "," + userFeedbackObj.get("incorrectGuess") + ")";
            ResultSet resultSet = statement.executeQuery("SELECT * FROM computer_feedback");
            //statement.executeQuery(sqlCommand);
            statement.executeUpdate(sqlCommand);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeInputHistoryIntoDB(String userNameValue, String resultValue) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", "menhajsharaf", "helloworld.com");
            Statement statement = connection.createStatement();
            String sqlCommand = "INSERT INTO user_input_history (userName, userInput)" + "VALUES ('"+ userNameValue +"'"+ "," + resultValue + ")";
            statement.executeUpdate(sqlCommand);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void readUserInputHistoryFromDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", "menhajsharaf", "helloworld.com");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_input_history");
            while (resultSet.next()) {
                String userData = resultSet.getString("id") +  "\t" + resultSet.getString("userName") + "\t" + resultSet.getString("userInput") + "." ;
                System.out.println(userData);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteDataFromComputerFeedback() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", "menhajsharaf", "helloworld.com");
            Statement statement = connection.createStatement();
            String sqlCommand = "DELETE FROM computer_feedback";
            statement.executeUpdate(sqlCommand);
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFeedbackGuessToDB(String userNameValue, String feedbackValue, String guessValue) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", "menhajsharaf", "helloworld.com");
            Statement statement = connection.createStatement();
            String sqlCommand = "INSERT INTO user_name_feedback_guess (userName, feedback, guess)" + "VALUES ('"+ userNameValue +"'"+ ",'" + feedbackValue + "'" + ",'" + guessValue + "')";
            statement.executeUpdate(sqlCommand);
            System.out.println("successfully saved!");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFeedbackGuessFromDB() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mastermind", "menhajsharaf", "helloworld.com");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user_name_feedback_guess");
            while (resultSet.next()) {
                String userData = resultSet.getString("id") +  "\t" + resultSet.getString("userName") + "\t" + resultSet.getString("feedback") + "\t" + resultSet.getString("guess") + "." ;
                System.out.println(userData);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ConnectToMySQL only works if the tables exist.
     */
    public static void commentedSQLCommands() {
        System.out.println("This is method includes the SQL commands and commented.");
        /*
        CREATE database mastermind;
        use mastermind;
        CREATE TABLE computer_feedback(
                id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                userName VARCHAR(30) NULL,
                correctNumberLocation INT NULL,
                correctNumberOnly INT NULL,
                incorrectGuess INT NULL
        );
        CREATE TABLE user_input_history(
                id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                userName VARCHAR(30) NULL,
                userInput VARCHAR(250) NULL
        );

        CREATE TABLE user_name_feedback_guess(
                id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
                userName VARCHAR(30) NULL,
                feedback VARCHAR(250) NULL,
                guess VARCHAR(250) NULL
        );
        show tables;
        desc user_name_feedback_guess;
        drop table table_name;
        */
    }
}
