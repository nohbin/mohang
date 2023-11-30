package com.example.mohang.security;

import com.example.mohang.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public record CustomPrincipal(
        String username,
        String password,
        String email,
        String nickname,
        Collection<? extends GrantedAuthority> authorities,
        Map<String, Object> oAuth2Attributes
) implements UserDetails, OAuth2User {

    public static CustomPrincipal of(String username, String password, String email, String nickname){
        return CustomPrincipal.of(username, password, email, nickname ,Map.of());
    }

    public static CustomPrincipal of(String username, String password, String email, String nickname, Map<String, Object> oAuth2Attributes){
        Set<RoleType> roleTypes = Set.of(RoleType.USER);
        return new CustomPrincipal(
                username,
                password,
                email,
                nickname,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .toList(),
                oAuth2Attributes
        );
    }

    public static CustomPrincipal from(UserAccountDto dto){
        return CustomPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.email(),
                dto.nickname()

        );
    }

    public UserAccountDto toDto(){
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname
        );
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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
        return true;
    }



    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Attributes;
    }

    @Override
    public String getName() {
        return username;
    }

    public enum RoleType{
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
