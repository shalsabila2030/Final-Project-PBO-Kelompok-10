
package finalproject;

import database.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import koperasi.*;

public class formNasabahController implements Initializable {
    
    @FXML
    private TableView<koperasi.Individu> tabelNasabah;
    @FXML
    private TableColumn<koperasi.Individu, Integer> kolomID;
    @FXML
    private TableColumn<koperasi.Individu, String> kolomNama;
    @FXML
    private TableColumn<koperasi.Individu, String> kolomAlamat;
    @FXML
    private TableColumn<koperasi.Individu, Long> kolomNIK;
    @FXML
    private TableColumn<koperasi.Individu, Long> kolomNPWP;
    @FXML
    private TableColumn<koperasi.Individu, Integer> kolomJumlah;
    @FXML
    private TableView<koperasi.Rekening> tabelRekening;
    @FXML
    private TableColumn<koperasi.Rekening, Integer> kolomNoRek;
    @FXML
    private TableColumn<koperasi.Rekening, Double> kolomSaldo;
    @FXML
    private TextField textID;
    @FXML
    private TextField textNama;
    @FXML
    private TextField textAlamat;
    @FXML
    private TextField textNIK;
    @FXML
    private TextField textNPWP;
    @FXML
    private TextField textNoRekening;
    @FXML
    private TextField textSaldo;
    @FXML
    private Button txtSave;
    @FXML
    private Button txtRefresh;
    @FXML
    private Button txtClear;
    @FXML
    private TextField textIDBaru;
    @FXML
    private TextField textNoRekeningBaru;
    @FXML
    private TextField textSaldoBaru;
    @FXML
    private Button btnTambahRekening;
    @FXML
    private Label labelDbStatus;
    @FXML
    private Label labelSaveStatus;
    @FXML
    private TextField textIDPerusahaan;
    @FXML
    private TextField textNamaPerusahaan;
    @FXML
    private TextField textAlamatPerusahaan;
    @FXML
    private TextField textNIB;
    @FXML
    private TextField textNoRekeningPerusahaan;
    @FXML
    private TextField textSaldoPerusahaan;
    @FXML
    private Button txtSavePerusahaan;
    @FXML
    private Button txtRefreshPerusahaan;
    @FXML
    private Button txtClearPerusahaan;
    @FXML
    private Label labelSaveStatusPerusahaan;
    @FXML
    private TableView<koperasi.Perusahaan> tabelNasabahPerusahaan;
    @FXML
    private TableColumn<koperasi.Perusahaan, Integer> kolomIDPerusahaan;
    @FXML
    private TableColumn<koperasi.Perusahaan, String> kolomNamaPerusahaan;
    @FXML
    private TableColumn<koperasi.Perusahaan, String> kolomAlamatPerusahaan;
    @FXML
    private TableColumn<koperasi.Perusahaan, String> kolomNIB;
    @FXML
    private TableColumn<koperasi.Perusahaan, Integer> kolomJumlahPerusahaan;
    @FXML
    private TableView<koperasi.Rekening> tabelRekeningPerusahaan;
    @FXML
    private TableColumn<koperasi.Rekening, Integer> kolomNoRekPerusahaan;
    @FXML
    private TableColumn<koperasi.Rekening, Double> kolomSaldoPerusahaan;
    @FXML
    private TextField textIDBaruPerusahaan;
    @FXML
    private TextField textNoRekeningBaruPerusahaan;
    @FXML
    private TextField textSaldoBaruPerusahaan;
    @FXML
    private Button btnTambahRekeningPerusahaan;
    
    rekeningDataModel datamodel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            datamodel = new rekeningDataModel();
            labelDbStatus.setText(datamodel.con == null ? "Terputus" : "Tersambung");
            txtClear.fire();
            txtClearPerusahaan.fire();
            txtRefresh.fire();
            txtRefreshPerusahaan.fire();
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
        
        tabelNasabah.getSelectionModel().selectedIndexProperty().addListener(listener->{
            if(tabelNasabah.getSelectionModel().getSelectedItem() != null){
                koperasi.Individu akun = tabelNasabah.getSelectionModel().getSelectedItem();
                viewRekening(akun.getID());
                
                btnTambahRekening.setDisable(false);
                try{
                    textIDBaru.setText("" + akun.getID());
                    textNoRekeningBaru.setText(Integer.toString(datamodel.nextNoRekening(akun.getID())));
                }catch(Exception e){
                    System.out.println("Error : " + e);
                }
            }
        });
        
