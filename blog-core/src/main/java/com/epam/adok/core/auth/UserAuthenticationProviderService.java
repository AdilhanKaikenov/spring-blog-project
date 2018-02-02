package com.epam.adok.core.auth;

import com.epam.adok.core.entity.Role;
import com.epam.adok.core.entity.User;
import com.epam.adok.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAuthenticationProviderService implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();
        final User user = userService.getUserByAuth(login, password);
        if (user != null) {
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
            for (Role role : user.getRoles()) {
                grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
            }
            return new UsernamePasswordAuthenticationToken(login, password, grantedAuthorityList);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
