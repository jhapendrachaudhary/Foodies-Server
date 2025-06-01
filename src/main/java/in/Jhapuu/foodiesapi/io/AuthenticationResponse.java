package in.Jhapuu.foodiesapi.io;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {
    private String email;
    private String token;
//    private String role;
}
