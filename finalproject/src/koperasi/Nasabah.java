
package koperasi;

import java.util.*;
import javafx.beans.property.*;

public abstract class Nasabah {
    protected IntegerProperty ID;
    protected StringProperty name;
    protected StringProperty address;
    protected IntegerProperty numAccount;
    protected ArrayList<Rekening> rekening;

    public Nasabah(int ID, String name, String address, ArrayList<Rekening> rekening) {
        this.ID = new SimpleIntegerProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.rekening = rekening;
        this.numAccount = new SimpleIntegerProperty(this.rekening.size());
    }
    
    public Nasabah(int ID, String name, String address, Rekening rekening) {
        this.rekening = new ArrayList<>();
        this.ID = new SimpleIntegerProperty(ID);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.rekening.add(rekening);
        this.numAccount = new SimpleIntegerProperty(this.rekening.size());
    }
    
    public int getID() {
        return ID.get();
    }

    public void setID(int ID) {
        this.ID.set(ID);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public int getNumAccount() {
        return numAccount.get();
    }

    public ArrayList<Rekening> getRekening() {
        return rekening;
    }

    public void setRekening(ArrayList<Rekening> rekening) {
        this.rekening = rekening;
    }
    
    public IntegerProperty IDProperty(){
        return this.ID;
    }
    
    public StringProperty nameProperty(){
        return this.name;
    }
    
    public StringProperty addressProperty(){
        return this.address;
    }
    
    public IntegerProperty numAccountProperty(){
        return this.numAccount;
    }
    
    public abstract void print();
}