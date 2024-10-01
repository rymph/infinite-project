package com.example.security.oauth;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;

import java.util.*;

public class CustomOAuth2User implements OAuth2User {

    @Getter
    private final String provider;
    @Getter
    private final String nameAttributeKey;
    private final Map<String, Object> attributes;
    private final Set<GrantedAuthority> authorities;

    public CustomOAuth2User(OAuth2User oAuth2User, String provider, String nameAttributeKey) {
        Assert.notNull(oAuth2User, "oAuth2User cannot be null");
        Assert.hasText(provider, "provider cannot be empty");
        Assert.hasText(nameAttributeKey, "nameAttributeKey cannot be empty");
        // set provider
        this.provider = provider;
        // set authorities to ROLE_USER
        Set<GrantedAuthority> authorities = new LinkedHashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        this.authorities = Collections.unmodifiableSet(authorities);
        // set attributes
        Map<String, Object> attributes = oAuth2User.getAttributes();
        if(provider.equals("naver")){
            this.attributes = Collections.unmodifiableMap((Map<String, Object>)attributes.get("response"));
            this.nameAttributeKey = "id";
        }else{
            this.attributes = attributes;
            this.nameAttributeKey = nameAttributeKey;
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return this.attributes.get(nameAttributeKey).toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: [");
        sb.append(this.getName());
        sb.append("], Granted Authorities: [");
        sb.append(this.getAuthorities());
        sb.append("], User Attributes: [");
        sb.append(this.getAttributes());
        sb.append("]");
        return sb.toString();
    }

}
