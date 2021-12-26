
package database;

import java.sql.*;

public class dbHelper {
    Connection con = null;
    
    public Connection getConnection(){
        try{
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:src\\database\\Koperasi.db");
            System.out.println("Success!");
            
            return con;
        }catch(Exception e){
            System.out.println("Connection Failed : " + e);
            
            return null;
        }
    }
    
    public void createTable(){
        Connection con = getConnection();
        PreparedStatement state = null;
        
        String query;

        try{
            // Create table nasabah
            query = "CREATE TABLE IF NOT EXISTS nasabah (\n" +
                       "    id      INTEGER       PRIMARY KEY\n" +
                       "                          NOT NULL,\n" +
                       "    name    VARCHAR (256) NOT NULL,\n" +
                       "    address VARCHAR (256) NOT NULL\n" +
                       ");";
            
            state = con.prepareStatement(query);
            state.execute();
            
            // Create table individu
            query = "CREATE TABLE IF NOT EXISTS individu (\n" +
                    "    id                      PRIMARY KEY\n" +
                    "                            REFERENCES nasabah (id) ON DELETE RESTRICT\n" +
                    "                                                    ON UPDATE CASCADE,\n" +
                    "    nik  LONG NOT NULL,\n" +
                    "    npwp LONG NOT NULL\n" +
                    ");";
            
            state = con.prepareStatement(query);
            state.execute();
            
            // Create table perusahaan
            query = "CREATE TABLE IF NOT EXISTS perusahaan (\n" +
                    "    id                    PRIMARY KEY\n" +
                    "                          REFERENCES nasabah (id) ON DELETE RESTRICT\n" +
                    "                                                  ON UPDATE CASCADE,\n" +
                    "    nib VARCHAR (256) NOT NULL\n" +
                    ");";
            
            state = con.prepareStatement(query);
            state.execute();
            
            // Create table rekening
            query = "CREATE TABLE IF NOT EXISTS rekening (\n" +
                    "    no_rekening INTEGER PRIMARY KEY\n" +
                    "                        NOT NULL,\n" +
                    "    saldo       DOUBLE  NOT NULL,\n" +
                    "    id                  REFERENCES nasabah (id) ON DELETE RESTRICT\n" +
                    "                                                ON UPDATE CASCADE\n" +
                    ");";
            
            state = con.prepareStatement(query);
            state.execute();
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }
}
