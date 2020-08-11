package site.minnan.linkserver.entites;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupDO {

    private String username;

    private String password;

    private String nickName;

    private String code;


}
