package P4;

import p2.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OvChipKaartDAOPsql implements OvChipDAO{
    public Connection myConn = null;


    @Override
    public boolean save(OvChipKaart ovChipKaart) throws SQLException {


        PreparedStatement stmt1=myConn.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot,klasse,saldo,reiziger_id) VALUES(?, ?, ?,?,?)");
        stmt1.setInt(1,ovChipKaart.getKaartNummer());
        stmt1.setDate(2,ovChipKaart.getGeldigTot());
        stmt1.setInt(3,ovChipKaart.getKlasse());
        stmt1.setDouble(4,ovChipKaart.getSaldo());
        stmt1.setInt(5,ovChipKaart.getReiziger().getReiziger_id());


        //String sql2 = String.format("INSERT INTO ADRES (ADRES_ID, POSTCODE,HUISNUMMER,STRAAT,WOONPLAATS,REIZIGER_ID) VALUES(%d, '%s', '%s','%s','%s','%d')", adres.getId(),adres.getPostcode(), adres.getHuisnummer(),adres.getStraat(),adres.getWoonplaats(),adres.getReiziger_id());



        return stmt1.execute();
    }

    @Override
    public boolean update(OvChipKaart ovChipKaart) throws SQLException {


        PreparedStatement stmt1=myConn.prepareStatement("UPDATE ov_chipkaart "+
                "SET klasse=?,saldo=?" +
                "WHERE kaart_nummer=?");

        stmt1.setDouble(1,ovChipKaart.getSaldo());
        stmt1.setInt(2,ovChipKaart.getKaartNummer());

        return stmt1.execute();
    }

    @Override
    public boolean delete(OvChipKaart ovChipKaart) throws SQLException {
        PreparedStatement stmt1=myConn.prepareStatement("DELETE FROM ov_chipkaart WHERE id=?");
        stmt1.setInt(1,ovChipKaart.getKaartNummer());
        return stmt1.execute();
    }

    @Override
    public List<OvChipKaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OvChipKaart> ovChipKaartList=new ArrayList<>();
        PreparedStatement stmt1=myConn.prepareStatement("select * from ov_chipkaart \" +\n" +
                "                \"WHERE reiziger_id=?");
        stmt1.setInt(1,reiziger.getReiziger_id());

        ResultSet myRs=stmt1.executeQuery();

        while (myRs.next()){
            ovChipKaartList.add(new OvChipKaart(myRs.getInt("kaartnummer"),myRs.getDate("geldig_tot"),myRs.getInt("klasse"),myRs.getDouble("saldo"),reiziger));


        }


return ovChipKaartList;
    }
}
