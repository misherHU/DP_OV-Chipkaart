package p5;

import P4.OvChipKaart;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO{
    public Connection myConn = null;

    public ProductDAOPsql(Connection myConn){
        this.myConn=myConn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        try {
            PreparedStatement stmt1=myConn.prepareStatement("INSERT INTO product (product_nummer,naam,beschrijving,prijs) VALUES(?,?,?,?)");
            stmt1.setInt(1,product.getProduct_nummer());
            stmt1.setString(2,product.getNaam());
            stmt1.setString(3,product.getBeschrijving());
            stmt1.setDouble(4,product.getPrijs());
            stmt1.execute();


            if (product.getOvChipKaarts().size()>0){
                LocalDate currentDate = LocalDate.now();

                for (Integer ovChipKaart:product.getOvChipKaarts()) {
                    PreparedStatement stmt3=myConn.prepareStatement("SELECT * FROM ov_chipkaart_product WHERE kaart_nummer=?");

                    ResultSet opgeslaagdeOvChipkaartDB=stmt3.executeQuery();
                    if (opgeslaagdeOvChipkaartDB.next()){
                        PreparedStatement stmt2=myConn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES(?,?,actief,?)");
                        stmt2.setInt(1,ovChipKaart);
                        stmt2.setInt(2,product.getProduct_nummer());
                        stmt2.setDate(3, Date.valueOf(currentDate));
                        stmt2.execute();
                        stmt2.close();
                    }



                }
            }
            return true;



        }catch (Exception exc){
            return false;


        }



    }

    @Override
    public boolean update(Product product) throws SQLException {
        PreparedStatement stmt1=myConn.prepareStatement("UPDATE product SET naam=?,beschrijving=?,prijs=? WHERE product_nummer=?");
        stmt1.setString(1,product.getNaam());
        stmt1.setString(2,product.getBeschrijving());
        stmt1.setDouble(3,product.getPrijs());
        stmt1.setInt(4,product.getProduct_nummer());
        stmt1.executeUpdate();

        PreparedStatement stmt2 = myConn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer = ?");
        stmt2.setInt(1, product.getProduct_nummer());
        stmt2.executeUpdate();


        //Ovchipkaarts met een bepaalde kaart nummer halen van de database want in de getOvChipkaarts() methode van product krijg ik alleen de kaart nummers.
        PreparedStatement stmt3=myConn.prepareStatement("SELECT * FROM ov_chipkaarts WHERE kaart_nummer=?");
        List<OvChipKaart> ovChipKaartList=new ArrayList<>();
        for (Integer ovChipkaartNummer : product.getOvChipKaarts()) {

            ResultSet ovChipkaartDB=stmt3.executeQuery();
            stmt3.setInt(1,ovChipkaartNummer);

            while (ovChipkaartDB.next()){
                ovChipKaartList.add(new OvChipKaart(ovChipkaartDB.getInt("kaart_nummer"),ovChipkaartDB.getDate("geldig_tot"),ovChipkaartDB.getInt("klasse"),ovChipkaartDB.getDouble("saldo")));

            }

        }

        PreparedStatement stmt4 = myConn.prepareStatement("INSERT INTO ov_chipkaart_product VALUES (?, ?, ?, ?)");
        for (OvChipKaart ovChipkaart:ovChipKaartList) {
            stmt4.setInt(1, ovChipkaart.getKaartNummer());
            stmt4.setInt(2, product.getProduct_nummer());
            stmt4.setString(3, "actief");
            stmt4.setDate(4, Date.valueOf(LocalDate.now()));
            stmt4.execute();

        }






            return true;


    }



    @Override
    public boolean delete(Product product) throws SQLException {
        PreparedStatement stmt2=myConn.prepareStatement("DELETE FROM ov_chipkaart_product WHERE product_nummer=?");
        stmt2.setInt(1,product.getProduct_nummer());
        stmt2.execute();


        PreparedStatement stmt=myConn.prepareStatement("DELETE FROM product WHERE product_nummer=?");
        stmt.setInt(1,product.getProduct_nummer());




        return stmt.execute();
    }

    public List<Product> findByOVChipkaart(OvChipKaart ovChipKaart) throws SQLException {
        List<Product> gevondenProductsList=new ArrayList<>();
        PreparedStatement stmt1=myConn.prepareStatement("SELECT p.product_nummer,p.naam,p.beschrijving,p.prijs\n" +
                "FROM  product p\n" +
                "JOIN  ov_chipkaart_product ovp ON ovp.product_nummer=p.product_nummer AND ovp.kaart_nummer=?");
        stmt1.setInt(1,ovChipKaart.getKaartNummer());
        ResultSet gevondenProducten=stmt1.executeQuery();

        while (gevondenProducten.next()){


            gevondenProductsList.add(new Product(gevondenProducten.getInt("product_nummer"),gevondenProducten.getString("naam"),gevondenProducten.getString("beschrijving"),gevondenProducten.getDouble("prijs")));
        }


        for (Product product:gevondenProductsList ){
            PreparedStatement stmt2=myConn.prepareStatement("SELECT ock.kaart_nummer \n" +
                    "FROM ov_chipkaart ock\n" +
                    "JOIN ov_chipkaart_product ovp ON ovp.kaart_nummer=ock.kaart_nummer AND ovp.product_nummer=?");
            stmt1.setInt(1,product.getProduct_nummer());
            ResultSet gevondenOvChipKaarts=stmt2.executeQuery();
            while(gevondenOvChipKaarts.next()){

                product.addOVchipkaart(gevondenOvChipKaarts.getInt("kaart_nummer"));

            }
        }


        return gevondenProductsList;

    }
    public List<Product> findAll() throws SQLException {
        List<Product> productList=new ArrayList<>();
        PreparedStatement stmt1= myConn.prepareStatement("SELECT * FROM product");
        ResultSet productResultSet=stmt1.executeQuery();

        while (productResultSet.next()){
            productList.add(new Product(productResultSet.getInt("product_naam"),productResultSet.getString("naam"),productResultSet.getString("beschrijving"),productResultSet.getDouble("prijs")));


        }

        for (Product product: productList){
            PreparedStatement stmt2= myConn.prepareStatement("SELECT ock.kaart_nummer " +
                                                                 "FROM ov_chipkaart ock " +
                                            "JOIN ov_chipkaart_product ovp ON ovp.kaart_nummer=ock.kaart_nummer AND ovp.product_nummer=?");
            stmt2.setInt(1,product.getProduct_nummer());
            ResultSet ovChipResultset=stmt2.executeQuery();

            while (ovChipResultset.next()){
                product.addOVchipkaart(ovChipResultset.getInt("kaart_nummer"));
            }

        }

        return productList;


    }



}
