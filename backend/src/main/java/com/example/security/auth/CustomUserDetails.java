package com.example.security.auth;

import lombok.ToString;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.*;

@ToString
public class CustomUserDetails implements UserDetails {

    private final String email;
    private final String password;
    private final String provider;
    private final Set<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String email, String password, String provider, Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(email, "email must not be null");
        Assert.notNull(password, "password must not be null");
        Assert.notNull(provider, "provider must not be null");
        Assert.notNull(authorities, "authorities must not be null");
        this.email = email;
        this.password = password;
        this.provider = provider;
        this.authorities = Collections.unmodifiableSet((Set<? extends GrantedAuthority>) authorities);

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return true;
    }

    public String getProvider() {
        return this.provider;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String email;
        private String password;
        private String provider;
        private Set<? extends GrantedAuthority> authorities;

        private Builder() {

        }

        public Builder email(String email) {
            Assert.notNull(email, "email must not be null");
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            Assert.notNull(password, "password must not be null");
            this.password = password;
            return this;
        }

        public Builder provider(String provider) {
            Assert.notNull(provider, "provider must not be null");
            this.provider = provider;
            return this;
        }

        public Builder roles(String roles) {
            String[] rolesArray = roles.split(",");
            Set<GrantedAuthority> authorities = new HashSet<>();
            for (String role : rolesArray) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
            this.authorities = authorities;
            return this;
        }

        public UserDetails build() {
            return new CustomUserDetails(this.email, this.password, this.provider, this.authorities);
        }
    }
}
