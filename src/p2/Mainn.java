package p2;

import p3.Adres;
import p3.AdresDAO;
import p3.AdresDAOPsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Mainn {
    static Connection connection;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Mainn.GetConnection();
        //testReizigerDAO(new ReizigerDAOPsql(connection));
        testAdresDAO(new AdresDAOPsql(connection));
    }


     public static void GetConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "master123");



    }

    private void closeConnection() throws SQLException {
        connection.close();
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");

        rdao.save(sietske);
        System.out.println("na save");
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        System.out.println("reizigers met geboorte datum 1981-03-14");
        for (Reiziger reiziger:rdao.findByGbDatum("1981-03-14")) {
            System.out.println(reiziger.toString());


        }
        System.out.println("reiziger met id 1= "+rdao.findById(1));

        System.out.println("aantaal voor delete:"+reizigers.size());
        rdao.delete(sietske);
        System.out.println("aantaal na delete:"+reizigers.size());

        rdao.save(sietske);

        System.out.println("sietskte voor update "+sietske.toString());
        rdao.update(sietske);
        System.out.println("sietskte na update "+rdao.findById(77));






    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        List<Adres> adressen=adao.findAll();
        System.out.println(adressen.size()+" adressen");
        for (Adres a:adressen){
            System.out.println(a.toString());
        }
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(2, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.println("adres van sietske= "+adao.findByReiziger(sietske));
        Adres a1=new Adres(6,"nijmegen", "22", "7890nl", "nostraat");
        a1.setReiziger_id(77);
        adao.save(a1);

        adressen=adao.findAll();
        System.out.println(adressen.size()+" adressen na save");

        adao.delete(a1);

        adressen=adao.findAll();

        System.out.println(adressen.size()+" adressen na delete");

        adao.save(a1);

        System.out.println("a1 voor update "+a1.toString());

        a1.setPostcode("1111MO");
        adao.update(a1);
        System.out.println("a1 na update "+a1.toString());

    }
}
