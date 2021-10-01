package p5;

import P4.OvChipDAO;
import P4.OvChipKaart;
import P4.OvChipKaartDAOPsql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainP5 {
    static Connection connection;
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MainP5.GetConnection();

        MainP5.testP5(new ProductDAOPsql(connection),new OvChipKaartDAOPsql(connection));

    }

    public static void GetConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Plijn", "postgres", "master123");



    }

    public static void testP5(ProductDAO productDAO, OvChipDAO ovChipDAO) throws SQLException {
        //nieuwe product oplslaan met de save() methode
        productDAO.save(new Product(1,"week","reizen in de week",2));

        LocalDate currentDate = LocalDate.now();

        //nieuwe ov chipkaart opslaan met de save() methode
        ovChipDAO.save(new OvChipKaart(1, Date.valueOf(currentDate),4,5));

        //nieuwe product met een product in zijn lijst
        List<Integer> productNummerList=new ArrayList<>();
        productNummerList.add(1);
        OvChipKaart ovChipKaart1=new OvChipKaart(2, Date.valueOf(currentDate),4,29);
        ovChipKaart1.setProducts(productNummerList);
        ovChipDAO.save(ovChipKaart1);

        //findAll van de producten klasse
        System.out.println("FindAll():");
        for (Product product:productDAO.findAll()) {
            System.out.println("product "+product.getProduct_nummer());

        }



        //findByOvchipkaart() zoekt alle producten van het gegeven ovchipkaart.
        System.out.println("findByOvChipkaart():");

        for (Product product:productDAO.findByOVChipkaart(ovChipKaart1)) {
            System.out.println("product nummer: "+product.getProduct_nummer());

        }

        //de functie delete van ovchipkaart verwijderd ook alle records die de koppel tabel heeft met die ovchipkaart
        ovChipDAO.delete(ovChipKaart1);








    }

}
