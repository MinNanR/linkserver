package site.minnan.linkserver.entites.jwt;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class JwtRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
}
