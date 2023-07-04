package tfip.b3.mp.pokemart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import tfip.b3.mp.pokemart.model.AuthRequest;
import tfip.b3.mp.pokemart.model.RegisterRequest;
import tfip.b3.mp.pokemart.model.Roles;
import tfip.b3.mp.pokemart.model.UserDetailsImpl;
import tfip.b3.mp.pokemart.model.UserProfileDAO;
import tfip.b3.mp.pokemart.service.UserService;
import tfip.b3.mp.pokemart.utils.ControllerUtil;
import tfip.b3.mp.pokemart.utils.JWTUtil;

@Controller
public class AuthController {

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    JWTUtil jwtUtil;
    @Autowired
    UserService userSvc;
    @Autowired
    PasswordEncoder pwdEncoder;


    //TODO: HANDLE EXCEPTIONS
    @PostMapping(path="/api/auth/login",consumes = "application/json") 
    public ResponseEntity<String> authenticateUser(@RequestBody AuthRequest authRequest) {
        System.out.println(">> [INFO] Authenticate:" + authRequest.getUsername());
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        UserDetailsImpl userdetails = (UserDetailsImpl)authentication.getPrincipal();
        System.out.println(">> [INFO] Generate Token For:" + userdetails.getUsername());
        System.out.println(">> [INFO] Role:" + userdetails.getAuthorities().get(0));
        String jwtToken = jwtUtil.generateToken(userdetails.getUsername());
        System.out.println(">> [INFO] Generated Token:" + jwtToken);

        return ResponseEntity.ok((Json.createObjectBuilder()
            .add("userID",userdetails.getUserID())
            .add("role",userdetails.getAuthorities().get(0).toString())
            .add("accessToken",jwtToken))
            .build().toString());
    }

    @PostMapping("/api/auth/registerCustomer")
    public ResponseEntity<String> registerCustomer(@RequestBody RegisterRequest registerRequest) {
        registerRequest.setPassword(pwdEncoder.encode(registerRequest.getPassword()));
        String newUser = userSvc.createNewUser(registerRequest, Roles.ROLE_CUSTOMER);
        UserDetailsImpl newUserDetails = userSvc.loadUserByUserID(newUser);
        UserProfileDAO newUserProfile = userSvc.getUserProfileByUserID(newUser);
        System.out.println(">> [INFO] Registered: " + newUserDetails.toString() + newUserProfile.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body((Json.createObjectBuilder().add("userID",newUser)).build().toString());
    }

    @GetMapping("/api/authtest")
    public ResponseEntity<String> hello(){
        UserDetailsImpl user = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(">> [INFO] HELLO Access Test User: " + user.getUsername());
        return ResponseEntity.ok("hello! You are Authorized");
    }

    @GetMapping("/api/user/profile/{username}")
    public ResponseEntity<String> getUserProfile(@PathVariable String username){
        try{
            String userID = userSvc.loadUserByUsername(username).getUserID();
            UserProfileDAO userProfile = userSvc.getUserProfileByUserID(userID);
            ObjectMapper mapper = new ObjectMapper();
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.writeValueAsString(userProfile));
        } catch (Exception ex) {
            return ControllerUtil.exceptionHandler(ex);
        }
    }
}
 