package tfip.b3.mp.pokemart.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import tfip.b3.mp.pokemart.model.UserDetailsImpl;

public class UserDetailsMapper implements RowMapper<UserDetailsImpl> {
    
    @Override
    public UserDetailsImpl mapRow(ResultSet rs, int rowNum) throws SQLException {
        LinkedList<SimpleGrantedAuthority> authorities = new LinkedList<>();
        authorities.add(new SimpleGrantedAuthority(rs.getString("role")));
        return new UserDetailsImpl(
            rs.getString("user_id"),
            rs.getString("username"),
            rs.getString("password"),
            rs.getBoolean("enabled"),
            rs.getBoolean("account_non_expired"),
            rs.getBoolean("credentials_non_expired"),
            rs.getBoolean("account_non_locked"),
            authorities
        );
    }
}


 