        tabelNasabahPerusahaan.getSelectionModel().selectedIndexProperty().addListener(listener->{
            if(tabelNasabahPerusahaan.getSelectionModel().getSelectedItem() != null){
                koperasi.Perusahaan akun = tabelNasabahPerusahaan.getSelectionModel().getSelectedItem();
                viewRekeningPerusahaan(akun.getID());
                
                btnTambahRekeningPerusahaan.setDisable(false);
                try{
                    textIDBaruPerusahaan.setText("" + akun.getID());
                    textNoRekeningBaruPerusahaan.setText(Integer.toString(datamodel.nextNoRekening(akun.getID())));
                }catch(Exception e){
                    System.out.println("Error : " + e);
                }
            }
        });
    }    

    @FXML
    private void handleAddNasabah(ActionEvent event) {
        koperasi.Rekening rekeningBaru = new koperasi.Rekening(
                                             Integer.parseInt(textNoRekening.getText()),
                                             Double.parseDouble(textSaldo.getText())
                                            );
        koperasi.Individu nasabahBaru = new koperasi.Individu(
                                            Integer.parseInt(textID.getText()),
                                            textNama.getText(),
                                            textAlamat.getText(),
                                            Long.parseLong(textNIK.getText()),
                                            Long.parseLong(textNPWP.getText()),
                                            rekeningBaru
                                           );
        
        try{
            datamodel.addNasabah(nasabahBaru);
            labelSaveStatus.setText("Data Berhasil Ditambahkan!");
            txtRefresh.fire();
            txtClear.fire();
        }catch(Exception e){
            System.out.println("Error : " + e);
            labelSaveStatus.setText("Data Gagal Ditambahkan!");
        }
    }

    @FXML
    private void handleRefreshNasabah(ActionEvent event) {
        try{
            ObservableList<koperasi.Individu> data = datamodel.getIndividu();
            kolomID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            kolomNama.setCellValueFactory(new PropertyValueFactory<>("name"));
            kolomAlamat.setCellValueFactory(new PropertyValueFactory<>("address"));
            kolomNIK.setCellValueFactory(new PropertyValueFactory<>("nik"));
            kolomNPWP.setCellValueFactory(new PropertyValueFactory<>("npwp"));
            kolomJumlah.setCellValueFactory(new PropertyValueFactory<>("numAccount"));
            
            tabelNasabah.setItems(null);
            tabelNasabah.setItems(data);
            
            btnTambahRekening.setDisable(true);
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }

    @FXML
    private void handleClearData(ActionEvent event) {
        try{
            textID.setText("" + datamodel.nextId());
            textNoRekening.setText(textID.getText() + datamodel.nextNoRekening(0));
            textNama.setText("");
            textAlamat.setText("");
            textNIK.setText("");
            textNPWP.setText("");
            textSaldo.setText("");
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }

    @FXML
    private void handleAddRekening(ActionEvent event) {
        try{
            datamodel.addRekening(Integer.parseInt(textIDBaru.getText()), new koperasi.Rekening(Integer.parseInt(textNoRekeningBaru.getText()), Double.parseDouble(textSaldoBaru.getText())));
            viewRekening(Integer.parseInt(textIDBaru.getText()));
            textNoRekeningBaru.setText(Integer.toString(datamodel.nextNoRekening(Integer.parseInt(textIDBaru.getText()))));
            textSaldoBaru.setText("");
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    public void viewRekening(int id){
        try{
            ObservableList<koperasi.Rekening> data = datamodel.getRekening(id);
            kolomNoRek.setCellValueFactory(new PropertyValueFactory<>("noRekening"));
            kolomSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
            tabelRekening.setItems(null);
            tabelRekening.setItems(data);
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }

    @FXML
    private void handleAddNasabahPerusahaan(ActionEvent event) {
        koperasi.Rekening rekeningBaru = new koperasi.Rekening(
                                             Integer.parseInt(textNoRekeningPerusahaan.getText()),
                                             Double.parseDouble(textSaldoPerusahaan.getText())
                                            );
        koperasi.Perusahaan nasabahBaru = new koperasi.Perusahaan(
                                            Integer.parseInt(textIDPerusahaan.getText()),
                                            textNamaPerusahaan.getText(),
                                            textAlamatPerusahaan.getText(),
                                            textNIB.getText(),
                                            rekeningBaru
                                           );
        
        try{
            datamodel.addNasabah(nasabahBaru);
            labelSaveStatusPerusahaan.setText("Data Berhasil Ditambahkan!");
            txtRefreshPerusahaan.fire();
            txtClearPerusahaan.fire();
        }catch(Exception e){
            System.out.println("Error : " + e);
            labelSaveStatusPerusahaan.setText("Data Gagal Ditambahkan!");
        }
    }

    @FXML
    private void handleRefreshNasabahPerusahaan(ActionEvent event) {
        try{
            ObservableList<koperasi.Perusahaan> data = datamodel.getPerusahaan();
            kolomIDPerusahaan.setCellValueFactory(new PropertyValueFactory<>("ID"));
            kolomNamaPerusahaan.setCellValueFactory(new PropertyValueFactory<>("name"));
            kolomAlamatPerusahaan.setCellValueFactory(new PropertyValueFactory<>("address"));
            kolomNIB.setCellValueFactory(new PropertyValueFactory<>("nib"));
            kolomJumlahPerusahaan.setCellValueFactory(new PropertyValueFactory<>("numAccount"));
            
            tabelNasabahPerusahaan.setItems(null);
            tabelNasabahPerusahaan.setItems(data);
            
            btnTambahRekeningPerusahaan.setDisable(true);
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }

    @FXML
    private void handleClearDataPerusahaan(ActionEvent event) {
        try{
            textIDPerusahaan.setText("" + datamodel.nextId());
            textNoRekeningPerusahaan.setText(textID.getText() + datamodel.nextNoRekening(0));
            textNamaPerusahaan.setText("");
            textAlamatPerusahaan.setText("");
            textNIB.setText("");
            textSaldoPerusahaan.setText("");
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }

    @FXML
    private void handleAddRekeningPerusahaan(ActionEvent event) {
        try{
            datamodel.addRekening(Integer.parseInt(textIDBaruPerusahaan.getText()), new koperasi.Rekening(Integer.parseInt(textNoRekeningBaruPerusahaan.getText()), Double.parseDouble(textSaldoBaruPerusahaan.getText())));
            viewRekeningPerusahaan(Integer.parseInt(textIDBaruPerusahaan.getText()));
            textNoRekeningBaruPerusahaan.setText(Integer.toString(datamodel.nextNoRekening(Integer.parseInt(textIDBaruPerusahaan.getText()))));
            textSaldoBaruPerusahaan.setText("");
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    public void viewRekeningPerusahaan(int id){
        try{
            ObservableList<koperasi.Rekening> data = datamodel.getRekening(id);
            kolomNoRekPerusahaan.setCellValueFactory(new PropertyValueFactory<>("noRekening"));
            kolomSaldoPerusahaan.setCellValueFactory(new PropertyValueFactory<>("saldo"));
            tabelRekeningPerusahaan.setItems(null);
            tabelRekeningPerusahaan.setItems(data);
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    public void tambahSaldo(){
        String noRekening = JOptionPane.showInputDialog("No Rekening");
        String nominal = JOptionPane.showInputDialog("Nominal");
        
        Double base = datamodel.getSaldoRekening(Integer.parseInt(noRekening));
        
        try{
            String sql = "UPDATE rekening SET saldo = " + (base + Double.parseDouble(nominal)) + " WHERE no_rekening = " + noRekening;
            
            datamodel.con.prepareStatement(sql).execute();
            viewRekeningPerusahaan(Integer.parseInt(textIDPerusahaan.getText()));
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }
    
    public void tarikSaldo(){
        String noRekening = JOptionPane.showInputDialog("No Rekening");
        String nominal = JOptionPane.showInputDialog("Nominal");
        
        Double base = datamodel.getSaldoRekening(Integer.parseInt(noRekening));
        
        try{
            String sql = "UPDATE rekening SET saldo = " + (base - Double.parseDouble(nominal)) + " WHERE no_rekening = " + noRekening;
            
            datamodel.con.prepareStatement(sql).execute();
            viewRekeningPerusahaan(Integer.parseInt(textIDPerusahaan.getText()));
        }catch(Exception e){
            System.out.println("Error : " + e);
        }
    }
}