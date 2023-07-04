package tfip.b3.mp.pokemart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    //userdetails
    private String username;
    private String password;
    //userprofile
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private Long birthdate;
    private String gender;
    
}
