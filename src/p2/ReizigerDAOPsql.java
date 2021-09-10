package p2;

import p2.Reiziger;
import p2.ReizigerDAO;
import p3.Adres;
import p3.AdresDAOPsql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {
    public Connection myConn = null;


    public ReizigerDAOPsql(Connection myConn){
        this.myConn=myConn;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        Statement stmt = myConn.createStatement();

        String sql = String.format("INSERT INTO REIZIGER (REIZIGER_ID, VOORLETTERS,TUSSENVOEGSEL,ACHTERNAAM,GEBOORTEDATUM) VALUES(%d, '%s', '%s','%s','%s')", reiziger.getReiziger_id(), reiziger.getVoorletters(), reiziger.getTussenvoegsel(),reiziger.getAchternaam(),reiziger.getGeboorteDatum());


        return stmt.execute(sql);
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        Statement stmt = myConn.createStatement();

        if (reiziger.getTussenvoegsel()==null){

            String sql = String.format("UPDATE reiziger" +
                    "SET voorletters='%s',achternaam='%s',geboortedatum='%s'" +
                    "WHERE id=%d",reiziger.getVoorletters(),reiziger.getAchternaam(),reiziger.getGeboorteDatum(),reiziger.getReiziger_id());

            return stmt.execute(sql);
        }else{
            String sql = String.format("UPDATE reiziger" +
                "SET voorletters='%s',tussenvoegsel='%s',achternaam='%s',geboortedatum='%s'" +
                "WHERE id=%d",reiziger.getVoorletters(),reiziger.getTussenvoegsel(),reiziger.getAchternaam(),reiziger.getGeboorteDatum(),reiziger.getReiziger_id());
            return stmt.execute(sql);

        }




    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        Statement stmt = myConn.createStatement();

        String sql = String.format("DELETE FROM REIZIGER WHERE REIZIGER_ID='%d'", reiziger.getReiziger_id());



        return stmt.execute(sql);
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Statement stmt = myConn.createStatement();
        String sql = String.format("select * from reiziger WHERE reiziger_id='%d'", id);

        ResultSet myRs=stmt.executeQuery(sql);
        myRs.next();


        AdresDAOPsql adresDAOPsql=new AdresDAOPsql(myConn);
        Adres adres=null;
        if (myRs.getString("tussenvoegsel")==null){
            adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));
            return new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres);

        }else{
            adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));


            return new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres);


        }

    }

    @Override
    public List<Reiziger> findByGbDatum(String datum) throws SQLException {
        List<Reiziger> reizigerList=new ArrayList<>();
        Statement stmt = myConn.createStatement();

        String sql = String.format("select * from reiziger WHERE geboortedatum='%s'", datum);

        ResultSet myRs=stmt.executeQuery(sql);
        while (myRs.next()){
            if (myRs.getString("tussenvoegsel")==null){

                reizigerList.add(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),new Adres()));
            }else reizigerList.add(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));
        }


        return reizigerList;
    }


    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> reizigerList=new ArrayList<>();
        Statement stmt = myConn.createStatement();
         ResultSet myRs = stmt.executeQuery("select * from reiziger");
         Adres adres;
        AdresDAOPsql adresDAOPsql=new AdresDAOPsql(myConn);
        System.out.println(1);
        while (myRs.next()){
            if (myRs.getString("tussenvoegsel")==null){
                adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));
                System.out.println(2);
                reizigerList.add(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres));
            }else{
                adres=adresDAOPsql.findByReiziger(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum")));
                System.out.println(3);
                reizigerList.add(new Reiziger(myRs.getInt("reiziger_id"),myRs.getString("voorletters"),myRs.getString("tussenvoegsel"),myRs.getString("achternaam"),myRs.getDate("geboortedatum"),adres));

            }
        }


        return reizigerList;
    }
}
