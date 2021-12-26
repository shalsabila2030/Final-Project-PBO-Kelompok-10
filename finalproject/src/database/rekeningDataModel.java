
package database;

import java.sql.*;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import koperasi.*;

public class rekeningDataModel {
    public final Connection con;

    public rekeningDataModel() {
        dbHelper db = new dbHelper();
        
        con = db.getConnection();
    }
    
    public void addNasabah(Individu nasabah){
        PreparedStatement state = null;
        String queryNasabah, queryIndividu, queryPerusahaan, queryRekening;
        
        try{
            queryNasabah = "INSERT INTO nasabah (id, name, address) "
                         + "VALUES (?,?,?);";
            queryIndividu = "INSERT INTO individu (id, nik, npwp) "
                          + "VALUES (?,?,?);";
            queryRekening = "INSERT INTO rekening (no_rekening, saldo, id) "
                          + "VALUES (?,?,?);";

            state = con.prepareStatement(queryNasabah);
            state.setInt(1, nasabah.getID());
            state.setString(2, nasabah.getName());
            state.setString(3, nasabah.getAddress());
            state.execute();
            
            state = con.prepareStatement(queryIndividu);
            state.setInt(1, nasabah.getID());
            state.setLong(2, nasabah.getNik());
            state.setLong(3, nasabah.getNpwp());
            state.execute();
            
            state = con.prepareStatement(queryRekening);
            state.setInt(1, nasabah.getRekening().get(0).getNoRekening());
            state.setDouble(2, nasabah.getRekening().get(0).getSaldo());
            state.setInt(3, nasabah.getID());
            state.execute();
            
            System.out.println("Berhasil Ditambahkan!");
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    public void addNasabah(Perusahaan nasabah){
        PreparedStatement state = null;
        String queryNasabah, queryIndividu, queryPerusahaan, queryRekening;
        
        try{
            queryNasabah = "INSERT INTO nasabah (id, name, address) "
                         + "VALUES (?,?,?);";
            queryPerusahaan = "INSERT INTO perusahaan (id, nib) "
                          + "VALUES (?,?);";
            queryRekening = "INSERT INTO rekening (no_rekening, saldo, id) "
                          + "VALUES (?,?,?);";

            state = con.prepareStatement(queryNasabah);
            state.setInt(1, nasabah.getID());
            state.setString(2, nasabah.getName());
            state.setString(3, nasabah.getAddress());
            state.execute();
            
            state = con.prepareStatement(queryPerusahaan);
            state.setInt(1, nasabah.getID());
            state.setString(2, nasabah.getNib());
            state.execute();
            
            state = con.prepareStatement(queryRekening);
            state.setInt(1, nasabah.getRekening().get(0).getNoRekening());
            state.setDouble(2, nasabah.getRekening().get(0).getSaldo());
            state.setInt(3, nasabah.getID());
            state.execute();
            
            System.out.println("Berhasil Ditambahkan!");
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    public void addRekening(int id, Rekening rekening){
        String queryRekening = "INSERT INTO rekening (no_rekening, saldo, id) "
                             + "VALUES (?,?,?);";
        PreparedStatement state = null;
        
        try{
            state = con.prepareStatement(queryRekening);
            state.setInt(1, rekening.getNoRekening());
            state.setDouble(2, rekening.getSaldo());
            state.setInt(3, id);
            state.execute();
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    public ObservableList<Individu> getIndividu(){
        ObservableList<Individu> data = FXCollections.observableArrayList();
        String sql, sqlAccount;
        
        try{
            sql = "SELECT id,name,address,nik,npwp FROM "
                + "nasabah NATURAL JOIN individu ORDER BY name";
            
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while(rs.next()){
                sqlAccount = "SELECT no_rekening,saldo,id FROM rekening "
                           + "WHERE id = " + rs.getInt(1);
                ResultSet rsAccount = con.createStatement().executeQuery(sqlAccount);
                
                ArrayList<Rekening> dataAccount = new ArrayList<>();
                
                while(rsAccount.next()){
                    dataAccount.add(new Rekening(rsAccount.getInt(1),rsAccount.getDouble(2)));
                }
                
                data.add(new Individu(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getLong(4),rs.getLong(5),dataAccount));
            }
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
        
        return data;
    }
    
    public ObservableList<Perusahaan> getPerusahaan(){
        ObservableList<Perusahaan> data = FXCollections.observableArrayList();
        String sql, sqlAccount;
        
        try{
            sql = "SELECT id,name,address,nib FROM "
                + "nasabah NATURAL JOIN perusahaan ORDER BY name";
            
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while(rs.next()){
                sqlAccount = "SELECT no_rekening,saldo FROM rekening "
                           + "WHERE id = " + rs.getInt(1);
                ResultSet rsAccount = con.createStatement().executeQuery(sqlAccount);
                
                ArrayList<Rekening> dataAccount = new ArrayList<>();
                
                while(rsAccount.next()){
                    dataAccount.add(new Rekening(rsAccount.getInt(1),rsAccount.getDouble(2)));
                }
                
                data.add(new Perusahaan(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),dataAccount));
            }
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
        
        return data;
    }
    
    public ObservableList<Rekening> getRekening(int id){
        ObservableList<Rekening> data = FXCollections.observableArrayList();
        
        try{
            String sql = "SELECT no_rekening,saldo "
                       + "FROM rekening WHERE id = " + id;
            
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while(rs.next()){
                data.add(new Rekening(rs.getInt(1),rs.getDouble(2)));
            }
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
        
        return data;
    }
    
    public Double getSaldoRekening(int noRekening){
        String sql = "SELECT saldo FROM rekening WHERE no_rekening=" + noRekening;
        Double saldo;
        
        try{            
            ResultSet rs = con.prepareStatement(sql).executeQuery();
            saldo = rs.getDouble(1);
        }catch(Exception e){
            System.out.println("Error : " + e);
            saldo = 0d;
        }
        
        return saldo;
    }
    
    public int nextId(){
        String sql = "SELECT MAX(id) FROM nasabah";
        int maxValue = 0;
        
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while(rs.next()){
                maxValue = rs.getInt(1);
            }
            
            return maxValue == 0 ? 1000001 : maxValue + 1;
        }catch(Exception e) {
            System.out.println("Error : " + e);
        }
        
        return 0;
    }
    
    public int nextNoRekening(int id){
        String sql = "SELECT MAX(no_rekening) FROM rekening WHERE id = " + id;
        int maxValue = 0;
        
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            
            while(rs.next()){
                maxValue = rs.getInt(1);
            }
            
            return maxValue == 0 ? 101 : maxValue + 1;
        }catch(Exception e) {
            System.out.println("Error : " + e);
        }
        
        return 0;
    }
}