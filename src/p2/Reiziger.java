package p2;

import p3.Adres;

import java.sql.Date;

public class Reiziger {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboorteDatum;
    private Adres adres;

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboorteDatum){
        this.reiziger_id=reiziger_id;
        this.voorletters=voorletters;
        this.tussenvoegsel=tussenvoegsel;
        this.achternaam=achternaam;
        this.geboorteDatum=geboorteDatum;



    }
    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboorteDatum,Adres adres){
        this.reiziger_id=reiziger_id;
        this.voorletters=voorletters;
        this.tussenvoegsel=tussenvoegsel;
        this.achternaam=achternaam;
        this.geboorteDatum=geboorteDatum;
        this.adres=adres;



    }


    public Reiziger(int reiziger_id, String voorletters, String achternaam, Date geboorteDatum){
        this.reiziger_id=reiziger_id;
        this.voorletters=voorletters;
        this.achternaam=achternaam;
        this.geboorteDatum=geboorteDatum;
    }
    public Reiziger(int reiziger_id, String voorletters, String achternaam, Date geboorteDatum,Adres adres){
        this.reiziger_id=reiziger_id;
        this.voorletters=voorletters;
        this.achternaam=achternaam;
        this.geboorteDatum=geboorteDatum;
        this.adres=adres;
    }


    public Adres getAdres() {
        return adres;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public Date getGeboorteDatum() {
        return geboorteDatum;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getNaam(){
        String beschikbaarTussenvoegsel= "";
        if(tussenvoegsel!=null){
           beschikbaarTussenvoegsel=tussenvoegsel;
        }
        return voorletters+" "+beschikbaarTussenvoegsel+" "+achternaam;
    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "reiziger_id=" + reiziger_id +
                getNaam()+
                ", geboorteDatum='" + geboorteDatum + '\'' +
                "}\n"+adres.toString();
    }
}
