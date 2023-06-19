package tfip.b3.mp.pokemart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tfip.b3.mp.pokemart.model.MemberLevel;
import tfip.b3.mp.pokemart.model.RegisterRequest;
import tfip.b3.mp.pokemart.model.Roles;
import tfip.b3.mp.pokemart.model.UserDetailsImpl;
import tfip.b3.mp.pokemart.model.UserProfile;
import tfip.b3.mp.pokemart.utils.Utils;

import static tfip.b3.mp.pokemart.repository.UserQueries.*;

import java.util.Date;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jTemplate;

    public UserDetailsImpl getUserDetailsByUsername(String username) throws DataAccessException {
        return jTemplate.queryForObject(SELECT_USERDETAILS_BY_USERNAME, new UserDetailsMapper(), username);
    }

    public UserDetailsImpl getUserDetailsByUserID(String userID) throws DataAccessException {
        return jTemplate.queryForObject(SELECT_USERDETAILS_BY_USERID, new UserDetailsMapper(), userID);
    }

    public UserProfile getUserProfileByEmail(String email) throws DataAccessException{
        return jTemplate.queryForObject(SELECT_USERPROFILE_BY_EMAIL, new UserProfileMapper(),email);
    }

    public UserProfile getUserProfileByUserID(String userID) throws DataAccessException{
        return jTemplate.queryForObject(SELECT_USERPROFILE_BY_USERID, new UserProfileMapper(),userID);
    }

    @Transactional
    public String createNewUser(RegisterRequest registerRequest,  Roles role) throws DataAccessException{
        String newUserID = "u" + Utils.generateUUID(8);
        System.out.println(newUserID);
        while(jTemplate.queryForObject(EXISTS_USER_BY_USERID,Boolean.class, newUserID)){
            newUserID = "u" + Utils.generateUUID(8);
        }
        jTemplate.update(INSERT_NEW_USER_DETAILS,
            newUserID,
            registerRequest.getUsername(),
            registerRequest.getPassword(),
            true, true, true, true,
            role.toString()
            );
        jTemplate.update(INSERT_NEW_USER_PROFILE,
            newUserID,
            registerRequest.getCustomerName(),
            registerRequest.getCustomerEmail(),
            registerRequest.getCustomerPhone(),
            registerRequest.getShippingAddress(),
            new Date(registerRequest.getBirthday()),
            registerRequest.getGender(),
            MemberLevel.BRONZE.toString(),
            new Date()
            );
        return newUserID;
    }
    
}
