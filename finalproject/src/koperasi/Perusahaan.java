
package koperasi;

import java.util.ArrayList;
import javafx.beans.property.*;

public class Perusahaan extends Nasabah{
    private StringProperty nib;

    public Perusahaan(int ID, String name, String address, String nib, ArrayList<Rekening> rekening) {
        super(ID, name, address, rekening);
        this.nib = new SimpleStringProperty(nib);
    }

    public Perusahaan(int ID, String name, String address, String nib, Rekening rekening) {
        super(ID, name, address, rekening);
        this.nib = new SimpleStringProperty(nib);
    }
    
    public String getNib() {
        return nib.get();
    }

    public void setNib(String nib) {
        this.nib.set(nib);
    }
    
    public StringProperty nibProperty(){
        return this.nib;
    }
    
    @Override
    public void print() {
        
    }
}