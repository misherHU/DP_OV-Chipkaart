package P4;

import p2.Reiziger;
import p3.Adres;

import java.sql.SQLException;
import java.util.List;

public interface OvChipDAO {
    boolean save(OvChipKaart ovChipKaart) throws SQLException;
    boolean update(OvChipKaart ovChipKaart) throws SQLException;
    boolean delete(OvChipKaart ovChipKaart) throws SQLException;
    List<OvChipKaart> findByReiziger(Reiziger reiziger) throws SQLException;

}
