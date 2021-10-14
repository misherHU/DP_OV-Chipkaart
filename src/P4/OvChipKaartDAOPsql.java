package P4;

import p2.Reiziger;
import p5.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OvChipKaartDAOPsql implements OvChipDAO{
    public Connection myConn = null;

    public OvChipKaartDAOPsql(Connection myConn){
        this.myConn=myConn;
    }


    @Override
    public boolean save(OvChipKaart ovChipKaart) throws SQLException {
        try{

            PreparedStatement stmt1=myConn.prepareStatement("INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot,klasse,saldo,reiziger_id) VALUES(?, ?, ?,?,?)");
            stmt1.setInt(1,ovChipKaart.getKaartNummer());
            stmt1.setDate(2,ovChipKaart.getGeldigTot());
            stmt1.setInt(3,ovChipKaart.getKlasse());
            stmt1.setDouble(4,ovChipKaart.getSaldo());
            stmt1.setInt(5,ovChipKaart.getReiziger().getReiziger_id());
            stmt1.executeUpdate();


            LocalDate currentDate = LocalDate.now();
            if (ovChipKaart.getProducts().size()>0){
                for (Integer productNummer:ovChipKaart.getProducts()) {

                    PreparedStatement stmt2=myConn.prepareStatement("INSERT INTO ov_chipkaart_product (product_nummer,kaart_nummer,status,last_update)  VALUES(?,?,actief,?)");
                    stmt2.setInt(1,productNummer);
                    stmt2.setInt(2,ovChipKaart.getKaartNummer());
                    stmt2.setDate(3, Date.valueOf(currentDate));

                    stmt2.executeUpdate();

                }




            }

            return true;


        }catch (Exception exc){
            return false;
        }

    }


//    @Override
//    public boolean addProductToOvChipKaart(Product product) throws SQLException {
//        PreparedStatement stmt1=myConn.prepareStatement("INSERT INTO ov_chipkaart(kaart_nummer, geldig_tot,klasse,saldo,reiziger_id) VALUES(?, ?, ?,?,?)");
//        stmt1.setInt(1,);
//        stmt1.setDate(2,ovChipKaart.getGeldigTot());
//        stmt1.setInt(3,ovChipKaart.getKlasse());
//        stmt1.setDouble(4,ovChipKaart.getSaldo());
//        stmt1.setInt(5,ovChipKaart.getReiziger().getReiziger_id());
//
//        return false;
//    }

    @Override
    public boolean update(OvChipKaart ovChipKaart) throws SQLException {


        PreparedStatement stmt1=myConn.prepareStatement("UPDATE ov_chipkaart "+
                "SET klasse=?,saldo=?" +
                "WHERE kaart_nummer=?");

        stmt1.setDouble(1,ovChipKaart.getSaldo());
        stmt1.setInt(2,ovChipKaart.getKaartNummer());



//        PreparedStatement stmt2=myConn.prepareStatement("UPDATE ov_chipkaart_product "+
//                "SET kaart_nummer=?" +
//                "WHERE kaart_nummer=?");
//
//        stmt1.setDouble(1,ovChipKaart.getSaldo());
//        stmt1.setInt(2,ovChipKaart.getKaartNummer());
//        stmt2.execute();


        return stmt1.execute();
    }

    @Override
    public boolean delete(OvChipKaart ovChipKaart) throws SQLException {
        PreparedStatement stmt1=myConn.prepareStatement("DELETE FROM ov_chipkaart WHERE id=?");
        stmt1.setInt(1,ovChipKaart.getKaartNummer());


        PreparedStatement stmt2=myConn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE kaart_nummer=?");
        stmt2.setInt(1,ovChipKaart.getKaartNummer());


        stmt2.execute();
        return stmt1.execute();
    }

    @Override
    public List<OvChipKaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OvChipKaart> ovChipKaartList=new ArrayList<>();
        PreparedStatement stmt1=myConn.prepareStatement("select * from ov_chipkaart " +
                "                WHERE reiziger_id=?");
        stmt1.setInt(1,reiziger.getReiziger_id());

        ResultSet myRs=stmt1.executeQuery();

        while (myRs.next()){
            ovChipKaartList.add(new OvChipKaart(myRs.getInt("kaartnummer"),myRs.getDate("geldig_tot"),myRs.getInt("klasse"),myRs.getDouble("saldo"),reiziger));


        }

        PreparedStatement stmt2=myConn.prepareStatement("select * from ov_chipkaart_product " +
                " WHERE kaart_nummer=?");


        for (OvChipKaart ovChipKaart:ovChipKaartList) {
            stmt2.setInt(1,ovChipKaart.getKaartNummer());

            ResultSet myRs2=stmt1.executeQuery();
            List<Integer> products=new ArrayList<>();
            while (myRs2.next()){
                products.add(myRs2.getInt("product_nummer"));



            }
            ovChipKaart.setProducts(products);
        }


return ovChipKaartList;
    }




}
