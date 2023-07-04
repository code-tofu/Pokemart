package tfip.b3.mp.pokemart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tfip.b3.mp.pokemart.model.RegisterRequest;
import tfip.b3.mp.pokemart.model.Roles;
import tfip.b3.mp.pokemart.model.UserDetailsImpl;
import tfip.b3.mp.pokemart.model.UserProfileDAO;
import tfip.b3.mp.pokemart.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    UserRepository userRepo;

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException{
        return userRepo.getUserDetailsByUsername(username);
    }

    public UserDetailsImpl loadUserByUserID(String userID) throws UsernameNotFoundException{
        return userRepo.getUserDetailsByUserID(userID);
    }

    public String createNewUser(RegisterRequest registerRequest,Roles role){
        return userRepo.createNewUser(registerRequest,role);
    }

    public UserProfileDAO getUserProfileByUserID(String userID) throws DataAccessException{
        return userRepo.getUserProfileByUserID(userID);
    }
    
}
