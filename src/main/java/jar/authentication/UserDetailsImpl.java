package jar.authentication;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private String email;
    private String password;
    private boolean enabled;
    private Collection<SimpleGrantedAuthority> authorities;

    public UserDetailsImpl(String email, String password, boolean enabled, Collection<SimpleGrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Return true if account expiration is not a concern
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Return true if account lock is not a concern
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Return true if credentials expiration is not a concern
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
