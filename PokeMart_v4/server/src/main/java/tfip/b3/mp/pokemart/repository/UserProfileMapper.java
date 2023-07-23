package tfip.b3.mp.pokemart.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import tfip.b3.mp.pokemart.model.MemberLevel;
import tfip.b3.mp.pokemart.model.UserProfileDAO;

public class UserProfileMapper implements RowMapper<UserProfileDAO> {
    
    @Override
    public UserProfileDAO mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new UserProfileDAO(
                rs.getString("user_id"),
            rs.getString("customer_name"),
            rs.getString("customer_email"),
            rs.getString("customer_phone"),
            rs.getString("shipping_address"),
            rs.getDate("birthdate").getTime(),
            rs.getString("gender"),
            MemberLevel.valueOf(rs.getString("member_level")),
            rs.getDate("member_since").getTime()
            );
    }
}