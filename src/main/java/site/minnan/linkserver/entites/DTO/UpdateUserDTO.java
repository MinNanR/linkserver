package site.minnan.linkserver.entites.DTO;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateUserDTO {

    @NotNull
    private Integer id;
    private String password;
    private String nickName;
}
