package p3;

import p2.Reiziger;

public class Adres {
private int id;
private String straat;
private String postcode;
private String woonplaats;
private String huisnummer;
private int reiziger_id;
private Reiziger reiziger;

public Adres(){}
public Adres(int id,String woonplaats,String huisnummer,String postcode,String straat,int reiziger_id){
    this.id=id;
    this.woonplaats=woonplaats;
    this.huisnummer=huisnummer;
    this.postcode=postcode;
    this.straat=straat;
    this.reiziger_id=reiziger_id;

}

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "id=" + id +
                ", straat='" + straat + '\'' +
                ", postcode='" + postcode + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                ", huisnummer='" + huisnummer + '\'' +
                ", reiziger_id=" + reiziger_id +
                '}';
    }
}
