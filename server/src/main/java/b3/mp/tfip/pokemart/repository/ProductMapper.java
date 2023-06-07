package b3.mp.tfip.pokemart.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import b3.mp.tfip.pokemart.model.ProductDAO;

public class ProductMapper implements RowMapper<ProductDAO> {

    @Override
    public ProductDAO mapRow(ResultSet rs, int rowNum) throws SQLException {
        ProductDAO product = new ProductDAO(
                rs.getString("product_id"),
                rs.getInt("api_id"),
                rs.getString("name_id"),
                rs.getString("category"),
                rs.getDouble("cost"),
                rs.getString("details"),
                rs.getString("product_name"));
        return product;
    }
}
