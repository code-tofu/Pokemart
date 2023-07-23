package tfip.b3.mp.pokemart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateRequest {

    private String userID;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private String gender;

}
