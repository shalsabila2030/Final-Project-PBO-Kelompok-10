
package koperasi;

import javafx.beans.property.*;

public class Rekening {
    private IntegerProperty noRekening;
    private DoubleProperty saldo;

    public Rekening(int noRekening, double saldo) {
        this.noRekening = new SimpleIntegerProperty(noRekening);
        this.saldo = new SimpleDoubleProperty(saldo);
    }
    
    public int getNoRekening() {
        return noRekening.get();
    }

    public void setNoRekening(int noRekening) {
        this.noRekening.set(noRekening);
    }
    
    public double getSaldo() {
        return saldo.get();
    }

    public void setSaldo(double saldo) {
        this.saldo.set(saldo);
    }
    
    public void tambahSaldo(double jumlah){
        this.saldo.set(this.getSaldo() + jumlah);
    }
    
    public void tarikTunai(double jumlah){
        this.saldo.set(this.getSaldo() - jumlah);
    }
    
    public IntegerProperty noRekeningProperty(){
        return this.noRekening;
    }
    
    public DoubleProperty saldoProperty(){
        return this.saldo;
    }
}
