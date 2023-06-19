package tfip.b3.mp.pokemart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    private String userID;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String shippingAddress;
    private Long birthday;
    private String gender;
    private MemberLevel memberLevel;
    private Long memberSince;
}
    // socialMedia
    // country