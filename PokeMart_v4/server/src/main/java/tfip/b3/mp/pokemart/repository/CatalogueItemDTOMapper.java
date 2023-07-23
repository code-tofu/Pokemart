package tfip.b3.mp.pokemart.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tfip.b3.mp.pokemart.model.CatalogueItemDTO;


public class CatalogueItemDTOMapper implements RowMapper<CatalogueItemDTO> {

    @Override
    public CatalogueItemDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CatalogueItemDTO(
                rs.getString("product_id"),
                rs.getString("name_id"),
                rs.getString("product_name"),
                rs.getDouble("cost"),
                rs.getInt("stock"),
                rs.getDouble("discount"),
                rs.getString("comments"));
    }
}
