package lk.ijse.gdse68.greenshadow.jwtmodels;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignIn {
    private String email;
    private String password;
}

