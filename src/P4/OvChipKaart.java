package P4;

import p2.Reiziger;

import java.sql.Date;

public class OvChipKaart {
    int kaartNummer;
    Date geldigTot;
    int klasse;
    double saldo;
    Reiziger reiziger;

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

}
