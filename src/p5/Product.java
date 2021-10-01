package p5;

import P4.OvChipKaart;

import java.util.List;

public class Product {
    private int product_nummer;
    private String naam;
    private String beschrijving;
    private double prijs;
    private List<Integer> ovChipKaarts;



    public Product(int product_nummer,String naam,String beschrijving,double prijs){
        this.prijs=prijs;
        this.product_nummer=product_nummer;
        this.naam=naam;
        this.beschrijving=beschrijving;
    }
    public Product(int product_nummer, String naam, String beschrijving, double prijs, List<Integer> ovChipKaarts){
        this(product_nummer,naam,beschrijving,prijs);
        this.ovChipKaarts=ovChipKaarts;
    }



    public void addOVchipkaart(int kaartNummer){
        ovChipKaarts.add(kaartNummer);
    }


    public List<Integer> getOvChipKaarts() {
        return ovChipKaarts;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public String getNaam() {
        return naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }
}
