package site.minnan.linkserver.entites.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    String redirectUrl;
    String jwtToken;
    List<String> router;
}
