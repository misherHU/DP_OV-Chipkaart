package P4;

import p2.Reiziger;
import p5.Product;

import java.sql.Date;
import java.util.List;

public class OvChipKaart {
    int kaartNummer;
    Date geldigTot;
    int klasse;
    double saldo;
    Reiziger reiziger;
    private List<Integer> products;


    public OvChipKaart(int kaartNummer,Date geldigTot,int klasse,double saldo){
        this.kaartNummer=kaartNummer;
        this.geldigTot=geldigTot;
        this.klasse=klasse;
        this.saldo=saldo;




    }



    public OvChipKaart(int kaartNummer,Date geldigTot,int klasse,double saldo,Reiziger reiziger){
        this.kaartNummer=kaartNummer;
        this.geldigTot=geldigTot;
        this.klasse=klasse;
        this.saldo=saldo;
        this.reiziger=reiziger;



    }




    public Reiziger getReiziger() {
        return reiziger;
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Integer>getProducts() {
        return products;
    }

    public void setProducts(List<Integer> products) {
        this.products = products;
    }

    public void addProduct(int productNummer){
        products.add(productNummer);
    }

}
