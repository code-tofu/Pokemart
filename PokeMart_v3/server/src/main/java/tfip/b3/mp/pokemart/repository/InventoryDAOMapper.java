package tfip.b3.mp.pokemart.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tfip.b3.mp.pokemart.model.InventoryDAO;


public class InventoryDAOMapper implements RowMapper<InventoryDAO>  {

    @Override
    public InventoryDAO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new InventoryDAO(
            rs.getString("product_id"),
            rs.getInt("stock"),
            rs.getDouble("discount"),
            rs.getString("comments")
        );
    }
}
