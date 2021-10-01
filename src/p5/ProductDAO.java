package p5;

import P4.OvChipKaart;
import p3.Adres;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    List<Product> findAll() throws SQLException ;
    List<Product> findByOVChipkaart(OvChipKaart ovChipKaart) throws SQLException ;


    }
