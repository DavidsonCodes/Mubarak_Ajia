package Project1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class DevelopersImpl implements Developers {

    static File project = new File("C:\\Users\\user\\Desktop\\IdeaProjects\\untitled1\\src\\Project1\\project.txt");

    Connection conn = null;
    private static void ifFileExist(){
        //Check if file exist
        if (!project.exists()){
            System.out.println("File does not exist");
        }
    }

    private void createTableDevelopers(){
        try(Statement statement = conn.createStatement()){
            String createTable = "CREATE TABLE IF NOT EXISTS developers(id Integer not null auto_increment, name Text, age Integer, location Text, skill Text, PRIMARY KEY(id))";

            statement.execute(createTable);
        }catch (SQLException e){
            System.out.printf("Unable to connect to Database.... check your credentials thoroughly,  %s", e.getMessage());
        }
    }

    private Connection connectToDataBase() throws SQLException {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/developer", "root", "legendre");
        return conn;
    }
    private void closeDataBase(){
        try {
            if(conn != null){
                conn.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }


   public void readFileToDB(){

       ifFileExist();

       try(BufferedReader bufferedReader = new BufferedReader(new FileReader(project, StandardCharsets.UTF_8));){

           Connection connection = connectToDataBase();
                createTableDevelopers();

           char ignoreCharacter = '#';
           String line;
           while ((line = bufferedReader.readLine()) != null){
               line = line.substring(0, line.lastIndexOf('#'));
               String[] value = line.split(",");

               String name = value[0].trim();
               String age = value[1].trim();
               String location = value[2].trim();
               String skill = value[3].trim();

               String insertDeveloper = "INSERT INTO developers(name, age, location, skill) VALUES(?, ?, ?, ?)";
               try (PreparedStatement statements = connection.prepareStatement(insertDeveloper)){
                   statements.setString(1, name);
                   statements.setString(2, age);
                   statements.setString(3, location);
                   statements.setString(4, skill);
                   statements.executeUpdate();
               }catch (SQLException e){
                   System.out.printf("Something went wrong, %s", e.getMessage());
               }
           }
           closeDataBase();

       }catch (IOException | SQLException e){
           System.out.printf("Could not read from file to database, %s", e.getMessage());
       }
       System.out.println("Developers populated into database successfully...");
       System.out.println();
       }


    @Override
    public ResultSet loadDevelopers() {
        ResultSet resultSet = null;
        Connection connection;

            try{
                String selectAllQuery = "SELECT * FROM developers";

                connection = connectToDataBase();
                PreparedStatement statement1 = connection.prepareStatement(selectAllQuery);

                resultSet = statement1.executeQuery();

                System.out.println("ID, \tName, \tAge, \tLocation, \tSkill");
                while( resultSet.next() ){
                    int id = resultSet.getInt(1);
                    String name = resultSet.getString(2);
                    String age = resultSet.getString(3);
                    String location = resultSet.getString(4);
                    String skill = resultSet.getString(5);

                    System.out.printf("%d \t%s \t%s \t%s \t%s \n", id, name, age, location, skill);
                }
                closeDataBase();
                statement1.close();
                resultSet.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        return resultSet;
    }

    public static void main(String[] args) {
        DevelopersImpl developers = new DevelopersImpl();
        developers.readFileToDB();
        developers.loadDevelopers();
    }
}