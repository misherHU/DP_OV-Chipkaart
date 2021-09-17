package p3;

import p2.Reiziger;
import p2.ReizigerDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{

    public Connection myConn;
    public ReizigerDAOPsql reizigerDAOPsql;


    public AdresDAOPsql(Connection myConn){
        this.myConn=myConn;
    }

    public AdresDAOPsql(Connection myConn, ReizigerDAOPsql reizigerDAOPsql){
        this.myConn=myConn;
        this.reizigerDAOPsql=reizigerDAOPsql;
    }

    public void setReizigerDAOPsql(ReizigerDAOPsql reizigerDAOPsql) {
        this.reizigerDAOPsql = reizigerDAOPsql;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        //Statement stmt = myConn.createStatement();


//        String sql = String.format("select reiziger_id from reiziger " +
//                "WHERE reiziger_id=%d", adres);
//
//        ResultSet reizigerInfo=stmt.executeQuery(sql);
//        stmt.executeUpdate(sql);




//        Statement stmt2 = myConn.createStatement();

        PreparedStatement stmt1=myConn.prepareStatement("INSERT INTO ADRES (ADRES_ID, POSTCODE,HUISNUMMER,STRAAT,WOONPLAATS,REIZIGER_ID) VALUES(?, ?, ?,?,?,?)");
        stmt1.setInt(1,adres.getId());
        stmt1.setString(2,adres.getPostcode());
        stmt1.setString(3,adres.getHuisnummer());
        stmt1.setString(4,adres.getStraat());
        stmt1.setString(5,adres.getWoonplaats());
        stmt1.setInt(6,adres.getReiziger_id());


        //String sql2 = String.format("INSERT INTO ADRES (ADRES_ID, POSTCODE,HUISNUMMER,STRAAT,WOONPLAATS,REIZIGER_ID) VALUES(%d, '%s', '%s','%s','%s','%d')", adres.getId(),adres.getPostcode(), adres.getHuisnummer(),adres.getStraat(),adres.getWoonplaats(),adres.getReiziger_id());

        return stmt1.execute();
    }


    @Override
    public boolean update(Adres adres) throws SQLException {



//        Statement stmt = myConn.createStatement();
//
//        String sql = String.format("UPDATE adres" +
//                                   "SET POSTCODE='%s',HUISNUMMER='%s',STRAAT'%s',WOONPLAATS='%s'" +
//                                   "WHERE id=%d",adres.getPostcode(),adres.getHuisnummer(),adres.getStraat(),adres.getWoonplaats(),adres.getId());

        PreparedStatement stmt1=myConn.prepareStatement("UPDATE adres "+
                "SET POSTCODE=?,HUISNUMMER=?,STRAAT=?,WOONPLAATS=?" +
                "WHERE id=?");
        //,adres.getPostcode(),adres.getHuisnummer(),adres.getStraat(),adres.getWoonplaats(),adres.getId()


        stmt1.setString(1,adres.getPostcode());
        stmt1.setString(2,adres.getHuisnummer());
        stmt1.setString(3,adres.getStraat());
        stmt1.setString(4,adres.getWoonplaats());
        stmt1.setInt(5,adres.getReiziger_id());

        return stmt1.execute();
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
//        Statement stmt = myConn.createStatement();
        PreparedStatement stmt1=myConn.prepareStatement("DELETE FROM adres WHERE id=?");
        stmt1.setInt(1,adres.getId());

//        String sql = String.format("DELETE FROM adres WHERE id='%d'",adres.getId());


        return stmt1.execute();
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Statement stmt = myConn.createStatement();
        PreparedStatement stmt1=myConn.prepareStatement("select * from adres \" +\n" +
                "                \"WHERE reiziger_id=?");
        stmt1.setInt(1,reiziger.getReiziger_id());

//        String sql = String.format("select * from adres " +
//                "WHERE reiziger_id=%d",reiziger.getReiziger_id());

        ResultSet adres=stmt1.executeQuery();
        adres.next();
        System.out.println("in finbyreiziger");
        Adres returnAdres=new Adres(adres.getInt("adres_id"),adres.getString("straat"),adres.getString("huisnummer"),adres.getString("postcode"),adres.getString("straat"),adres.getInt("reiziger_id"));
        returnAdres.setReiziger(reiziger);
        return returnAdres;
    }

    @Override
    public  List<Adres> findAll() throws SQLException {
        List<Adres> adresList=new ArrayList<>();
        //Statement stmt = myConn.createStatement();


        PreparedStatement stmt1=myConn.prepareStatement("select * from adres");



        ResultSet adressenVanDB=stmt1.executeQuery();


        Adres adres;
        while (adressenVanDB.next()) {
            adres=new Adres(adressenVanDB.getInt("adres_id"),adressenVanDB.getString("straat"),adressenVanDB.getString("huisnummer"),adressenVanDB.getString("postcode"),adressenVanDB.getString("straat"),adressenVanDB.getInt("reiziger_id"));
            adres.setReiziger(reizigerDAOPsql.findById(adres.getReiziger_id()));

            adresList.add(adres);
        }
        return adresList;
    }
}
