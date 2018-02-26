package sg.edu.nus.comp;

import javafx.application.Application;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static String DEFAULT_USER = "postgres";
    private static String DEFAULT_PASSWORD = "password";

    public static void main(String[] args) {
        // write your code here
        DataBase dataBase = new DataBase(DEFAULT_USER, DEFAULT_PASSWORD);
        try {
            dataBase.connect();
            //dataBase.executeQuery("insert into userinfo values (1, 'pass', NULL, '1234567' ,'1995-10-20');");
        } catch (ConnectionFailException e) {
            e.printStackTrace();
        }
        boolean isExit = false;

        do {
            System.out.println("Please input a command");
            Scanner sc = new Scanner(System.in);
            String query = sc.nextLine();
            query = query.trim();
            if(query.contains("exit")){
                isExit = true;
                continue;
            }
            while (!query.endsWith(";")){
                String input = sc.nextLine();
                query = query + " " + input.trim();
            }
            Optional<ResultSet> result = dataBase.executeQuery(query);
            if(result.isPresent()){

                try {
                    displayResultSet(result.get());
                } catch (SQLException e){
                    e.printStackTrace();
                }
                //Application app = new DataTable(result.get());

            }
        } while (isExit == false);

    }



    private static void displayResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        String[] header = new String[metaData.getColumnCount()];
        for(int i = 0; i < header.length; i++) {
            header[i] = metaData.getColumnLabel(i + 1);
            System.out.print(metaData.getColumnName(i + 1) + "\t|");
        }
        System.out.println("");

        while(rs.next()){
            for(int i = 0; i < header.length; i++) {
                Object obj = rs.getObject(header[i]);
                if (obj != null) {
                    System.out.print(obj.toString() + "\t|");
                } else {
                    System.out.print("\t|");
                }
                header[i].toString();
            }
            System.out.println("");
        }
    }

}
