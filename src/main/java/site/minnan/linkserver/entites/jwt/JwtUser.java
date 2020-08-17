package site.minnan.linkserver.entites.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class JwtUser implements UserDetails {

    private static final long serialVersionID = 1L;

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户登陆
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 是否启用
     */
    private Boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());
    }

    private List<GrantedAuthority> mapToGrantedAuthorities(String authority) {
        return Arrays.asList(new SimpleGrantedAuthority(authority));
    }

    public JwtUser(Integer id, String username, String password, List<String> authorities, Boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = mapToGrantedAuthorities(authorities);
        this.enabled = enabled;
    }

    public JwtUser(Integer id, String username, String password, String authorities, Boolean enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = mapToGrantedAuthorities(authorities);
        this.enabled = enabled;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
