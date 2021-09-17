package p2;

import P4.OvChipKaart;
import P4.OvChipKaartDAOPsql;
import p2.Reiziger;
import p2.ReizigerDAO;
import p3.Adres;
import p3.AdresDAO;
import p3.AdresDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    public Connection myConn = null;
    AdresDAOPsql adresDAOPsql;
    OvChipKaartDAOPsql ovChipKaartDAOPsql;
    List<OvChipKaartDAOPsql> ovChipKaartDAOPsqlList;


    public ReizigerDAOPsql(Connection myConn){
        this.myConn=myConn;
    }
    public ReizigerDAOPsql(Connection myConn,AdresDAOPsql adresDAOPsql){
        this.myConn=myConn;
        this.adresDAOPsql=adresDAOPsql;
    }


    public void setAdresDAOPsql(AdresDAOPsql adresDAOPsql) {
        this.adresDAOPsql=adresDAOPsql;

    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {

        //setAdresDAOPsql(new AdresDAOPsql(myConn));
        //Statement stmt = myConn.createStatement();
        PreparedStatement stmt2=myConn.prepareStatement("INSERT INTO REIZIGER (REIZIGER_ID, VOORLETTERS,TUSSENVOEGSEL,ACHTERNAAM,GEBOORTEDATUM) VALUES(?, ?, ?,?,?)");
        stmt2.setInt(1,reiziger.getReiziger_id());//1 specifies the first parameter in the query
        stmt2.setString(2,reiziger.getVoorletters());
        stmt2.setString(3,reiziger.getTussenvoegsel());//1 specifies the first parameter in the query
        stmt2.setString(4,reiziger.getAchternaam());
        stmt2.setDate(5,reiziger.getGeboorteDatum());
        if (!reiziger.getOvChipKaartList().isEmpty()){
            for (OvChipKaart ovChipKaart: reiziger.getOvChipKaartList()) {
                ovChipKaartDAOPsql.save(ovChipKaart);

            }
        }



        adresDAOPsql.save(reiziger.getAdres());

        //String sql = String.format("INSERT INTO REIZIGER (REIZIGER_ID, VOORLETTERS,TUSSENVOEGSEL,ACHTERNAAM,GEBOORTEDATUM) VALUES(%d, '%s', '%s','%s','%s')", reiziger.getReiziger_id(), reiziger.getVoorletters(), reiziger.getTussenvoegsel(),reiziger.getAchternaam(),reiziger.getGeboorteDatum());




        return stmt2.execute();
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        //Statement stmt = myConn.createStatement();

        if (reiziger.getTussenvoegsel()==null){
            PreparedStatement stmt2=myConn.prepareStatement("UPDATE REIZIGER SET VOORLETTERS=? ,ACHTERNAAM=?,GEBOORTEDATUM=? WHERE reiziger_id=?");
            stmt2.setString(1,reiziger.getVoorletters());
            stmt2.setString(2,reiziger.getAchternaam());
            stmt2.setDate(3,reiziger.getGeboorteDatum());
            stmt2.setInt(4,reiziger.getReiziger_id());


//            String sql = String.format("UPDATE reiziger" +
//                    "SET voorletters='%s',achternaam='%s',geboortedatum='%s'" +
//                    "WHERE id=%d",reiziger.getVoorletters(),reiziger.getAchternaam(),reiziger.getGeboorteDatum(),reiziger.getReiziger_id());

            ;

            if (!reiziger.getOvChipKaartList().isEmpty()){
                for (OvChipKaart ovChipKaart: reiziger.getOvChipKaartList() ) {
                    ovChipKaartDAOPsql.update(ovChipKaart);


                }
            }
            adresDAOPsql.update(reiziger.getAdres());
            return stmt2.execute();
        }else{
            PreparedStatement stmt2=myConn.prepareStatement("UPDATE REIZIGER SET TUSSENVOEGSEL=? ,VOORLETTERS=? ,ACHTERNAAM=?,GEBOORTEDATUM=? WHERE reiziger_id=?");
            stmt2.setString(1,reiziger.getTussenvoegsel());
            stmt2.setString(2,reiziger.getVoorletters());
            stmt2.setString(3,reiziger.getAchternaam());
            stmt2.setDate(4,reiziger.getGeboorteDatum());
            stmt2.setInt(5,reiziger.getReiziger_id());


            if (!reiziger.getOvChipKaartList().isEmpty()){
                for (OvChipKaart ovChipKaart: reiziger.getOvChipKaartList() ) {
                    ovChipKaartDAOPsql.update(ovChipKaart);


                }
            }
//            String sql = String.format("UPDATE reiziger" +
//                "SET voorletters='%s',tussenvoegsel='%s',achternaam='%s',geboortedatum='%s'" +
//                "WHERE id=%d",reiziger.getVoorletters(),reiziger.getTussenvoegsel(),reiziger.getAchternaam(),reiziger.getGeboorteDatum(),reiziger.getReiziger_id());
            adresDAOPsql.update(reiziger.getAdres());
            return stmt2.execute();

        }




    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        //Statement stmt = myConn.createStatement();
        PreparedStatement stmt1=myConn.prepareStatement("DELETE FROM REIZIGER WHERE REIZIGER_ID=?");
        stmt1.setInt(1,reiziger.getReiziger_id());//1 specifies the first parameter in the query


        //String sql = String.format("DELETE FROM REIZIGER WHERE REIZIGER_ID='%d'", reiziger.getReiziger_id());
        adresDAOPsql.delete(reiziger.getAdres());
//        PreparedStatement stmt2=myConn.prepareStatement("DELETE FROM adres WHERE reiziger_id=?");
//        stmt2.setInt(1,reiziger.getReiziger_id());//1 specifies the first parameter in the query
//        stmt2.executeUpdate();

        if (!reiziger.getOvChipKaartList().isEmpty()){
            for (OvChipKaart ovChipKaart: reiziger.getOvChipKaartList() ) {
                ovChipKaartDAOPsql.delete(ovChipKaart);


            }
        }



        return stmt1.execute();
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        PreparedStatement stmt1=myConn.prepareStatement("select * from reiziger WHERE reiziger_id=?");
        stmt1.setInt(1,id);//1 specifies the first parameter in the query
//        PreparedStatement stmt2=myConn.prepareStatement("select * from ov_chipkaart WHERE reiziger_id=?");
//        stmt2.setInt(1,id);//1 specifies the first parameter in the query




        ResultSet myRs=stmt1.executeQuery();
        //ResultSet myRs2=stmt2.executeQuery();
        Adres adres;


        myRs.next();


        //AdresDAOPsql adresDAOPsql=new AdresDAOPsql(myConn);
        Reiziger reiziger;
        if (myRs.getString("tussenvoegsel")==null){



            adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));
            reiziger=new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres);
            reiziger.setOvChipKaartList(ovChipKaartDAOPsql.findByReiziger(reiziger));
            return reiziger;


        }else{

            adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));

            reiziger=new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres);
            reiziger.setOvChipKaartList(ovChipKaartDAOPsql.findByReiziger(reiziger));
            return reiziger;

        }

    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) throws SQLException {
        List<Reiziger> reizigerList=new ArrayList<>();

        PreparedStatement stmt1=myConn.prepareStatement("select * from reiziger WHERE geboortedatum=?");
        stmt1.setString(1,datum);


        ResultSet myRs=stmt1.executeQuery();
        Adres adres;
        Reiziger reiziger;
        while (myRs.next()){
            if (myRs.getString("tussenvoegsel")==null){

                adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));

                reiziger=new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres);
                reiziger.setOvChipKaartList(ovChipKaartDAOPsql.findByReiziger(reiziger));


                reizigerList.add(reiziger);
            }else{
                adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));
                reiziger=new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres);
                reiziger.setOvChipKaartList(ovChipKaartDAOPsql.findByReiziger(reiziger));

                reizigerList.add(reiziger);

            }
        }


        return reizigerList;
    }


    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigerList=new ArrayList<>();
        PreparedStatement stmt1=myConn.prepareStatement("select * from reiziger");

        ResultSet myRs = stmt1.executeQuery();
        Adres adres;
       Reiziger reiziger;
        while (myRs.next()){
            if (myRs.getString("tussenvoegsel")==null){
                adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));
                reiziger=new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres);
                reiziger.setOvChipKaartList(ovChipKaartDAOPsql.findByReiziger(reiziger));

                reizigerList.add(reiziger);
            }else{
                adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));
                reiziger=new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres);
                reiziger.setOvChipKaartList(ovChipKaartDAOPsql.findByReiziger(reiziger));

                reizigerList.add(reiziger);

            }
        }


        return reizigerList;
    }
}
