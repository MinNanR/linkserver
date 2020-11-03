package site.minnan.linkserver.entites.DO;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class UserInformation {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;
    private String password;
    @TableField(value = "nick_name")
    private String nickName;

    private String role;
}


