package b3.mp.tfip.pokemart.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import b3.mp.tfip.pokemart.model.CatalogueComponentDTO;

public class CatalogueComponentMapper implements RowMapper<CatalogueComponentDTO> {

    @Override
    public CatalogueComponentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CatalogueComponentDTO(
                rs.getString("product_id"),
                rs.getString("name_id"),
                rs.getString("product_name"),
                rs.getDouble("cost"),
                rs.getInt("quantity"),
                rs.getDouble("discount"),
                rs.getDouble("deduct"));
    }
}
