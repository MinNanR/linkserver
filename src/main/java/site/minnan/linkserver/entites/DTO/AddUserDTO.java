package site.minnan.linkserver.entites.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class AddUserDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String nickName;

}
