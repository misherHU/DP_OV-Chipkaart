package p3;

import p2.Reiziger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO{

    public Connection myConn = null;


    public AdresDAOPsql(Connection myConn){
        this.myConn=myConn;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
        //Statement stmt = myConn.createStatement();


//        String sql = String.format("select reiziger_id from reiziger " +
//                "WHERE reiziger_id=%d", adres);
//
//        ResultSet reizigerInfo=stmt.executeQuery(sql);
//        stmt.executeUpdate(sql);




        Statement stmt2 = myConn.createStatement();
        String sql2 = String.format("INSERT INTO ADRES (ADRES_ID, POSTCODE,HUISNUMMER,STRAAT,WOONPLAATS,REIZIGER_ID) VALUES(%d, '%s', '%s','%s','%s','%d')", adres.getId(),adres.getPostcode(), adres.getHuisnummer(),adres.getStraat(),adres.getWoonplaats(),adres.getReiziger_id());

        return stmt2.execute(sql2);
    }


    @Override
    public boolean update(Adres adres) throws SQLException {



        Statement stmt = myConn.createStatement();
        String sql = String.format("UPDATE adres" +
                                   "SET POSTCODE='%s',HUISNUMMER='%s',STRAAT'%s',WOONPLAATS='%s'" +
                                   "WHERE id=%d",adres.getPostcode(),adres.getHuisnummer(),adres.getStraat(),adres.getWoonplaats(),adres.getId());



        return stmt.execute(sql);
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        Statement stmt = myConn.createStatement();
        String sql = String.format("DELETE FROM adres WHERE id='%d'",adres.getId());


        return stmt.execute(sql);
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Statement stmt = myConn.createStatement();

        String sql = String.format("select * from adres " +
                "WHERE reiziger_id=%d",reiziger.getReiziger_id());

        ResultSet adres=stmt.executeQuery(sql);
        adres.next();
        System.out.println("in finbyreiziger");
        return new Adres(adres.getInt("adres_id"),adres.getString("straat"),adres.getString("huisnummer"),adres.getString("postcode"),adres.getString("straat"));
    }

    @Override
    public  List<Adres> findAll() throws SQLException {
        List<Adres> adresList=new ArrayList<>();
        Statement stmt = myConn.createStatement();
        ResultSet adressenVanDB=stmt.executeQuery("select * from adres ");



        while (adressenVanDB.next()) {
            adresList.add(new Adres(adressenVanDB.getInt("adres_id"),adressenVanDB.getString("straat"),adressenVanDB.getString("huisnummer"),adressenVanDB.getString("postcode"),adressenVanDB.getString("straat")));
        }
        return adresList;
    }
}
