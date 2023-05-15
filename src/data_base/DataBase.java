package data_base;

import java.sql.*;

public class DataBase {
    Connection c;
    {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }
        try {
            c = DriverManager.getConnection("jdbc:sqLite:DataBaseForGame");
            c.close();
        } catch (SQLException ex) {
            System.out.println("Error: unable to load driver class!");
            System.exit(1);
        }

    }
}
