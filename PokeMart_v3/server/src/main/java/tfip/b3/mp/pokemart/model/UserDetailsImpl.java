package tfip.b3.mp.pokemart.model;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailsImpl implements UserDetails{
    String userID;
    String username;
    String password;
    boolean enabled;
    boolean accountNonExpired;
    boolean credentialsNonExpired;
    boolean accountNonLocked;
    List<SimpleGrantedAuthority> authorities;
}



