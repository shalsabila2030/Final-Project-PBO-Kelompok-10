
package koperasi;

import java.util.ArrayList;
import javafx.beans.property.*;

public class Individu extends Nasabah{
    
    private LongProperty nik;
    private LongProperty npwp;

    public Individu(int ID, String name, String address, long nik, long npwp, ArrayList<Rekening> rekening) {
        super(ID, name, address, rekening);
        this.nik = new SimpleLongProperty(nik);
        this.npwp = new SimpleLongProperty(npwp);
    }

    public Individu(int ID, String name, String address, long nik, long npwp, Rekening rekening) {
        super(ID, name, address, rekening);
        this.nik = new SimpleLongProperty(nik);
        this.npwp = new SimpleLongProperty(npwp);
    }

    public long getNik() {
        return nik.get();
    }

    public void setNik(long nik) {
        this.nik.set(nik);
    }

    public long getNpwp() {
        return npwp.get();
    }

    public void setBirhtdate(long npwp) {
        this.npwp.set(npwp);
    }
    
    public LongProperty nikProperty(){
        return this.nik;
    }
    
    public LongProperty npwpProperty(){
        return this.npwp;
    }

    @Override
    public void print() {
        
    }
}
