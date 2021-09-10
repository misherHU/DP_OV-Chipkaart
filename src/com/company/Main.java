package com.company;
import p3.Adres;

import java.sql.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        // write your code here
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;

        try {
            Class.forName("org.postgresql.Driver");
            myConn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "master123");
            myConn.setAutoCommit(false);


            // 2. Create a statement
            myStmt = myConn.createStatement();

            // 3. Execute SQL query
            myRs = myStmt.executeQuery("select * from reiziger");
            //List<Adres> tess= (List<Adres>)

            // 4. Process the result set
            System.out.println("Alle reizigers");
            int teller=1;
            while (myRs.next()) {
                System.out.println("#"+teller+": "+myRs.getString("voorletters") + ". " + myRs.getString("achternaam")+" ("+myRs.getDate("geboortedatum")+")");
            teller++;
            }



        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myRs != null) {
                myRs.close();
            }

            if (myStmt != null) {
                myStmt.close();
            }

            if (myConn != null) {
                myConn.close();
            }

        }
    }
}
