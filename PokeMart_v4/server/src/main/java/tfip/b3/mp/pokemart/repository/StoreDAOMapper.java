package tfip.b3.mp.pokemart.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tfip.b3.mp.pokemart.model.StoreDAO;

public class StoreDAOMapper implements RowMapper<StoreDAO> {
 
    @Override
    public StoreDAO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new StoreDAO(
            rs.getInt("store_id"),
            rs.getString("store_address"),
            rs.getString("store_name"),
            rs.getString("store_phone"),
            rs.getDouble("store_lat"),
            rs.getDouble("store_long")
        );
    }
}
