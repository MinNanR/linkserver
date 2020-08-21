package site.minnan.linkserver.entites.DTO;

import lombok.Data;
import site.minnan.linkserver.entites.jwt.JwtUser;

import javax.validation.constraints.NotBlank;

@Data
public class ValidateUserDTO {

    @NotBlank
    String password;

    JwtUser jwtUser;
}
